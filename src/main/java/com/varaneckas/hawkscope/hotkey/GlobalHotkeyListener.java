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

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;
import com.varaneckas.hawkscope.util.IOUtils;
import com.varaneckas.hawkscope.util.OSUtils;

/**
 * Global hotkey listener for invoking hawkscope menu
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class GlobalHotkeyListener {
    
    /**
     * Global Hotkey Listener instance
     */
    private static GlobalHotkeyListener instance = null;
    
    /**
     * Loads the required instance if possible
     * 
     * @return instance
     */
    public static synchronized GlobalHotkeyListener getInstance() {
        if (instance == null) {
            instance = chooseImpl();
        }
        return instance;
    }
    
    /**
     * Chooses {@link GlobalHotkeyListener} implementation according to OS
     * 
     * @return
     */
    private static GlobalHotkeyListener chooseImpl() {
        switch (OSUtils.CURRENT_OS) {
        case UNIX:
            return new X11KeyListener();
        case WIN:
            return new WinKeyListener();
        default:
            return null;
        }
    }
    
    protected boolean loadJarLibrary(String jarLib) {
        final String tempLib = System.getProperty("java.io.tmpdir") 
                + File.separator + jarLib;
        boolean copied = IOUtils.copyFile(jarLib, tempLib);
        if (!copied) {
        	return false;
        }
        System.load(tempLib);
        return true;
    }
    
    protected void displayHawkscopeMenu() {
		try {
            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    final StateEvent se = new StateEvent();
                    final Point loc = Display.getDefault().getCursorLocation();
                    se.setX(loc.x);
                    se.setY(loc.y);
//                    Shell sh = new Shell();
//                    sh.setVisible(true);
                    try {
                    Thread.sleep(1l);
//                    sh.setVisible(false);
//                        Robot robo = new Robot();
//                        Shell hs = new Shell();
//                        hs.setLocation(loc.x -100, loc.y - 100);
//                        hs.setSize(200, 200);
//                        hs.setVisible(true);
//                        robo.mousePress(InputEvent.BUTTON1_MASK);
                        Thread.sleep(1L);
                        MenuFactory.getMainMenu().getState().act(se);
//                        robo.mouseRelease(InputEvent.BUTTON1_MASK);
//                        hs.setLocation(10000, 10000);
                        Thread.sleep(10L);
//                        hs.setVisible(false);
//                        hs.dispose();
                    } catch (final Exception e) {
                        throw new RuntimeException("Failed invoking hawkscope menu with shortcut", e);
                    }
//                    sh.dispose();
                }
            });
        } catch (final Exception e) {
        	throw new RuntimeException("Failed invoking hawkscope with shortcut key", e);
        }
	}

}
