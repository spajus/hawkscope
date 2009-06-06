/*
 * Copyright (c) 2008-2009 Tomas Varaneckas
 * http://www.varaneckas.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.varaneckas.hawkscope.hotkey;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import jxgrabkey.HotkeyConflictException;
import jxgrabkey.HotkeyListener;
import jxgrabkey.JXGrabKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;

/**
 * Key listener for X11 (Linux)
 * 
 * FIXME this is all based on dirty hacks
 * Due to swt bug: https://bugs.eclipse.org/bugs/show_bug.cgi?id=11570
 * 
 * Does not work on top of eclipse
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class X11KeyListener extends GlobalHotkeyListener {
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(X11KeyListener.class);

    /**
     * Constructs libJXGrabKey listener
     */
    public X11KeyListener() {
        MenuFactory.getMainMenu().getSwtMenuObject().getDisplay()
                .asyncExec(new Runnable() {
            public void run() {
                if (loadJarLibrary("libJXGrabKey.so")) {
	                JXGrabKey.setDebugOutput(false);
	                try {
	                    JXGrabKey.getInstance().registerAwtHotkey(1, 
	                            InputEvent.CTRL_MASK, KeyEvent.VK_SPACE);
	                    JXGrabKey.getInstance().addHotkeyListener(getListener());
	                } catch (HotkeyConflictException e) {
	                    log.debug("Hotkey conflict!", e);
	                    JXGrabKey.getInstance().cleanUp();
	                }
                }
            }
        });
    }
    
    /**
     * Gets the instance of new HotkeyListener configured for hawkscope 
     * (Shift + space to invoke)
     * 
     * @return new HotkeyListener
     */
    public HotkeyListener getListener() {
        return new HotkeyListener() {
            public void onHotkey(final int key) {
                log.debug("hotkey found " + key);
                displayHawkscopeMenu();
            }
        };
    }
    
    /**
     * Hawkscope launch shell for dirty hacks
     */
    private Shell hawkscopeLaunchShell = new Shell(SWT.DIALOG_TRIM | SWT.NONE);
    
    /**
     * Hawkscope support shell for dirty hacks
     */
    private Shell supportShell = new Shell();
    
    /**
     * A robot to simulate mouse clicks
     */
    private Robot robo = createRobot();
    
    /**
     * Displays Hawkscope menu at mouse location.
     * Based on very, very dirty hacks, because of this bug:
     * https://bugs.eclipse.org/bugs/show_bug.cgi?id=11570
     */
    protected void displayHawkscopeMenu() {
        try {
            MenuFactory.getMainMenu().getSwtMenuObject().getDisplay()
                    .syncExec(new Runnable() {
                public void run() {
                    if (MenuFactory.getMainMenu().getSwtMenuObject().isVisible()) {
                        MenuFactory.getMainMenu().forceHide();
                    }
                    try {
                        hawkscopeLaunchShell.setText("Hawkscope");
                        final StateEvent se = new StateEvent();
                        final Point loc = Display.getDefault().getCursorLocation();
                        se.setX(loc.x);
                        se.setY(loc.y);
                        hawkscopeLaunchShell.setLocation(loc.x -30, loc.y - 30);
                        hawkscopeLaunchShell.setSize(60, 60);
                        hawkscopeLaunchShell.setVisible(true);
                        supportShell.setSize(1, 1);
                        supportShell.setVisible(true);
                        supportShell.setVisible(false);
                        Thread.sleep(20L);
                        Thread.yield();
                        robo.mousePress(InputEvent.BUTTON1_MASK);
                        Thread.sleep(10L);
                        robo.mouseRelease(InputEvent.BUTTON1_MASK);
                        MenuFactory.getMainMenu().getState().act(se);
                        hawkscopeLaunchShell.setVisible(false);
                    } catch (final Exception e) {
                        throw new RuntimeException("Failed invoking hawkscope " +
                        		"menu with shortcut", e);
                    }
                }
            });
        } catch (final Exception e) {
            throw new RuntimeException("Failed invoking hawkscope with " +
            		"shortcut key", e);
        }
    }
    
    /**
     * Creates the Robot
     * 
     * @return
     */
    private Robot createRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            return null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        JXGrabKey.getInstance().cleanUp();
    }
    
}
