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

import jxgrabkey.HotkeyListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;

/**
 * X11 Global {@link HotkeyListener} that tries to invoke Hawkscope menu
 * at mouse cursor location
 * 
 * FIXME this is all based on dirty hacks
 * Due to swt bug: https://bugs.eclipse.org/bugs/show_bug.cgi?id=11570
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class X11HotkeyListener implements HotkeyListener {
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(X11HotkeyListener.class);
    
    /**
     * Hawkscope launch shell for dirty hacks
     */
    private Shell hawkscopeLaunchShell = new Shell(Display.getDefault(), SWT.ON_TOP);
    
    /**
     * Hawkscope support shell for dirty hacks
     */
    private Shell supportShell = new Shell(Display.getDefault(), SWT.ON_TOP);
    
    /**
     * A robot to simulate mouse clicks
     */
    private Robot robo = createRobot();
    
    /**
     * Creates the Robot
     * 
     * @return
     */
    private Robot createRobot() {
        try {
            return new Robot();
        } catch (final AWTException e) {
            log.error("Could not create Robot", e);
            return null;
        }
    }

    
    public void onHotkey(final int key) {
        log.debug("hotkey found " + key);
        displayHawkscopeMenu();
    }
    
    /**
     * Displays Hawkscope menu at mouse location. Based on dirty hacks and 
     * blind experiments. Basically, it creates a window beneath the cursor
     * and executes a real mouse click with help of {@link Robot}. This way
     * invoking the menu works.
     * 
     * A bug that prevents the normal activity:
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
                        final StateEvent se = new StateEvent();
                        final Point loc = Display.getDefault().getCursorLocation();
                        se.setX(loc.x);
                        se.setY(loc.y);
                        hawkscopeLaunchShell.setLocation(loc.x -30, loc.y - 30);
                        hawkscopeLaunchShell.setSize(60, 60);
                        hawkscopeLaunchShell.setVisible(true);
                        //for a weird reason, if the second shell is not
                        //displayed, the whole hack does not work.
                        supportShell.setLocation(loc.x -30, loc.y - 30);
                        supportShell.setSize(60, 60);
                        supportShell.setVisible(true);
                        //need all the sleeps
                        //if duration is lowered, hack starts to randomly
                        //fail to work.
                        Thread.sleep(50L);
                        Thread.yield();
                        if (robo != null) {
                            robo.mousePress(InputEvent.BUTTON1_MASK);
                            Thread.sleep(50L);
                        }
                        MenuFactory.getMainMenu().getState().act(se);
                        Thread.sleep(50L);
                        if (robo != null) {
                            robo.mouseRelease(InputEvent.BUTTON1_MASK);
                        }
                        supportShell.setVisible(false);
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
}
