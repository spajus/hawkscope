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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Text;

/**
 * Operating System specific utilities.
 * 
 * Used for places where different OS acts differently.
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class OSUtils {
	
    /**
     * Logger
     */
	private static final Log log = LogFactory.getLog(OSUtils.class);

	/**
	 * Supported Operating Systems
	 * 
	 * @author Tomas Varaneckas
	 * @version $Id$
	 */
	public enum OS {
		MAC, WIN, UNIX, UNKNOWN
	}
	
	/**
	 * Current Operating System
	 */
	public static final OS CURRENT_OS = getCurrentOS();

	/**
	 * FileSystemView (don't use with Mac)
	 */
	private static final FileSystemView fsw = getFileSystemView();

	/**
	 * Gets the current operating system
	 * 
	 * @return
	 */
	private static OS getCurrentOS() {
		final String os = System.getProperty("os.name", "unknown").toLowerCase();
		if (os.startsWith("win")) {
			return OS.WIN;
		}
		if (os.startsWith("mac")) {
			return OS.MAC;
		}
		if (os.startsWith("linux") || os.startsWith("unix")
				|| os.startsWith("bsd")) {
			return OS.UNIX;
		}
		return OS.UNKNOWN;
	}

	/**
	 * Gets FileSystemView for non-macs
	 * 
	 * @return
	 */
	private static FileSystemView getFileSystemView() {
	    if (!CURRENT_OS.equals(OS.MAC)) {
	        return FileSystemView.getFileSystemView();
	    }
        return null;
    }

    /**
	 * Gets the tray icon size
	 * 
	 * @return
	 */
	public static int getTrayIconSize() {
		//experimental
		if (System.getProperty("java.version").compareTo("1.6") >= 0) {
			try {
				log.debug("Java > 1.6, trying java.awt.SystemTray");
				final Class<?> systemTrayClass = Class.forName(
				        "java.awt.SystemTray");
				Method m = systemTrayClass.getMethod("getSystemTray", 
				        new Class[] {});
				final Object systemTray = m.invoke(systemTrayClass, 
				        new Object[] {});
				m = systemTray.getClass().getMethod("getTrayIconSize", 
				        new Class[] {});
				final Object size = m.invoke(systemTray, new Object[] {});
				m = null;
				return ((java.awt.Dimension) size).height;
			} catch (final Exception e) {
				log.warn("Failed calling java.awt.SystemTray object", e);
			}
		}
		if (CURRENT_OS.equals(OS.WIN)) {
			return 16;
		}
		if (CURRENT_OS.equals(OS.UNIX)) {
			return 24;
		}
		if (CURRENT_OS.equals(OS.MAC)) {
			return 16;
		}
		return 16;
	}

	/**
	 * Gets the system tray (menubar, panel, whatever) size
	 * 
	 * @return
	 */
	public static int getTraySize() {
		if (CURRENT_OS.equals(OS.MAC)) {
			return 26;
		}
		return getTrayIconSize();
	}

	/**
	 * Gets file display name according to local OS
	 * 
	 * @param file
	 * @return
	 */
	public static String getSystemDisplayName(final File file) {
		if (!CURRENT_OS.equals(OS.MAC)) {
			return fsw.getSystemDisplayName(file);
		}
		return null;
	}

	/**
	 * Tells if {@link File} is a floppy drive
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isFloppyDrive(final File file) {
		if (CURRENT_OS.equals(OS.WIN)) {
			return fsw.isFloppyDrive(file);
		}
		return false;
	}

	/**
	 * Tells if {@link File} is a file system root
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isFileSystemRoot(final File file) {
		switch (CURRENT_OS) {
		case MAC: {
		    if (file.getAbsolutePath().equals("/") || file.getAbsolutePath()
		            .matches("^/Volumes/[^/]+$")) {
		        return true;
		    }
		    return false;
		}
		case UNIX: {
		    if (file.getAbsolutePath().equals("/") 
		            || file.getAbsolutePath().matches("^/(media|mnt)/[^/]+$")) {
		        return true;
		    }
		    return false;
		}
		default: 
		    return fsw.isFileSystemRoot(file);
		}
	}

	/**
	 * Gets the File System Roots
	 * 
	 * @return
	 */
	public static List<File> getFileSystemRoots() {
		final List<File> roots = new LinkedList<File>();
		switch (CURRENT_OS) {
		case MAC: {
			final File[] volumes = new File("/Volumes").listFiles();
			if (volumes.length == 0) {
				//something's wrong..
				roots.add(new File("/"));
			} else {
				for (final File f : volumes) {
					if (f.canRead() && !isMacApp(f)) {
					    roots.add(f);
					}
				}
			}
			return roots;
		}
		case UNIX:
			roots.add(new File("/"));
			//Ubuntu
			File media = new File("/media");
			if (!media.exists()) {
			    //Old School Unix fallback
			    media = new File("/mnt");
			} 
			if (media.exists()) {
    			for (final File f : media.listFiles()) {
    				if (f.isDirectory() && f.canRead()) {
    					roots.add(f);
    				}
    			}
			}
			return roots;
		default: {
			return Arrays.asList(File.listRoots());
		}
		}
	}
	
	/**
	 * Tells if {@link File} is an optical drive
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isOpticalDrive(final File file) {
	    if (!file.isDirectory()) {
	        return false;
	    }
	    String name = null;
	    if (CURRENT_OS.equals(OS.WIN)) {
	        name = fsw.getSystemDisplayName(file)
	                .toLowerCase();
	    }
	    if (name == null) {
	        name = file.getName().toLowerCase();
	    }
	    return name.matches(".*(cdrom|dvd).*");
	}
	
	/**
	 * Tells if file is a network drive
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isNetworkDrive(final File file) {
	    if (!file.isDirectory()) {
	        return false;
	    }
	    if (isMacApp(file)) {
	        return false;
	    }
	    if (CURRENT_OS.equals(OS.WIN)) {
	        return fsw.isComputerNode(file);
	    } 
	    return file.getAbsolutePath().toLowerCase()
	        .matches(".*(server|network|remote).*");
	}

	/**
	 * Tells if file is a removable drive
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isRemovableDrive(final File file) {
	    if (!file.isDirectory()) {
	        return false;
	    }
	    if (isMacApp(file)) {
	        return false;
	    }
        String name = null;
        if (CURRENT_OS.equals(OS.WIN)) {
            name = fsw.getSystemDisplayName(file).toLowerCase();
        }
        if (name == null) {
            name = file.getName().toLowerCase();
        }
        return name.matches(".*(usb|flash|removable).*");
	}
	
	/**
	 * Tells if file is executable
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isExecutable(final File file) {
	    final String filePath = file.getAbsolutePath().toLowerCase();
	    switch (CURRENT_OS) {
        case WIN:
            if (filePath.endsWith(".exe") 
                    || filePath.endsWith(".bat")
                    || filePath.endsWith(".msi")) {
                
                return true;
            } else {
                return false;
            }
        default:
            if (System.getProperty("java.version").compareTo("1.6") >= 0) {
                //we can check if it's executable
                try {
                    Method m = file.getClass().getMethod("canExecute", 
                            new Class[] {});
                    if (m != null) { 
                        final boolean result = (Boolean) m.invoke(file, 
                                new Object[] {}); 
                        m = null;
                        return result;
                    }
                } catch (final Exception e) {
                    log.warn("Failed dynamically calling File.canExecute", e);
                    return false;
                }
            } 
            return filePath.contains("/bin/");
        }
	}
	
	/**
	 * Tells if file is a Mac application
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isMacApp(final File file) {
	    if (CURRENT_OS.equals(OS.MAC) && file != null) {
	        return file.isDirectory() 
	            && file.getName().toLowerCase().endsWith(".app");
	    } 
	    return false;
	}

	/**
	 * Executes app with parameters
	 * 
	 * @param app command
	 * @param params parameters
	 * @return needs further execution? (when failed, returns true, otherwise false)
	 */
	public static boolean exec(String app, String params) {
		try {
			switch (CURRENT_OS) {
			case MAC:
				if (app.toLowerCase().endsWith(".app")) {
					app = "open -a " + app;
				}
				break;
			case WIN:
			    if (app.contains(" ")) {
			        app = "\"" + app + "\"";
			    }
			    params = "\"" + params + "\"";
			    break;
			}
			if (log.isDebugEnabled()) {
				log.debug("Executing: " + app + " " + params);
			}
			Runtime.getRuntime().exec(app + " " + params);
			return false;
		} catch (final Exception e) {
			log.warn("Failed executing app " + app + " with params: " 
			        + params, e);
		}
		return true;
	}
	
	/**
	 * Sleep for some milliseconds. No exceptions.
	 * 
	 * @param millis 
	 */
	public static void sleep(final long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			log.warn("Thread sleep interrupted", e);
		}
	}

	/**
	 * Gets SWT {@link Text} top offset adjustment. Apply manually.
	 * 
	 * @return
	 */
    public static int getTextTopOffsetAdjust() {
        switch (CURRENT_OS) {
        case WIN: 
            return -2;
        case MAC:
        	return -7;
        default:
            return -5;
        }
    }

    /**
     * Gets platform friendly fixed font name
     * 
     * @return
     */
	public static String getFixedFontName() {
		switch (CURRENT_OS) {
		case MAC:
			return "Monaco";
		default:
			return "Courier New";
		}
	}
}
