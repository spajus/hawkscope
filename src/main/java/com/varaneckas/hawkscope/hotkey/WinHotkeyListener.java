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

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import com.melloware.jintellitype.HotkeyListener;
import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;

/**
 * Windows {@link HotkeyListener}.
 * 
 * Displays Hawkscope menu at cursor location on any shortcut key
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class WinHotkeyListener implements HotkeyListener {
    
    /**
     * Displays Hawkscope menu at cursor location
     */
    public void onHotKey(final int hk) {
        MenuFactory.getMainMenu().getSwtMenuObject().getDisplay().syncExec(
                new Runnable() {
            public void run() {
                final StateEvent se = new StateEvent();
                final Point loc = Display.getDefault().getCursorLocation();
                se.setX(loc.x);
                se.setY(loc.y);
                MenuFactory.getMainMenu().getState().act(se);
            }
        });
    }
}
