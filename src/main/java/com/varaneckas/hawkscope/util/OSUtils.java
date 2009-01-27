package com.varaneckas.hawkscope.util;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;

public abstract class OSUtils {

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
		// FIXME use where possible!
		// SystemTray.getSystemTray().getTrayIconSize();
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
		if (!CURRENT_OS.equals(OS.MAC)) {
			return;
		}
		final Object layout = button.getLayoutData();
		int offset = 15;
		if (layout instanceof FormData) {
			((FormData) layout).width += offset;
			((FormData) layout).left.offset -= offset / 2;
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
}
