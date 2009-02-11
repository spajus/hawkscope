package com.varaneckas.hawkscope.plugin;

import java.io.File;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MainMenu;

public interface Plugin {
    
    public String getId();
    
    public boolean canEnhanceFolderMenu();
    
    public boolean canEnhanceFileMenuItem();
    
    public boolean canEnhanceQuickAccessItem();
    
    public boolean canHookBeforeAboutMenuItem();
    
    public boolean canHookBeforeQuickAccessList();

    public boolean canInterceptClick();

    void enhanceFolderMenu(File file, MenuItem menu, Menu submenu,
            FolderMenuItemListener listener);

    void enhanceFileMenuItem(MenuItem menuItem, File file);

    void beforeQuickAccess(MainMenu mainMenu);

    void enhanceQuickAccessItem(FolderMenu fm, File custom);

    void beforeAboutMenuItem(MainMenu mainMenu);

    public boolean interceptClick(File file);

    public void applySettings(Configuration cfg, CTabFolder settingsTabFolder);

    public void enhanceSettings(Configuration cfg, CTabFolder settingsTabFolder);

    
}
