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
     * Sets the enabled flag
     * 
     * @param enabled is menu item enabled?
     */
    public void setEnabled(final boolean enabled);
    
}
