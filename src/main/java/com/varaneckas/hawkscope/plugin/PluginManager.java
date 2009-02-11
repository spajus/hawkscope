package com.varaneckas.hawkscope.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MainMenu;
import com.varaneckas.hawkscope.plugin.openwith.OpenWithPlugin;

public class PluginManager {
    
    private static final PluginManager instance = new PluginManager();
    
    private static final Log log = LogFactory.getLog(PluginManager.class);
    
    private PluginManager() {
        //FIXME playing around
        plugins.add(new OpenWithPlugin());
    }
    
    private final List<Plugin> plugins = new ArrayList<Plugin>();
    
    public static PluginManager getInstance() {
        return instance;
    }
    
    public List<Plugin> getActivePlugins() {
        return plugins;
    }

    public void enhanceFolderMenu(File file, MenuItem menu, Menu submenu,
            FolderMenuItemListener listener) {
        for (Plugin plugin : getActivePlugins()) {
            if (plugin.canEnhanceFolderMenu())
                plugin.enhanceFolderMenu(file, menu, submenu, listener);
        }
    }

    public void enhanceFileMenuItem(MenuItem menuItem, File file) {
        for (Plugin plugin : getActivePlugins()) {
            if (plugin.canEnhanceFileMenuItem())
                plugin.enhanceFileMenuItem(menuItem, file);
        }
    }

    public void beforeQuickAccess(MainMenu mainMenu) {
        for (Plugin plugin : getActivePlugins()) {
            if (plugin.canHookBeforeQuickAccessList())
                plugin.beforeQuickAccess(mainMenu);
        }
    }

    public void enhanceQuickAccessItem(FolderMenu fm, File custom) {
        for (Plugin plugin : getActivePlugins()) {
            if (plugin.canEnhanceQuickAccessItem())
                plugin.enhanceQuickAccessItem(fm, custom);
        }
    }

    public void beforeAboutMenuItem(MainMenu mainMenu) {
        for (Plugin plugin : getActivePlugins()) {
            if (plugin.canHookBeforeAboutMenuItem())
                plugin.beforeAboutMenuItem(mainMenu);
        }
    }
    
    public boolean interceptClick(File file) {
        boolean proceed = true;
        for (Plugin plugin : getActivePlugins()) {
            if (plugin.canInterceptClick()) {
                proceed = plugin.interceptClick(file);
                if (!proceed) break;
            }
        }
        return proceed;
    }

    public void applySettings(final Configuration cfg, final CTabFolder settingsTabFolder) {
        for (Plugin plugin : getActivePlugins()) {
            plugin.applySettings(cfg, settingsTabFolder);
        }
    }

    public void enhanceSettings(final Configuration cfg, final CTabFolder settingsTabFolder) {
        for (Plugin plugin : getActivePlugins()) {
            try {
                plugin.enhanceSettings(cfg, settingsTabFolder);
            } catch (final Exception e) {
                log.warn("Failed enhancing settings tab for plugin: " 
                        + plugin.getId(), e);
            }
        }
    }

}
