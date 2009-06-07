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

import jxgrabkey.HotkeyListener;
import jxgrabkey.JXGrabKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Global Hotkey manager for X11 (Linux)
 * 
 * Based on JXGrabKey implementation (http://sf.net/jxgrabkey)
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class X11GlobalHotkeyManager extends GlobalHotkeyManager {
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(X11GlobalHotkeyManager.class);
   
    /**
     * Lazy loading {@link HotkeyListener} that invokes Hawkscope menu 
     * at cursor location
     */
    private HotkeyListener listener = null;
    
    /**
     * JXGrabKey hotkey manager
     */
    private JXGrabKey manager;

    /**
     * Constructs libJXGrabKey listener
     */
    public X11GlobalHotkeyManager() {
        if (loadJarLibrary("libJXGrabKey.so")) {
            manager = JXGrabKey.getInstance();
        }
    }
    
    /**
     * Gets the instance of new HotkeyListener configured for hawkscope 
     * (Shift + space to invoke)
     * 
     * @return new HotkeyListener
     */
    public HotkeyListener getListener() {
        if (listener == null) {
            synchronized (this) {
                if (listener == null) {
                    listener = new X11HotkeyListener();
                }
            }
        }
        return listener;
    }
    
    @Override
    protected void finalize() throws Throwable {
        manager.cleanUp();
    }

    @Override
    public void clearHotkeys() {
        if (listener != null) {
            manager.unregisterHotKey(1);
            manager.removeHotkeyListener(listener);
        }
    }

    @Override
    public void registerHotkey(final int modifier, final int key) {
        try {
            manager.registerAwtHotkey(1, modifier, key);
            manager.addHotkeyListener(getListener());
        } catch (final Exception e) {
            log.debug("Hotkey manager error!", e);
            manager.cleanUp();
        }
    }
    
}
