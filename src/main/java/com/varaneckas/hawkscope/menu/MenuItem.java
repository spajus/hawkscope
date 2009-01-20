package com.varaneckas.hawkscope.menu;

/**
 * Hawkscope Menu Item
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public interface MenuItem {

    /**
     * Sets the menu item icon
     * 
     * @param icon icon
     */
    public void setIcon(final Object icon);
    
    /**
     * Sets the menu item text
     * 
     * @param text menu item text
     */
    public void setText(final String text);
    
    /**
     * Sets the menu item tooltip text
     * 
     * @param text tooltip
     */
    public void setToolTipText(final String text);
    
    /**
     * Sets the enabled flag
     * 
     * @param enabled is menu item enabled?
     */
    public void setEnabled(final boolean enabled);

}
