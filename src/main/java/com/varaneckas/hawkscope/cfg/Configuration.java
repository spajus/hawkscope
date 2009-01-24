package com.varaneckas.hawkscope.cfg;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.gui.swing.SwingMainMenu;
import com.varaneckas.hawkscope.util.PathUtils;

/**
 * Hawkscope configuration.
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class Configuration {
    
    /**
     * Logger 
     */
    private static final Log log = LogFactory.getLog(Configuration.class);
    
    /**
     * Display hidden files property name
     */
    public static final String HIDDEN_FILES_DISPLAYED = "display.hidden";

    /**
     * Display floppy drives
     */
    public static final String FLOPPY_DRIVES_DISPLAYED = "display.floppy";
    
    /**
     * Quick folder access list property name
     */
    public static final String QUICK_ACCESS_LIST = "quick.access.list";
    
    /**
     * Property name for blacklist of file/folder names that will be filtered out 
     */
    //TODO use it
    public static final String FILESYSTEM_BLACKLIST = "filesystem.blacklist";
    
    /**
     * GUI implementation
     */
    public static final String GUI_IMPLEMENTATION = "gui.implementation";
    
    /**
     * Delay for reloading menu after it was hidden (milliseconds)
     */
    public static final String MENU_RELOAD_DELAY = "menu.reload.delay";

    /**
     * Properties {@link Map}
     */
    private final Map<String, String> properties;
    
    /**
     * Properties changed flag. If true, ConfigurationFactory should persist
     * data to disk.
     */
    private boolean changed = false;
    
    /**
     * Tells if configuration is changed
     * 
     * @return changed
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * Constructor that initializes properties
     * 
     * @param properties map
     */
    public Configuration(final Map<String, String> properties) {
        this.properties = properties;
    }
    
    /**
     * Gets properties map
     */
    public Map<String, String> getProperties() {
        return properties;
    }
    
    /**
     * Tells if to display hidden files
     * 
     * @return display
     */
    public boolean isHiddenFilesDisplayed() {
        return properties.get(Configuration.HIDDEN_FILES_DISPLAYED).equals("1");
    }
    
    /**
     * Tells if to display floppy drives
     * 
     * @return display 
     */
    public boolean isFloppyDrivesDisplayed() {
        return properties.get(Configuration.FLOPPY_DRIVES_DISPLAYED).equals("1");
    }
    
    /**
     * Gets GUI Implementation
     * 
     * @return implementation name
     */
    public String getGuiImplementation() {
        return properties.get(Configuration.GUI_IMPLEMENTATION);
    }

    /**
     * Gets list of {@link File}s that can be accessed quickly from 
     * {@link SwingMainMenu}
     * 
     * @return list of files
     */
    public List<File> getQuickAccessList() {
        return PathUtils.pathToDirList(
                properties.get(Configuration.QUICK_ACCESS_LIST), ";");
    }
    
    /**
     * Gets menu reload delay. 3 seconds (3000) default.
     * 
     * @return menu reload delay in milliseconds
     */
    public long getMenuReloadDelay() {
        long millis = 3000L; //default in case of missing
        try {
            String delay = properties.get(Configuration.MENU_RELOAD_DELAY);
            if (delay != null && !delay.equals("")) {
                millis = Long.valueOf(delay).longValue();
            }
        } catch (final Exception e) {
            log.warn("Could not read " + MENU_RELOAD_DELAY 
                    + ", defaulting to 10 seconds: " + e.getMessage());
        }
        return millis;
    }
    
}
