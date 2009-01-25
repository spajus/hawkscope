package com.varaneckas.hawkscope.gui.swt;

import java.io.File;

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
            return new Image(display, resources.get(name).openStream());
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
        return new Image(display, SWTIconFactory.class.getClassLoader()
                .getResourceAsStream("icons/" + name));
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
        Program p = Program.findProgram(file.getName());
        if (p != null) {
            ImageData data = p.getImageData();
            if (data != null) {
                image = new Image(display, data);
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
        return new Image(display, SWTIconFactory.class.getClassLoader()
                .getResourceAsStream(getBestTrayIcon()));
    }
}
