package com.varaneckas.hawkscope;

import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.swing.SwingUncaughtExceptionHandler;
import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.tray.TrayManagerFactory;

/**
 * Hawkscope Application Launcher
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class Launcher {
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(Launcher.class);

    /**
     * Application bootstrapper
     * 
     * @param args Command line arguments
     */
    public static void main(final String[] args) {
        log.info(Version.formatFullString());
        try {
            ConfigurationFactory.getConfigurationFactory(args).getConfiguration();
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        	Thread.setDefaultUncaughtExceptionHandler(
//        	         new SwingUncaughtExceptionHandler());
        } catch (final Throwable e) {
            log.fatal("Failed starting Hawkscope", e);
        }
        preload();
        TrayManagerFactory.getTrayManager().load();
    }
    
    /**
     * Preloads slow Hawkscope singletons for fast use later
     */
    private static void preload() {
		log.debug("Preloading data");
		MenuFactory.getMenuFactory().getMainMenu().loadMenu();
		log.debug("Preloaded...");
    }

}
