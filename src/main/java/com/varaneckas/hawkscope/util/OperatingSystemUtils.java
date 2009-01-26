package com.varaneckas.hawkscope.util;

import java.awt.Dimension;
import java.io.File;

import javax.swing.filechooser.FileSystemView;

public abstract class OperatingSystemUtils {
	
	public static Dimension getTrayIconSize() {
		//SystemTray.getSystemTray().getTrayIconSize();
		return new Dimension(16, 16);
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
	
}
