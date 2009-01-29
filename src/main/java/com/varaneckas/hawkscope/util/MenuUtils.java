package com.varaneckas.hawkscope.util;

import java.awt.GraphicsEnvironment;

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
            int screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice().getDisplayMode().getHeight();
            return (screenHeight / MENU_ITEM_SIZE) * 9 / 10; //menu gets cut at 90%
        } catch (final Exception e) {
            //for some weird reason sometimes display mode is null on Ubuntu 
            //running in VirtualBox 
            return 10;
        }
    }
    
}
