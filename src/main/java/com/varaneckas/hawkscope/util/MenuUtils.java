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
package com.varaneckas.hawkscope.util;

import org.eclipse.swt.widgets.Display;

/**
 * Utilities related to menus and menu items 
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class MenuUtils {

    /**
     * Menu item size in pixels
     */
    public static final int MENU_ITEM_SIZE = 32;
    
    /**
     * Gets automatic recommended number of menu items
     * 
     * @return number of menu items
     */
    public static int getAutoMenuSize() {
        try {
            int screenHeight = Display.getDefault().getPrimaryMonitor().getBounds().height;
            return (screenHeight / MENU_ITEM_SIZE) * 9 / 10; //menu gets cut at 90%
        } catch (final Exception e) {
            //for some weird reason sometimes display mode is null on Ubuntu 
            //running in VirtualBox 
            return 10;
        }
    }
    
}
