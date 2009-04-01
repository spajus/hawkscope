/*
 * Copyright (c) 2008-2009 Tomas Varaneckas
 * http://www.varaneckas.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.varaneckas.hawkscope.util;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;

/**
 * {@link IconFactory} - SWT implmementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class IconFactory {

    /**
     * Singleton instance
     */
	private static final IconFactory instance = new IconFactory();
	
	/**
	 * Singleton constructor
	 */
	private IconFactory() {}
	
	/**
	 * Gets the singleton instance
	 * 
	 * @return
	 */
	public static IconFactory getInstance() {
		return instance;
	}
	
	/**
	 * Image Resource Pool
	 */
    private final Map<String, Image> resourcePool = new HashMap<String, Image>();
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(IconFactory.class);
    
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
        final Image i = new Image(display, IconFactory.class.getClassLoader()
                .getResourceAsStream("icons/" + name));
        resourcePool.put(name, i);
        return i;
    }
    
    /**
     * Gets icon with plugin's classloader
     * 
     * @param name Icon name with extension
     * @return icon
     */
    public Image getPluginIcon(final String name, final ClassLoader classLoader) {
        if (resourcePool.containsKey(name)) {
            return resourcePool.get(name);
        } 
        final Image i = new Image(display, classLoader
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
    public Image getFileSystemIcon(final File file) {
    	if (file.isDirectory() || !file.getName().contains(".")) {
    		return null;
    	}
        Image image = null;
        final Program p = Program.findProgram(file.getName()
                .replaceAll(".*\\.", "."));
        if (p != null) {
            if (resourcePool.containsKey(p.getName())) {
                return resourcePool.get(p.getName());
            }
            final ImageData data = p.getImageData();
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
        final String name = "HawkscopeTrayIcon";
        if (resourcePool.containsKey(name)) {
            resourcePool.get(name);
        }
        final Image trayIcon = new Image(display, IconFactory.class
                .getClassLoader().getResourceAsStream(getBestTrayIcon()));
        resourcePool.put(name, trayIcon);
        return trayIcon;
    }
    
    /**
     * Releases the resources
     */
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
    
    
    /**
     * Preloaded resources
     */
    protected static final Map<String, URL> resources = new HashMap<String, URL>();
    
    static {
        //initialize resources
        resources.put("drive", loadResource("hdd24.png"));
        resources.put("floppy", loadResource("fdd24.png"));
        resources.put("cdrom", loadResource("cdrom24.png"));
        resources.put("network", loadResource("network24.png"));
        resources.put("removable", loadResource("removable24.png"));
        resources.put("folder", loadResource("folder24.png"));
        resources.put("folder.open", loadResource("folder.open.24.png"));
        resources.put("file", loadResource("file24.png"));
        resources.put("executable", loadResource("executable24.png"));
        resources.put("exit", loadResource("exit24.png"));
        resources.put("hide", loadResource("down24.png"));
        resources.put("more", loadResource("more24.png"));
        resources.put("unknown", loadResource("unknown24.png"));  
        resources.put("about", loadResource("about24.png"));  
        resources.put("open", loadResource("open24.png")); 
        resources.put("empty", loadResource("empty24.png")); 
        resources.put("update", loadResource("update24.png"));
        resources.put("settings", loadResource("settings24.png"));
        resources.put("help", loadResource("help24.png"));
    }
    
    /**
     * Gets icon for {@link File}
     * 
     * @param targetFile any file
     * @return icon
     */    
    public Image getIcon(final File targetFile) {
        if (ConfigurationFactory.getConfigurationFactory().getConfiguration()
                .useOsIcons()) {
            final Image icon = getFileSystemIcon(targetFile);
            if (icon != null) {
                return icon;
            }
        }
        if (OSUtils.isFileSystemRoot(targetFile)) {
            if (OSUtils.isFloppyDrive(targetFile)) {
                return getIcon("floppy");
            }
            if (OSUtils.isOpticalDrive(targetFile)) {
                return getIcon("cdrom");
            } 
            if (OSUtils.isNetworkDrive(targetFile)) {
                return getIcon("network");
            }
            if (OSUtils.isRemovableDrive(targetFile)) {
                return getIcon("removable");
            }
            return getIcon("drive");
        } else if (targetFile.isFile()) {
        	if (OSUtils.isExecutable(targetFile)) {
        	    return getIcon("executable");
        	}
            return getIcon("file");
        } else if (targetFile.isDirectory()) {
            //mac app
            if (OSUtils.CURRENT_OS.equals(OSUtils.OS.MAC) 
                    && targetFile.getName().endsWith(".app")) {
                return getIcon("executable");  
            } 
            return getIcon("folder");
        } else {
            return getIcon("unknown");
        }
    }
    
    /**
     * Loads a resource with help of active classloader
     * 
     * @param name resource name
     * @return
     */
    private static URL loadResource(final String name) {
        final Configuration cfg = ConfigurationFactory.getConfigurationFactory()
            .getConfiguration();
        try {
            String theme = cfg.getIconsTheme();
            if (theme.equals("default")) {
                theme = "";
            } else {
                theme += "/";
            }
            URL resource = IconFactory.class.getClassLoader()
                .getResource("icons/".concat(theme).concat(name));
            if (!theme.equals("")) {
                if (resource == null) {
                    resource = IconFactory.class.getClassLoader()
                        .getResource("icons/".concat(name));
                } else {
                    log.debug("File exists: " + name);
                }
            }
            return resource;
        } catch (final Exception e) {
            log.warn("Failed loading icon resource: " + name, e);
        }
        return null;
    }

    /**
     * Gets best sized tray icon name for current setup
     * 
     * @return tray icon name
     */
    protected String getBestTrayIcon() {
        final float height = OSUtils.getTrayIconSize();
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
        sizes = null;
        final String res = "icons/hawkscope".concat(String.valueOf(best))
                .concat(".png");
        if (log.isDebugEnabled()) {
            log.debug("Chose best icon for " + (int) height 
                    + " pixel tray: " + res);
        }
        return res;
    }   
}
