package com.varaneckas.hawkscope.gui;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Display;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.swing.SwingAboutFrame;
import com.varaneckas.hawkscope.gui.swing.SwingUncaughtExceptionHandler;
import com.varaneckas.hawkscope.gui.swt.SWTAboutShell;
import com.varaneckas.hawkscope.gui.swt.SWTTrayManager;
import com.varaneckas.hawkscope.gui.swt.SWTUncaughtExceptionHandler;
import com.varaneckas.hawkscope.tray.TrayManagerFactory;

/**
 * Window factory for switching among multiple GUI implementations
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class WindowFactory {

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(WindowFactory.class);

    /**
     * AboutWindow instance
     */
    private static AboutWindow aboutWindow = null;

    /**
     * Initializes the application GUI 
     */
    public static void initialize() {
        if (getGuiImplementation().equals("SWT")) {
            //Set application name 
            Display.setAppName(Version.APP_NAME);
        } else {
            try {
                //Set Swing look and feel to a native one
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (final Exception e) {
                log.warn("Failed setting Swing system look and feel", e);
            }
        }
    }
    
    /**
     * Gets the About Window
     * 
     * @return instance of About Window
     */
    public static AboutWindow getAboutWindow() {
        if (aboutWindow == null) {
            if (getGuiImplementation().equals("SWT")) {
                aboutWindow = new SWTAboutShell(((SWTTrayManager) 
                        TrayManagerFactory.getTrayManager()).getShell(), 0);
            } else {
                aboutWindow = new SwingAboutFrame();
            }
        }
        return aboutWindow;
    }
    
    /**
     * Gets the Settings Window
     * 
     * @return instance of Settings Window
     */
    public static SettingsWindow getSettingsWindow() {
        //TODO implement settings window
        throw new UnsupportedOperationException("GUI Settings Form is not " +
        		"yet implemented.");
    }
    
    /**
     * Gets an {@link UncaughtExceptionHandler} for current GUI implementation
     * 
     * @return instance of Uncaught Exception Handler
     */
    public static UncaughtExceptionHandler getUncaughtExceptionHandler() {
        if (getGuiImplementation().equals("SWT")) {
            return new SWTUncaughtExceptionHandler();
        } 
        return new SwingUncaughtExceptionHandler();
    }
    
    /**
     * Gets the GUI implementation name
     * @return
     */
    private static String getGuiImplementation() {
        return ConfigurationFactory.getConfigurationFactory().getConfiguration()
            .getGuiImplementation();
    }
    
}
