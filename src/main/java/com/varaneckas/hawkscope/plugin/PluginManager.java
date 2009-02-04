package com.varaneckas.hawkscope.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MainMenu;

public class PluginManager {
    
    private static final PluginManager instance = new PluginManager();
    
    private PluginManager() {
        plugins.add(new PluginAdapter() {});
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
            plugin.enhanceFolderMenu(file, menu, submenu, listener);
        }
    }

    public void enhanceFileMenuItem(MenuItem menuItem, File file) {
        for (Plugin plugin : getActivePlugins()) {
            plugin.enhanceFileMenuItem(menuItem, file);
        }
    }

    public void beforeQuickAccess(MainMenu mainMenu) {
        for (Plugin plugin : getActivePlugins()) {
            plugin.beforeQuickAccess(mainMenu);
        }
    }

    public void enhanceQuickAccessItem(FolderMenu fm, File custom) {
        for (Plugin plugin : getActivePlugins()) {
            plugin.enhanceQuickAccessItem(fm, custom);
        }
    }

    public void beforeAboutMenuItem(MainMenu mainMenu) {
        for (Plugin plugin : getActivePlugins()) {
            plugin.beforeAboutMenuItem(mainMenu);
        }
    }

}
