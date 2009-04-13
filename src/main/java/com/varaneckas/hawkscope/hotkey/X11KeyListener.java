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

import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;
import com.varaneckas.hawkscope.tray.TrayIconListener;

import jxgrabkey.HotkeyConflictException;
import jxgrabkey.HotkeyListener;
import jxgrabkey.JXGrabKey;

/**
 * Key listener for X11 (Linux)
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class X11KeyListener extends GlobalHotkeyListener {

    public X11KeyListener() {
        System.load(getClass().getClassLoader().getResource("libJXGrabKey.so").getFile());
        JXGrabKey.setDebugOutput(true);
        try {
            JXGrabKey.getInstance().registerAwtHotkey(1, KeyEvent.VK_CONTROL 
                    | KeyEvent.VK_SHIFT, KeyEvent.VK_H);
            JXGrabKey.getInstance().removeHotkeyListener(getListener());
        } catch (HotkeyConflictException e) {
            e.printStackTrace();
            JXGrabKey.getInstance().cleanUp();
        }
    }
    
    public HotkeyListener getListener() {
        return new HotkeyListener() {
            public void onHotkey(final int key) {
                final StateEvent se = TrayIconListener.findPopupMenuLocation();
                MenuFactory.getMainMenu().getState().act(se);
            }
        };
    }
    
    @Override
    protected void finalize() throws Throwable {
        JXGrabKey.getInstance().cleanUp();
    }
    
}
