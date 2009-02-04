package com.varaneckas.hawkscope.gui;

import java.lang.Thread.UncaughtExceptionHandler;

import org.eclipse.swt.widgets.Display;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.gui.swt.SWTAboutShell;
import com.varaneckas.hawkscope.gui.swt.SWTSettingsShell;
import com.varaneckas.hawkscope.gui.swt.SWTUncaughtExceptionHandler;
import com.varaneckas.hawkscope.tray.SWTTrayManager;

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
    private static SWTAboutShell aboutWindow = null;
    
    /**
     * SettingsWindow instance
     */
    private static SWTSettingsShell settingsWindow = null;

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
    public static SWTAboutShell getAboutWindow() {
        if (aboutWindow == null) {
            aboutWindow = new SWTAboutShell(SWTTrayManager.getInstance().getShell(), 0);
        }
        return aboutWindow;
    }
    
    /**
     * Gets the Settings Window
     * 
     * @return instance of Settings Window
     */
    public static SWTSettingsShell getSettingsWindow() {
        if (settingsWindow == null) {
            settingsWindow = new SWTSettingsShell(SWTTrayManager.getInstance().getShell(), 0);
        }
        return settingsWindow;
    }
    
    /**
     * Gets an {@link UncaughtExceptionHandler} for current GUI implementation
     * 
     * @return instance of Uncaught Exception Handler
     */
    public static UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return new SWTUncaughtExceptionHandler();
    }
    
}
