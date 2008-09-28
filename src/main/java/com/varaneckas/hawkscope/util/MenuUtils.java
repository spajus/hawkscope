package com.varaneckas.hawkscope.util;

import java.awt.GraphicsEnvironment;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * Utilities related to menus and menu items 
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class MenuUtils {

    /**
     * Menu separator
     */
    public final static JSeparator SEPARATOR = 
        new JSeparator(SwingConstants.HORIZONTAL);
    
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
        int screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDisplayMode().getHeight();
        return (screenHeight / MENU_ITEM_SIZE) * 9 / 10; //menu gets cut at 90%
    }
    
}
