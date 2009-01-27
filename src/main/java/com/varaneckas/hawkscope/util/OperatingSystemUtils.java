package com.varaneckas.hawkscope.util;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;

public abstract class OperatingSystemUtils {
	
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
		if (os.startsWith("linux") || os.startsWith("unix") || os.startsWith("bsd")) {
			return OS.UNIX;
		}
		return OS.UNKNOWN;
	}
	
	public static int getTrayIconSize() {
		//FIXME use where possible!
		//SystemTray.getSystemTray().getTrayIconSize();
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
		if (!System.getProperty("os.name").startsWith("Mac")) {
			final FileSystemView fsw = FileSystemView.getFileSystemView();
			return fsw.getSystemDisplayName(file);
    	}
		return null;
	}

	public static boolean isFloppyDrive(final File file) {
		if (System.getProperty("os.name").startsWith("Win")) {
			return FileSystemView.getFileSystemView().isFloppyDrive(file);
		} 
		return false;
	}
	
	public static boolean isFileSystemRoot(final File file) {
		if (System.getProperty("os.name").startsWith("Mac")) {
			if (file.getAbsolutePath().equals("/")) {
				return true;
			}
			return false;
		}  
		return FileSystemView.getFileSystemView().isFileSystemRoot(file);
	}
	
	public static void adjustButton(final Button button) {
		if (!System.getProperty("os.name").startsWith("Mac")) {
			return;
		}
		final Object layout = button.getLayoutData();
		int offset = 15;
		if (layout instanceof FormData) {
			((FormData) layout).width += offset;
			((FormData) layout).left.offset -= offset / 2;
		}
	}
	                                                  
	
	
}
