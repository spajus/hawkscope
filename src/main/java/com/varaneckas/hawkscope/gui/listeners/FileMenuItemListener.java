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
package com.varaneckas.hawkscope.gui.listeners;

import java.io.File;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.program.Program;

import com.varaneckas.hawkscope.menu.MenuItem;
import com.varaneckas.hawkscope.plugin.PluginManager;

/**
 * Listener for {@link File} {@link MenuItem}
 * 
 * Handles the "Click" on files.
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class FileMenuItemListener extends SelectionAdapter {
   
    /**
     * Target {@link File}
     */
    private final File file;

    /**
     * Constructor with target {@link File}
     * 
     * @param file target
     */
    public FileMenuItemListener(final File file) {
        this.file = file;
    }

    /**
     * Triggers plugins and opens the file if necessary
     */
    @Override
    public void widgetSelected(final SelectionEvent e) {
        //Plugins can intercept the default execution. 
        boolean launch = PluginManager.getInstance().interceptClick(file);
        if (launch) {
            if (!Program.launch(file.getAbsolutePath())) {
                throw new RuntimeException("Cannot find program for opening "
                        + file);
            }
        }
    }
}
