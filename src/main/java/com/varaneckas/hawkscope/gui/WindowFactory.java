package com.varaneckas.hawkscope.gui;

import java.lang.Thread.UncaughtExceptionHandler;

import org.eclipse.swt.widgets.Display;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.tray.TrayManager;

/**
 * Window factory for switching among multiple GUI implementations
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class WindowFactory {
	
    /**
     * AboutWindow instance
     */
    private static AbstractWindow aboutWindow = null;
    
    /**
     * SettingsWindow instance
     */
    private static SettingsShell settingsWindow = null;

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
        if (aboutWindow == null) {
            aboutWindow = new AboutWindow();
        }
        return aboutWindow;
    }
    
    /**
     * Gets the Settings Window
     * 
     * @return instance of Settings Window
     */
    public static SettingsShell getSettingsWindow() {
        if (settingsWindow == null) {
            settingsWindow = new SettingsShell(TrayManager.getInstance()
            		.getShell(), 0);
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
