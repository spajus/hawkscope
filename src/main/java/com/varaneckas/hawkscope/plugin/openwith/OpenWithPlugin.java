package com.varaneckas.hawkscope.plugin.openwith;

import java.io.File;
import java.io.IOException;
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
        for (String key : cfg.getProperties().keySet()) {
            if (key.startsWith("plugin.openwith.type.")) {
                apps.put(key.replaceFirst("plugin.openwith.type.", ""), 
                        cfg.getProperties().get(key));
            }
        }
        folderNavigator = cfg.getProperties().get("plugin.openwith.folder.navigator");
    }
    
    @Override
    public boolean interceptClick(File file) {
        String ext = file.getName().replaceAll(".*\\.", ".");
        if (apps.containsKey(ext)) {
            try {
                Runtime.getRuntime().exec(apps.get(ext) + " " + file.getAbsolutePath());
                return false;
            } catch (IOException e) {
                log.warn("Failed opening file: " + file.getAbsolutePath() 
                        + " with program: " + apps.get(ext));
            }
        }
        return true;
    }
    
    @Override
    public void enhanceFolderMenu(final File file, MenuItem menu, Menu submenu,
            FolderMenuItemListener listener) {
        if (folderNavigator == null || folderNavigator.length() == 0) return;
        log.debug("open with...");
        MenuItem open = submenu.getItem(0);
        
        open.removeListener(SWT.Selection, 
                open.getListeners(SWT.Selection)[0]);
        
        open.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event ev) {
                try {
                    Runtime.getRuntime().exec(folderNavigator + " " + file.getAbsolutePath());
                } catch (IOException e) {
                    log.warn("Failed opening folder: " 
                            + file.getAbsolutePath() + " with navigator: " + folderNavigator);
                    Program.launch(file.getAbsolutePath());
                }
            }
        });
        
        
//        ExecutableMenuItem openWith = new ExecutableMenuItem();
//        openWith.setIcon(IconFactory.getInstance().getIcon("open"));
//        openWith.setText("Open With...");
//        openWith.setCommand(new AboutCommand());
//        openWith.createMenuItem(submenu);
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
