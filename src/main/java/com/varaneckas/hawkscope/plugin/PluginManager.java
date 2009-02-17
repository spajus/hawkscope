package com.varaneckas.hawkscope.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;

import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.gui.settings.AbstractSettingsTabItem;
import com.varaneckas.hawkscope.gui.settings.SettingsWindow;
import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MainMenu;
import com.varaneckas.hawkscope.plugin.openwith.OpenWithPlugin;

/**
 * Plugin Manager
 * 
 * Works as a broker between Hawkscope and it's plugins
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class PluginManager {
    
    /**
     * Singleton instance
     */
    private static final PluginManager instance = new PluginManager();
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(PluginManager.class);
    
    /**
     * Private singleton constructor
     */
    private PluginManager() {
        //FIXME playing around
        plugins.add(OpenWithPlugin.getInstance());
    }
    
    /**
     * List of enabled {@link Plugin}s
     */
    private final List<Plugin> plugins = new ArrayList<Plugin>();
    
    /**
     * Gets the singleton instance of {@link PluginManager}
     * 
     * @return singleton instance
     */
    public static PluginManager getInstance() {
        return instance;
    }
    
    /**
     * Gets the list of active {@link Plugin}s
     * 
     * @return active plugins
     */
    public List<Plugin> getActivePlugins() {
        return plugins;
    }

    /**
     * Enhances {@link FolderMenu} with Plugins
     * 
     * @param file
     * @param menu
     * @param submenu
     * @param listener
     */
    public void enhanceFolderMenu(final File file, final MenuItem menu, 
            final Menu submenu, final FolderMenuItemListener listener) {
        for (final Plugin plugin : getActivePlugins()) {
            if (plugin.canEnhanceFolderMenu())
                plugin.enhanceFolderMenu(file, menu, submenu, listener);
        }
    }

    /**
     * Enhances {@link FileMenuItem} with Plugins
     * 
     * @param menuItem
     * @param file
     */
    public void enhanceFileMenuItem(final MenuItem menuItem, final File file) {
        for (final Plugin plugin : getActivePlugins()) {
            if (plugin.canEnhanceFileMenuItem())
                plugin.enhanceFileMenuItem(menuItem, file);
        }
    }

    /**
     * Hooks in before Quick Access menu
     * 
     * @param mainMenu
     */
    public void beforeQuickAccess(final MainMenu mainMenu) {
        for (final Plugin plugin : getActivePlugins()) {
            if (plugin.canHookBeforeQuickAccessList())
                plugin.beforeQuickAccess(mainMenu);
        }
    }

    /**
     * Enhances Quick Access item with plugins
     * 
     * @param fm
     * @param custom
     */
    public void enhanceQuickAccessItem(final FolderMenu fm, final File custom) {
        for (final Plugin plugin : getActivePlugins()) {
            if (plugin.canEnhanceQuickAccessItem())
                plugin.enhanceQuickAccessItem(fm, custom);
        }
    }

    /**
     * Hooks before About menu item
     * 
     * @param mainMenu
     */
    public void beforeAboutMenuItem(final MainMenu mainMenu) {
        for (final Plugin plugin : getActivePlugins()) {
            if (plugin.canHookBeforeAboutMenuItem())
                plugin.beforeAboutMenuItem(mainMenu);
        }
    }
    
    /**
     * Intercepts file click with Plugins
     * 
     * @param file target file
     * @return
     */
    public boolean interceptClick(final File file) {
        boolean proceed = true;
        for (final Plugin plugin : getActivePlugins()) {
            if (plugin.canInterceptClick()) {
                proceed = plugin.interceptClick(file);
                if (!proceed) break;
            }
        }
        return proceed;
    }

    /**
     * Enhances {@link SettingsWindow}'s {@link TabFolder}
     * 
     * @param settingsTabFolder
     * @param tabList
     */
    public void enhanceSettings(final TabFolder settingsTabFolder, 
            final List<AbstractSettingsTabItem> tabList) {
        for (final Plugin plugin : getActivePlugins()) {
            try {
                plugin.enhanceSettings(settingsTabFolder, tabList);
            } catch (final Exception e) {
                log.warn("Failed enhancing settings tab for plugin: " 
                        + plugin.getId(), e);
            }
        }
    }

}
