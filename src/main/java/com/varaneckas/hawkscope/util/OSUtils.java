package com.varaneckas.hawkscope.util;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;

public abstract class OSUtils {
	
	private static final Log log = LogFactory.getLog(OSUtils.class);

	public enum OS {
		MAC, WIN, UNIX, UNKNOWN
	}

	public static final OS CURRENT_OS = getCurrentOS();

	private static OS getCurrentOS() {
		String os = System.getProperty("os.name", "unknown").toLowerCase();
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

	public static int getTrayIconSize() {
		//experimental
		if (System.getProperty("java.version").compareTo("1.6") >= 0) {
			try {
				log.debug("Java > 1.6, trying java.awt.SystemTray");
				Class<?> systemTrayClass = Class.forName("java.awt.SystemTray");
				Method m = systemTrayClass.getMethod("getSystemTray", new Class[] {});
				Object systemTray = m.invoke(systemTrayClass, new Object[] {});
				m = systemTray.getClass().getMethod("getTrayIconSize", new Class[] {});
				Object size = m.invoke(systemTray, new Object[] {});
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

	public static int getTraySize() {
		if (CURRENT_OS.equals(OS.MAC)) {
			return 26;
		}
		return getTrayIconSize();
	}

	public static String getSystemDisplayName(final File file) {
		if (!CURRENT_OS.equals(OS.MAC)) {
			final FileSystemView fsw = FileSystemView.getFileSystemView();
			return fsw.getSystemDisplayName(file);
		}
		return null;
	}

	public static boolean isFloppyDrive(final File file) {
		if (CURRENT_OS.equals(OS.WIN)) {
			return FileSystemView.getFileSystemView().isFloppyDrive(file);
		}
		return false;
	}

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
		    return FileSystemView.getFileSystemView().isFileSystemRoot(file);
		}
	}

	public static void adjustButton(final Button button) {
		if (!CURRENT_OS.equals(OS.MAC) || button == null) {
			return;
		}
		final Object layout = button.getLayoutData();
		int offset = 15;
		if (layout instanceof FormData) {
			((FormData) layout).width += offset;
			if (((FormData) layout).left != null) {
				((FormData) layout).left.offset -= offset / 2;
			}
		}
	}

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
					if (f.canRead()) {
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
	
	public static boolean isOpticalDrive(final File file) {
	    if (!file.isDirectory()) {
	        return false;
	    }
	    String name = null;
	    if (CURRENT_OS.equals(OS.WIN)) {
	        name = FileSystemView.getFileSystemView().getSystemDisplayName(file)
	                .toLowerCase();
	    }
	    if (name == null) {
	        name = file.getName().toLowerCase();
	    }
	    return name.matches(".*(cdrom|dvd).*");
	}
	
	public static boolean isNetworkDrive(final File file) {
	    if (!file.isDirectory()) {
	        return false;
	    }
	    if (CURRENT_OS.equals(OS.WIN)) {
	        return FileSystemView.getFileSystemView().isComputerNode(file);
	    } 
	    return file.getAbsolutePath().toLowerCase()
	        .matches(".*(server|network|remote).*");
	}

	public static boolean isRemovableDrive(final File file) {
	    if (!file.isDirectory()) {
	        return false;
	    }
        String name = null;
        if (CURRENT_OS.equals(OS.WIN)) {
            name = FileSystemView.getFileSystemView().getSystemDisplayName(file)
                    .toLowerCase();
        }
        if (name == null) {
            name = file.getName().toLowerCase();
        }
        return name.matches(".*(usb|flash|removable).*");
	}
	
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
                    Method m = file.getClass().getMethod("canExecute", new Class[] {});
                    if (m != null) { 
                        return ((Boolean) m.invoke(file, new Object[] {}));
                        
                    }
                } catch (final Exception e) {
                    log.warn("Failed dynamically calling File.canExecute", e);
                    return false;
                }
            } 
            return filePath.contains("/bin/");
        }
	}
	
	public static boolean isMacApp(final File file) {
	    if (CURRENT_OS.equals(OS.MAC) && file != null) {
	        return file.isDirectory() 
	            && file.getName().toLowerCase().endsWith(".app");
	    } 
	    return false;
	}

	public static boolean exec(String app, String params) {
		try {
			switch (CURRENT_OS) {
			case MAC:
				if (app.toLowerCase().endsWith(".app")) {
					app = "open -a " + app;
				}
			default:
				if (log.isDebugEnabled()) {
					log.debug("Executing: " + app + " " + params);
				}
				Runtime.getRuntime().exec(app + " " + params);
				return false;
			}
		} catch (final Exception e) {
			log.warn("Failed executing app " + app + " with params: " + params, e);
		}
		return true;
	}
}
