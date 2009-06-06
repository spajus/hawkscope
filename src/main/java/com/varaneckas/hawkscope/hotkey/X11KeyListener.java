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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import jxgrabkey.HotkeyConflictException;
import jxgrabkey.HotkeyListener;
import jxgrabkey.JXGrabKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Display;

/**
 * Key listener for X11 (Linux)
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
        Display.getCurrent().asyncExec(new Runnable() {
            public void run() {
                if (loadJarLibrary("libJXGrabKey.so")) {
	                JXGrabKey.setDebugOutput(true);
	                try {
	                    JXGrabKey.getInstance().registerAwtHotkey(1, InputEvent.CTRL_MASK, KeyEvent.VK_SPACE);
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
                log.debug("otkey found " + key);
                displayHawkscopeMenu();
            }
        };
    }
    
    @Override
    protected void finalize() throws Throwable {
        JXGrabKey.getInstance().cleanUp();
    }
    
}
