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
package com.varaneckas.hawkscope.tray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;
import com.varaneckas.hawkscope.util.OSUtils;
import com.varaneckas.hawkscope.util.OSUtils.OS;

/**
 * Tray Icon listener for SWT GUI implementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class TrayIconListener implements Listener {

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(TrayIconListener.class);
    
    /**
     * Handles tray events
     */
    public void handleEvent(final Event event) {
        try {
            final StateEvent se = TrayIconListener.findPopupMenuLocation();
            MenuFactory.getMainMenu().getState().act(se);
        } catch (final Exception e) {
            log.error(e, e);
        }
    }

    /**
     * Finds popup menu location and makes a {@link StateEvent}
     * 
     * @return
     */
    public static synchronized StateEvent findPopupMenuLocation() {
        final Display d = Display.getDefault();
        final StateEvent se = new StateEvent();
        final Point loc = d.getCursorLocation();
        int x, y;
        x = loc.x;
        y = loc.y;
        if (!OSUtils.CURRENT_OS.equals(OS.WIN)) {
            final int traySize = OSUtils.getTraySize();
            //assume click is in the middle of the icon
            x = loc.x - traySize / 2;
            if (loc.y < traySize * 4) {
                //tray is on top side of the screen
                y = traySize;
            } else {
                //tray is on bottom side of the screen
                y = d.getBounds().height - traySize;
            }
        } else {
            loc.y -= 2;
        }
        se.setX(x);
        se.setY(y);
        return se;
    }

}
