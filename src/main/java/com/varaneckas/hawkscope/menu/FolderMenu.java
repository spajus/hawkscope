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

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;

import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.plugin.PluginManager;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.PathUtils;

/**
 * {@link FolderMenu} - SWT implementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class FolderMenu extends AbstractMenuItem {
    
    /**
     * Target folder
     */
    final protected File file;
    
    /**
     * SWT Submenu object
     */
    private Menu submenu;

    public void createMenuItem(final Menu parent) {
        swtMenuItem = new org.eclipse.swt.widgets.MenuItem(parent, SWT.CASCADE);
        submenu = new Menu(parent);
        final FolderMenuItemListener listener = new FolderMenuItemListener(this, 
                this.file);
        addOpenAction(listener);
        PluginManager.getInstance()
                .enhanceFolderMenu(file, swtMenuItem, submenu, listener);
        swtMenuItem.setMenu(submenu);
        swtMenuItem.setText(text);
        swtMenuItem.setEnabled(enabled);
        swtMenuItem.setImage((Image) icon);
        if (this.file != null) {
            submenu.addMenuListener(new MenuAdapter() {
                @Override
                public void menuShown(final MenuEvent e) {
                    listener.itemSelected();
                }
            });
        }
    }

    /**
     * Adds "Open" action to the menu
     * 
     * @param listener Folder menu item listener
     */
    private void addOpenAction(final FolderMenuItemListener listener) {
        if (file != null) {
            final org.eclipse.swt.widgets.MenuItem open = 
                new org.eclipse.swt.widgets.MenuItem(submenu, SWT.PUSH);
            open.setImage(IconFactory.getInstance().getIcon("open"));
            open.setText("&Open");
            open.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent e) {
                    listener.itemClicked();
                }
            });
        }
    }
    
    /**
     * Initializing constructor
     * 
     * @param file target folder
     */
    public FolderMenu(final File file) {
        this.file = file;
        if (file != null) {
            setText(PathUtils.getFileName(file));
            this.icon = IconFactory.getInstance().getIcon(file);
        } 
    }
    
    public void addMenuItem(final com.varaneckas.hawkscope.menu.MenuItem item) {
        item.createMenuItem(submenu);
    }

    public void addSeparator() {
        new org.eclipse.swt.widgets.MenuItem(swtMenuItem.getMenu(), SWT.SEPARATOR);
    }

}
