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
public class ExecutableMenuItem extends AbstractMenuItem {

	/**
     * Menu item command
     */
    protected Command command;

    /**
     * Creates the Executable menu item
     */
    public void createMenuItem(final Menu parent) {
        swtMenuItem = new MenuItem(parent, SWT.PUSH);
        swtMenuItem.setImage((Image) icon);
        swtMenuItem.setText(text);
        swtMenuItem.setEnabled(enabled);
        swtMenuItem.addListener(SWT.Selection, new Listener() {
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
     * Gets the {@link Command} to execute
     * 
     * @return command
     */
    public Command getCommand() {
        return command;
    }

}
