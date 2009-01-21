package com.varaneckas.hawkscope.gui.listeners;

/**
 * Menu Item listener interface
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public interface MenuItemListener {

    /**
     * Acts when item is selected
     */
    public void itemSelected();
    
    /**
     * Acts when item is clicked
     */
    public void itemClicked();
    
}
