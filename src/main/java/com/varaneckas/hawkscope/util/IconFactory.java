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

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.swing.SwingIconFactory;
import com.varaneckas.hawkscope.gui.swt.SWTIconFactory;

public abstract class IconFactory<IconType> {

    private static final Log log = LogFactory.getLog(IconFactory.class);
    
    private static IconFactory instance = null;
    
    /**
     * Collection of flyweight icons that are ready for use
     */
    private final Map<String, IconType> icons = new HashMap<String, IconType>();

    protected static final Map<String, URL> resources = new HashMap<String, URL>();
    
    static {
        try {
            //initialize resources
            resources.put("drive",  ClassLoader.getSystemClassLoader().getResource("icons/hdd24.png"));
            resources.put("floppy",  ClassLoader.getSystemClassLoader().getResource("icons/fdd24.png"));
            resources.put("folder", ClassLoader.getSystemClassLoader().getResource("icons/folder24.png"));
            resources.put("folder.open", ClassLoader.getSystemClassLoader().getResource("icons/folder.open.24.png"));
            resources.put("file",   ClassLoader.getSystemClassLoader().getResource("icons/file24.png"));
            resources.put("exit",   ClassLoader.getSystemClassLoader().getResource("icons/exit24.png"));
            resources.put("hide",   ClassLoader.getSystemClassLoader().getResource("icons/down24.png"));
            resources.put("more",   ClassLoader.getSystemClassLoader().getResource("icons/more24.png"));
            resources.put("unknown",ClassLoader.getSystemClassLoader().getResource("icons/unknown24.png"));  
            resources.put("about",  ClassLoader.getSystemClassLoader().getResource("icons/about24.png"));  
            resources.put("open",  ClassLoader.getSystemClassLoader().getResource("icons/open24.png")); 
            resources.put("empty",  ClassLoader.getSystemClassLoader().getResource("icons/empty24.png")); 
        } catch (final Exception e) {
            log.warn("Cannot find icon", e);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    public static <T> IconFactory<T> getIconFactory() {
        if (instance == null) {
            if (ConfigurationFactory.getConfigurationFactory()
                    .getConfiguration().getGuiImplementation().equals("SWT")) {
                instance = new SWTIconFactory();
            } else {
                instance = new SwingIconFactory();
            }
        }
        return (IconFactory<T>) instance;
    }
    
    abstract public IconType getIcon(final String icon);
    
    abstract public IconType getUncachedIcon(final String iconFile);

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
        float height = SystemTray.getSystemTray().getTrayIconSize().height;
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
