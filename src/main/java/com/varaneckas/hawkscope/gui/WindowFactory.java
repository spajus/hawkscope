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
package com.varaneckas.hawkscope.gui;

import java.lang.Thread.UncaughtExceptionHandler;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.gui.settings.SettingsWindow;

/**
 * Window factory for switching among multiple GUI implementations
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class WindowFactory {
	
	/**
	 * About Window
	 */
	private static AboutWindow aboutWindow;
	
	/**
	 * Settings Window
	 */
	private static SettingsWindow settingsWindow;
	
    /**
     * Initializes the application GUI 
     */
    public static void initialize() {
        Display.setAppName(Version.APP_NAME);
    }
    
    /**
     * Gets the About Window
     * 
     * @return instance of About Window
     */
    public static AbstractWindow getAboutWindow() {
    	if (aboutWindow == null || aboutWindow.shell.isDisposed()) {
    		aboutWindow = new AboutWindow();
    	}
        return aboutWindow;
    }
    
    /**
     * Makes a shell appear in center of the screen
     * 
     * @param target target shell
     */
    public static void centerShell(final Shell target) {
    	final Point size = target.getSize();
    	final Rectangle bounds = target.getDisplay().getPrimaryMonitor()
    	        .getBounds();
    	final int x = bounds.width / 2 - size.x / 2;
    	final int y = bounds.height / 2 - size.y / 2;
    	target.setLocation(x, y);
    }
    
    /**
     * Gets the Settings Window
     * 
     * @return instance of Settings Window
     */
    public static SettingsWindow getSettingsWindow() {
    	if (settingsWindow == null || settingsWindow.shell.isDisposed()) {
    		settingsWindow = new SettingsWindow();
    	}
        return settingsWindow;
    }
    
    /**
     * Gets an {@link UncaughtExceptionHandler} for current GUI implementation
     * 
     * @return instance of Uncaught Exception Handler
     */
    public static UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return new com.varaneckas.hawkscope.util.UncaughtExceptionHandler();
    }
    
}
