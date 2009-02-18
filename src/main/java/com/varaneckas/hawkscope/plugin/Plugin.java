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
package com.varaneckas.hawkscope.plugin;

import java.io.File;
import java.util.List;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.gui.settings.AbstractSettingsTabItem;
import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MainMenu;

/**
 * Hawkscope Plugin interface
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public interface Plugin {
	
	/**
	 * Tells if plugin is enabled
	 * 
	 * @return
	 */
	public boolean isEnabled();
	
	/**
	 * Sets plugin as enabled or disabled 
	 * 
	 * @param enabled
	 */
	public void setEnabled(final boolean enabled);
    
    /**
     * Gets plugin name
     * 
     * @return plugin Human readable name
     */
    public String getName();
    
    /**
     * Gets plugin description
     * 
     * @return description text
     */
    public String getDescription();
    
    /**
     * Gets plugin version
     * 
     * @return plugin version number
     */
    public String getVersion();
    
    /**
     * Tells if plugin can enhance {@link FolderMenu}
     * 
     * @return true | false
     */
    public boolean canEnhanceFolderMenu();
    
    /**
     * Tells if plugin can enhance {@link FileMenuItem}
     * 
     * @return true | false
     */
    public boolean canEnhanceFileMenuItem();
    
    /**
     * Tells if plugin can enhance Quick Access List Item
     * 
     * @return true | false
     */
    public boolean canEnhanceQuickAccessItem();
    
    /**
     * Tells if plugin can hook before about menu item 
     * 
     * @return true | false
     */
    public boolean canHookBeforeAboutMenuItem();
    
    /**
     * Tells if plugin can hook before Quick Access List
     * 
     * @return true | false
     */
    public boolean canHookBeforeQuickAccessList();

    /**
     * Tells if plugin can intercept {@link FileMenuItem} clicks
     * 
     * @return true | false
     */
    public boolean canInterceptClick();

    /**
     * Enhances {@link FolderMenu}
     * 
     * @param file target {@link File}
     * @param menu the folder menu
     * @param submenu submenu
     * @param listener FolderMenuItemListener
     */
    void enhanceFolderMenu(final File file, final MenuItem menu, 
            final Menu submenu, final FolderMenuItemListener listener);

    /**
     * Enhances {@link FileMenuItem}
     * 
     * @param menuItem menu item to enhance
     * @param file target file
     */
    void enhanceFileMenuItem(final MenuItem menuItem, final File file);

    /**
     * Hooks before quick access list
     * 
     * @param mainMenu main menu
     */
    void beforeQuickAccess(final MainMenu mainMenu);

    /**
     * Enhances Quick Access item
     * 
     * @param fm FolderMenu from Quick Access
     * @param custom target custom folder
     */
    void enhanceQuickAccessItem(final FolderMenu fm, final File custom);

    /**
     * Hooks before About menu item
     * 
     * @param mainMenu main menu
     */
    void beforeAboutMenuItem(final MainMenu mainMenu);

    /**
     * Intercepts {@link FileMenuItem} "Click"
     * 
     * @param file target file
     * @return should other actions be executed?
     */
    public boolean interceptClick(final File file);

    /**
     * Enhances Settings {@link TabFolder}
     * 
     * @param settingsTabFolder Settings tab folder
     * @param tabList Global tab list. Add new {@link TabItem} here too!
     */
    public void enhanceSettings(final TabFolder settingsTabFolder, 
            final List<AbstractSettingsTabItem> tabList);

}
