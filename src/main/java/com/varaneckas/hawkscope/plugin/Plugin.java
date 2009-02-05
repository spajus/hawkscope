package com.varaneckas.hawkscope.plugin;

import java.io.File;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.gui.swt.SWTFolderMenu;
import com.varaneckas.hawkscope.gui.swt.SWTMainMenu;

public interface Plugin {
    
    public boolean canEnhanceFolderMenu();
    
    public boolean canEnhanceFileMenuItem();
    
    public boolean canEnhanceQuickAccessItem();
    
    public boolean canHookBeforeAboutMenuItem();
    
    public boolean canHookBeforeQuickAccessList();

    public boolean canInterceptClick();

    void enhanceFolderMenu(File file, MenuItem menu, Menu submenu,
            FolderMenuItemListener listener);

    void enhanceFileMenuItem(MenuItem menuItem, File file);

    void beforeQuickAccess(SWTMainMenu mainMenu);

    void enhanceQuickAccessItem(SWTFolderMenu fm, File custom);

    void beforeAboutMenuItem(SWTMainMenu mainMenu);

    public boolean interceptClick(File file);

    
}
