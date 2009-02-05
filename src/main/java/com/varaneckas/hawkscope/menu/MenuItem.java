package com.varaneckas.hawkscope.menu;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;

/**
 * SWT specific menu item implementation interface
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public interface MenuItem {

    /**
     * Creates menu item
     * 
     * @param parent parent {@link Menu} item
     */
    public void createMenuItem(final Menu parent);
    

    /**
     * Sets the menu item icon
     * 
     * @param icon icon
     */
    public void setIcon(final Image icon);
    
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
