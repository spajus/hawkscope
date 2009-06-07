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
        } catch (AWTException e) {
            return null;
        }
    }

    
    public void onHotkey(final int key) {
        log.debug("hotkey found " + key);
        displayHawkscopeMenu();
    }
    
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
                        final StateEvent se = new StateEvent();
                        final Point loc = Display.getDefault().getCursorLocation();
                        se.setX(loc.x);
                        se.setY(loc.y);
                        hawkscopeLaunchShell.setLocation(loc.x -30, loc.y - 30);
                        hawkscopeLaunchShell.setSize(60, 60);
                        hawkscopeLaunchShell.setVisible(true);
                        supportShell.setLocation(loc.x -30, loc.y - 30);
                        supportShell.setSize(60, 60);
                        supportShell.setVisible(true);
                        Thread.sleep(50L);
                        Thread.yield();
                        robo.mousePress(InputEvent.BUTTON1_MASK);
                        Thread.sleep(50L);
                        MenuFactory.getMainMenu().getState().act(se);
                        Thread.sleep(50L);
                        robo.mouseRelease(InputEvent.BUTTON1_MASK);
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
