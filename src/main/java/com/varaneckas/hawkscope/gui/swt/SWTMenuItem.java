package com.varaneckas.hawkscope.gui.swt;

import org.eclipse.swt.widgets.Menu;

/**
 * SWT specific menu item implementation interface
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public interface SWTMenuItem {

    /**
     * Creates menu item
     * 
     * @param parent parent {@link Menu} item
     */
    public void createMenuItem(final Menu parent);
    
}
