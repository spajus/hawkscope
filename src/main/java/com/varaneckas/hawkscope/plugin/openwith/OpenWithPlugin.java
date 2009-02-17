package com.varaneckas.hawkscope.plugin.openwith;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.gui.settings.AbstractSettingsTabItem;
import com.varaneckas.hawkscope.plugin.PluginAdapter;
import com.varaneckas.hawkscope.util.OSUtils;
import com.varaneckas.hawkscope.util.PathUtils;

public class OpenWithPlugin extends PluginAdapter {
    
    public static final String PROP_FOLDER_NAVIGATOR = "plugin.openwith.folder.navigator";
    
    public static final String PROP_UNKNOWN_FILE_APP = "plugin.openwith.unknown.files";
    
    public static final String PROP_FILE_TYPE_PREFIX = "plugin.openwith.type.";
	
    private static final Map<String, String> apps = new HashMap<String, String>();
    
    private String folderNavigator = null;
    
    private String unknownFileApp = null;
    
    private OpenWithSettingsTabItem openWithTab;
    
    private static final OpenWithPlugin instance = new OpenWithPlugin();
    
    public static final OpenWithPlugin getInstance() {
        return instance;
    }
    
    private OpenWithPlugin() {
        canEnhanceFolderMenu = true;
        canInterceptClick = true;
        refresh();
    }
    
    public void refresh() {
        Configuration cfg = ConfigurationFactory.getConfigurationFactory()
                .getConfiguration();
        apps.clear();
        for (String key : cfg.getProperties().keySet()) {
            if (key.startsWith(PROP_FILE_TYPE_PREFIX)) {
                String runner = cfg.getProperties().get(key);
                apps.put(key.replaceFirst(PROP_FILE_TYPE_PREFIX, ""), 
                        PathUtils.sanitizePath(runner));
            }
        }
        folderNavigator = cfg.getProperties().get(PROP_FOLDER_NAVIGATOR);
        unknownFileApp = cfg.getProperties().get(PROP_UNKNOWN_FILE_APP);
    }
    
    @Override
    public boolean interceptClick(File file) {
        String ext = file.getName().replaceAll(".*\\.", ".");
        if (ext.equals(file.getName())) {
            ext = "";
        }
        log.debug("intercepting click on " + ext);
        if (apps.containsKey(ext)) {
        	return OSUtils.exec(apps.get(ext), file.getAbsolutePath());
        } else if (unknownFileApp != null && unknownFileApp.length() > 0){
            final Program p = Program.findProgram(file.getAbsolutePath());
            if (p == null) {
                log.debug("Opening unknown file: " + file.getAbsolutePath());
                return OSUtils.exec(unknownFileApp, file.getAbsolutePath());
            }
        }
        return true;
    }
    
    @Override
    public void enhanceFolderMenu(final File file, MenuItem menu, Menu submenu,
            FolderMenuItemListener listener) {
        if (folderNavigator == null || folderNavigator.length() == 0) return;
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
    public void enhanceSettings(TabFolder settingsTabFolder,
            List<AbstractSettingsTabItem> tabList) {
        openWithTab = new OpenWithSettingsTabItem(settingsTabFolder);
        tabList.add(openWithTab);
    }
   
}
