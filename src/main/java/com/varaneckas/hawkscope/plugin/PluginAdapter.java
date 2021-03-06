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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;

import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.gui.settings.AbstractSettingsTabItem;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MainMenu;

/**
 * {@link Plugin} adapter to build your Plugins upon.
 * 
 * Extend it and override the functionality your plugin will provide.
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class PluginAdapter implements Plugin {
    
    /**
     * Logger
     */
    protected Log log = LogFactory.getLog(getClass());
    
    /**
     * Tells if plugin is enabled
     */
    protected boolean enabled = true;
    
    /**
     * @see Plugin#canEnhanceFileMenuItem()
     */
    protected boolean canEnhanceFileMenuItem = false;
    
    /**
     * @see Plugin#canEnhanceFolderMenu()
     */
    protected boolean canEnhanceFolderMenu = false;
    
    /**
     * @see Plugin#canEnhanceQuickAccessItem()
     */
    protected boolean canEnhanceQuickAccessItem = false;
    
    /**
     * @see Plugin#canHookBeforeAboutMenuItem()
     */
    protected boolean canHookBeforeAboutMenuItem = false;
    
    /**
     * @see Plugin#canHookBeforeQuickAccessList()
     */
    protected boolean canHookBeforeQuickAccessList = false;
    
    /**
     * @see Plugin#canInterceptClick()
     */
    protected boolean canInterceptClick = false;
    
    /**
     * @see Plugin#canEnhanceFileMenuItem()
     */
    public boolean canEnhanceFileMenuItem() {
        return canEnhanceFileMenuItem;
    }
    
    /**
     * @see Plugin#canEnhanceFolderMenu()
     */
    public boolean canEnhanceFolderMenu() {
        return canEnhanceFolderMenu;
    }
    
    /**
     * @see Plugin#canEnhanceQuickAccessItem()
     */
    public boolean canEnhanceQuickAccessItem() {
        return canEnhanceQuickAccessItem;
    }
    
    /**
     * @see Plugin#canHookBeforeAboutMenuItem()
     */
    public boolean canHookBeforeAboutMenuItem() {
        return canHookBeforeAboutMenuItem;
    }

    /**
     * @see Plugin#canHookBeforeQuickAccessList()
     */
    public boolean canHookBeforeQuickAccessList() {
        return canHookBeforeQuickAccessList;
    }
    
    /**
     * @see Plugin#canInterceptClick()
     */
    public boolean canInterceptClick() {
        return canInterceptClick;
    }

    /**
     * @see Plugin#beforeAboutMenuItem(MainMenu)
     */
    public void beforeAboutMenuItem(final MainMenu mainMenu) {
        log.debug("hooking before about menu item");
    }

    /**
     * @see Plugin#beforeQuickAccess(MainMenu)
     */
    public void beforeQuickAccess(final MainMenu mainMenu) {
        log.debug("hooking before quick access");
    }

    /**
     * @see Plugin#enhanceFileMenuItem(MenuItem, File)
     */
    public void enhanceFileMenuItem(final MenuItem menuItem, final File file) {
        log.debug("hooking before file menu item");
    }

    /**
     * @see Plugin#enhanceFolderMenu(File, MenuItem, Menu, FolderMenuItemListener)
     */    
    public void enhanceFolderMenu(final File file, final MenuItem menu, 
            final Menu submenu, final FolderMenuItemListener listener) {
        log.debug("hooking before folder menu item");
    }

    /**
     * @see Plugin#enhanceQuickAccessItem(FolderMenu, File)
     */
    public void enhanceQuickAccessItem(final FolderMenu fm, final File custom) {
        log.debug("hhooking into quick access item");
    }
    
    /**
     * @see Plugin#interceptClick(File)
     */
    public boolean interceptClick(final File file) {
        log.debug("Intercepting click");
        //continue with other actions
        return true;
    }
    
    /**
     * @see Plugin#enhanceSettings(TabFolder, List)
     */
    public void enhanceSettings(final TabFolder folder, 
            final List<AbstractSettingsTabItem> tabItems) {
        log.debug("Skipping enhance settings on plugin");
    }
    
    /**
     * @see Plugin#isEnabled()
     */
    public boolean isEnabled() {
    	return enabled;
    }
    
    /**
     * @see Plugin#setEnabled(boolean)
     */
    public void setEnabled(final boolean enabled) {
    	this.enabled = enabled;
    }

}
