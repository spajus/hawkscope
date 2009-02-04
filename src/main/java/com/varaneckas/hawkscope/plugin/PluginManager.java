package com.varaneckas.hawkscope.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.gui.swt.SWTMainMenu;
import com.varaneckas.hawkscope.menu.FolderMenu;

public class PluginManager {
    
    private static final PluginManager instance = new PluginManager();
    
    private PluginManager() {
        //FIXME playing around
        //plugins.add(new OpenWithPlugin());
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

    public void beforeQuickAccess(SWTMainMenu mainMenu) {
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

    public void beforeAboutMenuItem(SWTMainMenu mainMenu) {
        for (Plugin plugin : getActivePlugins()) {
            if (plugin.canHookBeforeAboutMenuItem())
                plugin.beforeAboutMenuItem(mainMenu);
        }
    }

}
