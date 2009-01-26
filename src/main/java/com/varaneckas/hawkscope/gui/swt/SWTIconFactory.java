package com.varaneckas.hawkscope.gui.swt;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;

import com.varaneckas.hawkscope.util.IconFactory;

/**
 * {@link IconFactory} - SWT implmementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTIconFactory extends IconFactory<Image> {
    
    private final Map<String, Image> resourcePool = new HashMap<String, Image>();
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(SWTIconFactory.class);
    
    /**
     * Default {@link Display}
     */
    private static final Display display = Display.getDefault();

    /**
     * Gets {@link Icon} for name
     * 
     * @param name icon name
     * @return icon
     */
    public Image getIcon(final String name) {
        try {
            if (resourcePool.containsKey(name)) {
                return resourcePool.get(name);
            } 
            final Image i = new Image(display, resources.get(name).openStream());
            resourcePool.put(name, i);
            return i;
        } catch (final Exception e) {
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
        if (resourcePool.containsKey(name)) {
            return resourcePool.get(name);
        } 
        final Image i = new Image(display, SWTIconFactory.class.getClassLoader()
                .getResourceAsStream("icons/" + name));
        resourcePool.put(name, i);
        return i;
    }

    /**
     * Gets large file system icon for any file
     * 
     * @param file source
     * @return icon
     */
    @Override
    public Image getFileSystemIcon(final File file) {
        Image image = null;
        Program p = Program.findProgram(file.getName().replaceAll(".*\\.", "."));
        if (p != null) {
            if (resourcePool.containsKey(p.getName())) {
                return resourcePool.get(p.getName());
            }
            ImageData data = p.getImageData();
            if (data != null) {
                image = new Image(display, data);
                resourcePool.put(p.getName(), image);
            }
        }
        return image;
    }
    
    /**
     * Gets Hawkscope Tray Icon of best size
     * 
     * @return tray icon
     */
    public Image getTrayIcon() {
        String name = "HawkscopeTrayIcon";
        if (resourcePool.containsKey(name)) {
            resourcePool.get(name);
        }
        final Image trayIcon = new Image(display, SWTIconFactory.class.getClassLoader()
                .getResourceAsStream(getBestTrayIcon()));
        resourcePool.put(name, trayIcon);
        return trayIcon;
    }
    
    @Override
    public synchronized void cleanup() {
        for (final String im : resourcePool.keySet()) {
            try {
                log.debug("Releasing icon: " + im);
                resourcePool.get(im).dispose();
            } catch (final Exception e) {
                log.debug("Failed releasing icon", e);
            }
        }        
    }
}
