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
import org.eclipse.swt.widgets.MenuItem;

public abstract class AbstractMenuItem 
		implements com.varaneckas.hawkscope.menu.MenuItem {

	/**
	 * SWT Menu item
	 */
	protected MenuItem swtMenuItem;
	/**
	 * Menu item text
	 */
	protected String text;
	/**
	 * Menu item icon
	 */
	protected Image icon;
	/**
	 * Is the item enabled?
	 */
	protected boolean enabled = true;

	public AbstractMenuItem() {
		super();
	}

	/**
	 * Sets the menu item icon image
	 */
	public void setIcon(final Image icon) {
	    this.icon = icon;
	}

	/**
	 * Sets the menu item text
	 */
	public void setText(final String text) {
	    this.text = "&".concat(text);
	}

	/**
	 * Sets the Enabled flag
	 */
	public void setEnabled(final boolean enabled) {
	    this.enabled = enabled;
	}

	/**
	 * Tells if item is enabled
	 * 
	 * @return
	 */
	public boolean isEnabled() {
	    return this.enabled;
	}

	public MenuItem getSwtMenuItem() {
		return swtMenuItem;
	}
}