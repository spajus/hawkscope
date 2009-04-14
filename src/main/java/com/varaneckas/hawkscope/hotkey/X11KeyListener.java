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

import java.awt.event.KeyEvent;
import java.io.File;

import jxgrabkey.HotkeyConflictException;
import jxgrabkey.HotkeyListener;
import jxgrabkey.JXGrabKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;
import com.varaneckas.hawkscope.util.IOUtils;

/**
 * Key listener for X11 (Linux)
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class X11KeyListener extends GlobalHotkeyListener {
    
    private static final Log log = LogFactory.getLog(X11KeyListener.class);

    public X11KeyListener() {
        Display.getCurrent().asyncExec(new Runnable() {
            public void run() {
                String jarLib = "libJXGrabKey.so";
                String tempLib = System.getProperty("java.io.tmpdir") 
                        + File.separator + jarLib;
                log.debug("Copying file");
                boolean copied = IOUtils.copyFile(jarLib, tempLib);
                log.debug("Copied file: " + copied);
                log.debug("Loading: " + tempLib);
                System.load(tempLib);
                log.debug("Loaded lib");
                JXGrabKey.setDebugOutput(true);
                try {
                    JXGrabKey.getInstance().registerAwtHotkey(1, 
                            KeyEvent.VK_CONTROL, KeyEvent.VK_SPACE);
                    JXGrabKey.getInstance().addHotkeyListener(getListener());
                } catch (HotkeyConflictException e) {
                    log.debug("Hotkey conflict!", e);
                    JXGrabKey.getInstance().cleanUp();
                }
            }
        });
    }
    
    public HotkeyListener getListener() {
        return new HotkeyListener() {
            public void onHotkey(final int key) {
                log.debug("otkey found " + key);
                try {
                    Display.getDefault().asyncExec(new Runnable() {
                        public void run() {
                            final StateEvent se = new StateEvent();
                            final Point loc = Display.getDefault().getCursorLocation();
                            se.setX(loc.x);
                            se.setY(loc.y);
                            MenuFactory.getMainMenu().getState().act(se);
                        }
                    });
                } catch (final Exception e) {
                    log.error("Failed invoking Hawkscope with x11 hotkey", e);
                }
            }
        };
    }
    
    @Override
    protected void finalize() throws Throwable {
        JXGrabKey.getInstance().cleanUp();
    }
    
}
