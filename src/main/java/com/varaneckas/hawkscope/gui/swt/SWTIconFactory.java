package com.varaneckas.hawkscope.gui.swt;

import java.io.File;
import java.io.IOException;

import javax.swing.Icon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.varaneckas.hawkscope.util.IconFactory;

public class SWTIconFactory extends IconFactory<Image> {

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(SWTIconFactory.class);
    
    private static final Display display = Display.getDefault();


    
    /**
     * Gets {@link Icon} for name
     * 
     * @param name icon name
     * @return icon
     */
    public Image getIcon(final String name) {
        try {
            return new Image(display, resources.get(name).openStream());
        } catch (IOException e) {
            log.error("Failed getting icon: " + name, e);
        }
        return null;
    }
    
    /**
     * Gets uncached icon
     * 
     * @param name Icon name with extension
     * @return icon
     */
    public Image getUncachedIcon(final String name) {
        return new Image(display, ClassLoader.getSystemClassLoader()
                .getResourceAsStream("icons/" + name));
    }

    /**
     * Gets large file system icon for any file
     * 
     * @param file source
     * @return icon
     */
    public static Image getFileSystemIcon(final File file) {
        return null;
    }
    
    /**
     * Gets Hawkscope Tray Icon of best size
     * 
     * @return tray icon
     */
    public Image getTrayIcon() {
        return new Image(display, ClassLoader.getSystemClassLoader()
                .getResourceAsStream(getBestTrayIcon()));
    }
    

    
}
