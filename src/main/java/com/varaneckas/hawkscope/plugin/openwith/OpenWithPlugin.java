package com.varaneckas.hawkscope.plugin.openwith;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.plugin.PluginAdapter;
import com.varaneckas.hawkscope.util.OSUtils;
import com.varaneckas.hawkscope.util.PathUtils;

public class OpenWithPlugin extends PluginAdapter {
	
    private static final Map<String, String> apps = new HashMap<String, String>();
    
    private static String folderNavigator = null;
    
    public OpenWithPlugin() {
        canEnhanceFolderMenu = true;
        canInterceptClick = true;
        refresh();
    }
    
    public static void refresh() {
        Configuration cfg = ConfigurationFactory.getConfigurationFactory()
                .getConfiguration();
        apps.clear();
        for (String key : cfg.getProperties().keySet()) {
            if (key.startsWith("plugin.openwith.type.")) {
                String runner = cfg.getProperties().get(key);
                apps.put(key.replaceFirst("plugin.openwith.type.", ""), 
                        PathUtils.sanitizePath(runner));
            }
        }
        folderNavigator = cfg.getProperties().get("plugin.openwith.folder.navigator");

    }
    
    @Override
    public boolean interceptClick(File file) {
        String ext = file.getName().replaceAll(".*\\.", ".");
        log.debug("intercepting click on " + ext);
        if (apps.containsKey(ext)) {
        	return OSUtils.exec(apps.get(ext), file.getAbsolutePath());
        }
        return true;
    }
    
    @Override
    public void enhanceFolderMenu(final File file, MenuItem menu, Menu submenu,
            FolderMenuItemListener listener) {
        if (folderNavigator == null || folderNavigator.length() == 0) return;
        log.debug("open with...");
        if (submenu.getItemCount() < 1) return;
        MenuItem open = submenu.getItem(0);
        
        open.removeListener(SWT.Selection, 
                open.getListeners(SWT.Selection)[0]);
        
        open.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event ev) {
            	if (OSUtils.exec(folderNavigator, file.getAbsolutePath())) {
            		Program.launch(file.getAbsolutePath());
            	}
            }
        });
    }
    
    @Override
    public void enhanceSettings(final Configuration cfg, CTabFolder settingsTabFolder) {
         OpenWithPluginSettings.enhance(cfg, settingsTabFolder);
    }
    
    @Override
    public void applySettings(final Configuration cfg, CTabFolder settingsTabFolder) {
        OpenWithPluginSettings.apply(cfg, settingsTabFolder);
    }
   
}
