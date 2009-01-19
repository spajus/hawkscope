package com.varaneckas.hawkscope.gui;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.swing.SwingAboutFrame;
import com.varaneckas.hawkscope.gui.swing.SwingUncaughtExceptionHandler;
import com.varaneckas.hawkscope.gui.swt.SWTAboutShell;
import com.varaneckas.hawkscope.gui.swt.SWTTrayManager;
import com.varaneckas.hawkscope.gui.swt.SWTUncaughtExceptionHandler;
import com.varaneckas.hawkscope.tray.TrayManagerFactory;

public class WindowFactory {

    private static AboutWindow aboutWindow = null;
    
    private static final Log log = LogFactory.getLog(WindowFactory.class);
    
    public static void initialize() {
        if (getGuiImplementation().equals("SWT")) {
            //nothing to initialize
        } else {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (final Exception e) {
                log.warn("Failed setting Swing system look and feel", e);
            }
        }
    }
    
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
    
    public static UncaughtExceptionHandler getUncaughtExceptionHandler() {
        if (getGuiImplementation().equals("SWT")) {
            return new SWTUncaughtExceptionHandler();
        } 
        return new SwingUncaughtExceptionHandler();
    }
    
    private static String getGuiImplementation() {
        return ConfigurationFactory.getConfigurationFactory().getConfiguration().getGuiImplementation();
    }
    
}
