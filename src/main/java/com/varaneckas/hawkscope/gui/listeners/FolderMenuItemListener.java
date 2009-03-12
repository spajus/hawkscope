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
import java.util.Arrays;

import org.eclipse.swt.program.Program;

import com.varaneckas.hawkscope.menu.ExecutableMenuItem;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.plugin.PluginManager;
import com.varaneckas.hawkscope.util.DynamicFileFilter;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.OSUtils;

/**
 * Folder Menu Item Listener
 * 
 * Loads menu contents on mouse over
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class FolderMenuItemListener {
    
    /**
     * Lazy loading flag
     */
    private boolean loaded = false;
    
    /**
     * Target menu
     */
    private final FolderMenu folderMenu;
    
    /**
     * Target folder
     */
    private final File file;
    
    /**
     * Constructor
     * 
     * @param menu tagert
     * @param file target
     */
    public FolderMenuItemListener(final FolderMenu menu, final File file) {
        this.folderMenu = menu;
        this.file = file;
    }

    /**
     * Processes the folder menu item selection
     */
    public void itemSelected() {
        if (!loaded && file != null && file.isDirectory()) {
            File[] files = file.listFiles(DynamicFileFilter.getInstance());
            final FolderMenu workMenu = folderMenu;
            if (files == null || files.length == 0) {                
                final ExecutableMenuItem empty = MenuFactory.newExecutableMenuItem();
                empty.setIcon(IconFactory.getInstance().getIcon("empty"));
                empty.setText("Empty...");
                empty.setEnabled(false);
                workMenu.addMenuItem(empty);
            } else {
                Arrays.sort(files);
                for (final File ff : files) {
                    if (ff.isDirectory()) {
                        if (OSUtils.isMacApp(ff)) {
                            workMenu.addMenuItem(MenuFactory.newFileMenuItem(ff));
                        } else {
                            workMenu.addMenuItem(MenuFactory.newFolderMenu(ff));
                        }
                    } else {
                        workMenu.addMenuItem(MenuFactory.newFileMenuItem(ff));
                    }
                }
            }
            files = null; //cleanup memory
            loaded = true;
        }
    }
    
    public void itemClicked() {
        final boolean launch = PluginManager.getInstance().interceptClick(file);
        if (launch) {
            Program.launch(file.getAbsolutePath());
        }
    }
}
