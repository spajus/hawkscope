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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.command.Command;

/**
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class ExecutableMenuItem implements com.varaneckas.hawkscope.menu.MenuItem {

    /**
     * Menu item text
     */
    private String text;

    /**
     * Menu item icon
     */
    private Image icon;

    /**
     * Menu item command
     */
    private Command command;

    /**
     * Is the item enabled?
     */
    private boolean enabled = true;
    
    /**
     * Creates the Executable menu item
     */
    public void createMenuItem(final Menu parent) {
        final MenuItem mi = new MenuItem(parent, SWT.PUSH);
        mi.setImage((Image) icon);
        mi.setText(text);
        mi.setEnabled(enabled);
        mi.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                command.execute();
            }
        });
    }

    /**
     * Sets the command to execute on click
     * 
     * @param command
     */
    public void setCommand(final Command command) {
        this.command = command;
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
        this.text = text;
    }

    /**
     * Gets the {@link Command} to execute
     * 
     * @return command
     */
    public Command getCommand() {
        return command;
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

}
