package com.varaneckas.hawkscope.util;

import java.awt.SystemTray;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.awt.shell.ShellFolder;

import com.varaneckas.hawkscope.gui.swt.SWTIconFactory;

/**
 * Icon object factory
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 * @param <IconType> Icon class of the implementation
 */
public abstract class IconFactory<IconType> {

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(IconFactory.class);
    
    @SuppressWarnings("unchecked")
    private static IconFactory instance = null;
    
    /**
     * Preloaded resources
     */
    protected static final Map<String, URL> resources = new HashMap<String, URL>();
    
    static {
        try {
            //initialize resources
            resources.put("drive",  IconFactory.class.getClassLoader().getResource("icons/hdd24.png"));
            resources.put("floppy",  IconFactory.class.getClassLoader().getResource("icons/fdd24.png"));
            resources.put("folder", IconFactory.class.getClassLoader().getResource("icons/folder24.png"));
            resources.put("folder.open", IconFactory.class.getClassLoader().getResource("icons/folder.open.24.png"));
            resources.put("file",   IconFactory.class.getClassLoader().getResource("icons/file24.png"));
            resources.put("exit",   IconFactory.class.getClassLoader().getResource("icons/exit24.png"));
            resources.put("hide",   IconFactory.class.getClassLoader().getResource("icons/down24.png"));
            resources.put("more",   IconFactory.class.getClassLoader().getResource("icons/more24.png"));
            resources.put("unknown", IconFactory.class.getClassLoader().getResource("icons/unknown24.png"));  
            resources.put("about",  IconFactory.class.getClassLoader().getResource("icons/about24.png"));  
            resources.put("open",  IconFactory.class.getClassLoader().getResource("icons/open24.png")); 
            resources.put("empty",  IconFactory.class.getClassLoader().getResource("icons/empty24.png")); 
            resources.put("update", IconFactory.class.getClassLoader().getResource("icons/update24.png"));
            resources.put("settings", IconFactory.class.getClassLoader().getResource("icons/settings24.png"));
        } catch (final Exception e) {
            log.warn("Cannot find icon", e);
        }
    }
    
    
    /**
     * Gets the {@link IconFactory} instance
     * 
     * @param <T> Icon class
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> IconFactory<T> getIconFactory() {
        if (instance == null) {
            instance = new SWTIconFactory();
        }
        return (IconFactory<T>) instance;
    }
    
    /**
     * Gets the icon object
     * 
     * @param icon
     * @return
     */
    abstract public IconType getIcon(final String icon);
    
    /**
     * Gets uncached icon object
     * 
     * @param iconFile
     * @return
     */
    abstract public IconType getUncachedIcon(final String iconFile);

    /**
     * Gets Hawkscope Tray Icon object
     * 
     * @return
     */
    abstract public Object getTrayIcon();
    
    /**
     * Gets icon for {@link File}
     * 
     * @param targetFile any file
     * @return icon
     */    
    public IconType getIcon(final File targetFile) {
        if (ShellFolder.isFileSystemRoot(targetFile)) {
            if (FileSystemView.getFileSystemView().isFloppyDrive(targetFile)) {
                return getIcon("floppy");
            }
            return getIcon("drive");
        } else if (targetFile.isFile()) {
            return getIcon("file");
        } else if (targetFile.isDirectory()) {
            return getIcon("folder");
        } else {
            return getIcon("unknown");
        }
    }
    
    /**
     * Gets best sized tray icon name for current setup
     * 
     * @return tray icon name
     */
    protected String getBestTrayIcon() {
        float height = 24;
        if (SystemTray.isSupported()) {
            height = SystemTray.getSystemTray().getTrayIconSize().height;
        }
        int[] sizes = new int[] { 64, 48, 32, 24, 16 };
        int best = 64;
        for (int i = 0; i < sizes.length; i++) {
            if (sizes[i] / height >= 1) {
                best = sizes[i];
            }
            else {
                break;
            }
        }
        final String res = "icons/hawkscope" + best + ".png";
        if (log.isDebugEnabled()) {
            log.debug("Chose best icon for " + (int) height 
                    + " pixel tray: " + res);
        }
        return res;
    }    
    
}
