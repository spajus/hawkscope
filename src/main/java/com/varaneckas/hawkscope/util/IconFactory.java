package com.varaneckas.hawkscope.util;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.awt.shell.ShellFolder;

/**
 * Icon Factory
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class IconFactory {

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(IconFactory.class);
    
    /**
     * Collection of flyweight icons that are ready for use
     */
    private static final Map<String, Icon> icons = new HashMap<String, Icon>();

    static {
        try {
            //initialize icons
            icons.put("drive",  new ImageIcon(ClassLoader.getSystemClassLoader().getResource("icons/hdd24.png")));
            icons.put("floppy",  new ImageIcon(ClassLoader.getSystemClassLoader().getResource("icons/fdd24.png")));
            icons.put("folder", new ImageIcon(ClassLoader.getSystemClassLoader().getResource("icons/folder24.png")));
            icons.put("folder.open", new ImageIcon(ClassLoader.getSystemClassLoader().getResource("icons/folder.open.24.png")));
            icons.put("file",   new ImageIcon(ClassLoader.getSystemClassLoader().getResource("icons/file24.png")));
            icons.put("exit",   new ImageIcon(ClassLoader.getSystemClassLoader().getResource("icons/exit24.png")));
            icons.put("hide",   new ImageIcon(ClassLoader.getSystemClassLoader().getResource("icons/down24.png")));
            icons.put("more",   new ImageIcon(ClassLoader.getSystemClassLoader().getResource("icons/more24.png")));
            icons.put("unknown",new ImageIcon(ClassLoader.getSystemClassLoader().getResource("icons/unknown24.png")));  
            icons.put("about",  new ImageIcon(ClassLoader.getSystemClassLoader().getResource("icons/about24.png")));  
            icons.put("empty",  new ImageIcon(ClassLoader.getSystemClassLoader().getResource("icons/empty24.png"))); 
        } catch (final Exception e) {
            log.warn("Cannot find icon", e);
        }
    }
    
    /**
     * Gets {@link Icon} for name
     * 
     * @param name icon name
     * @return icon
     */
    public static Icon getIcon(final String name) {
        return icons.get(name);
    }
    
    /**
     * Gets uncached icon
     * 
     * @param name Icon name with extension
     * @return icon
     */
    public static Icon getUncachedIcon(final String name) {
        return new ImageIcon(ClassLoader.getSystemClassLoader()
                .getResource("icons/" + name));
    }
    
    /**
     * Gets {@link Icon} for {@link File}
     * 
     * @param file any file
     * @return icon
     */
    public static Icon getIcon(final File file) {
        if (ShellFolder.isFileSystemRoot(file)) {
            if (FileSystemView.getFileSystemView().isFloppyDrive(file)) {
                return getIcon("floppy");
            }
            return getIcon("drive");
        } else if (file.isFile()) {
            return getIcon("file");
        } else if (file.isDirectory()) {
            return getIcon("folder");
        } else {
            return getIcon("unknown");
        }
    }
    
    /**
     * Gets large file system icon for any file
     * 
     * @param file source
     * @return icon
     */
    public static Icon getFileSystemIcon(final File file) {
        try {
            return new ImageIcon(ShellFolder.getShellFolder(file).getIcon(true));
        } catch (final Exception e) {
            log.warn("Could not get system icon for file: " + file.getName());
        }
        return null;
    }
    
    /**
     * Gets Hawkscope Tray Icon of best size
     * 
     * @return tray icon
     */
    public static TrayIcon getTrayIcon() {
        final String trayIconImage = getBestTrayIcon();
        final URL iconURL = ClassLoader.getSystemClassLoader()
                .getResource(trayIconImage);
        if (iconURL == null) {
            throw new RuntimeException("Could not find tray icon " +
                    "image: " + trayIconImage);
        }
        return new TrayIcon(new ImageIcon(iconURL).getImage());
    }
    
    /**
     * Gets best sized tray icon name for current setup
     * 
     * @return tray icon name
     */
    private static String getBestTrayIcon() {
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
