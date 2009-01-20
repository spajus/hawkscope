package com.varaneckas.hawkscope.gui.swing;

import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.awt.shell.ShellFolder;

import com.varaneckas.hawkscope.util.IconFactory;

/**
 * {@link IconFactory} for Swing GUI implementation
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SwingIconFactory extends IconFactory<Icon> {

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(SwingIconFactory.class);
    
    /**
     * Gets {@link Icon} for name
     * 
     * @param name icon name
     * @return icon
     */
    public Icon getIcon(final String name) {
        return new ImageIcon(resources.get(name));
    }
    
    /**
     * Gets uncached icon
     * 
     * @param name Icon name with extension
     * @return icon
     */
    public Icon getUncachedIcon(final String name) {
        return new ImageIcon(ClassLoader.getSystemClassLoader()
                .getResource("icons/" + name));
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
    public Image getTrayIcon() {
        final String trayIconImage = getBestTrayIcon();
        final URL iconURL = ClassLoader.getSystemClassLoader()
                .getResource(trayIconImage);
        if (iconURL == null) {
            throw new RuntimeException("Could not find tray icon " +
                    "image: " + trayIconImage);
        }
        return new ImageIcon(iconURL).getImage();
    }
    
}
