package com.varaneckas.hawkscope.plugin.openwith;

import java.io.File;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.command.AboutCommand;
import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.gui.swt.SWTExecutableMenuItem;
import com.varaneckas.hawkscope.gui.swt.SWTIconFactory;
import com.varaneckas.hawkscope.plugin.PluginAdapter;

public class OpenWithPlugin extends PluginAdapter {

    public OpenWithPlugin() {
        canEnhanceFolderMenu = true;
    }
    
    @Override
    public void enhanceFolderMenu(File file, MenuItem menu, Menu submenu,
            FolderMenuItemListener listener) {
        log.debug("open with...");
        
        SWTExecutableMenuItem openWith = new SWTExecutableMenuItem();
        openWith.setIcon(SWTIconFactory.getInstance().getIcon("open"));
        openWith.setText("Open With...");
        openWith.setCommand(new AboutCommand());
        openWith.createMenuItem(submenu);
    }
    
}
