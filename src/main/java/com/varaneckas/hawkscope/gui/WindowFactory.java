package com.varaneckas.hawkscope.gui;

import java.lang.Thread.UncaughtExceptionHandler;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.swing.SwingAboutFrame;
import com.varaneckas.hawkscope.gui.swing.SwingUncaughtExceptionHandler;
import com.varaneckas.hawkscope.gui.swt.SWTAboutShell;
import com.varaneckas.hawkscope.gui.swt.SWTTrayManager;
import com.varaneckas.hawkscope.tray.TrayManagerFactory;

public class WindowFactory {

    private static AboutWindow aboutWindow = null;
    
    public static AboutWindow getAboutWindow() {
        if (aboutWindow == null) {
            if (ConfigurationFactory.getConfigurationFactory().getConfiguration().getGuiImplementation().equals("SWT")) {
                aboutWindow = new SWTAboutShell(((SWTTrayManager) TrayManagerFactory.getTrayManager()).getShell(), 0);
            } else {
                aboutWindow = new SwingAboutFrame();
            }
        }
        
        return aboutWindow;
    }
    
    public static UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return new SwingUncaughtExceptionHandler();
    }
    
}
