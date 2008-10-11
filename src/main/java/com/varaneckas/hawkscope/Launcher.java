package com.varaneckas.hawkscope;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.tray.TrayManagerFactory;
import com.varaneckas.hawkscope.util.SimpleUncaughtExceptionHandler;

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
        	Thread.setDefaultUncaughtExceptionHandler(
        	         new SimpleUncaughtExceptionHandler());
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
    	SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				log.debug("Preloading data");
				try {
				MenuFactory.getMainMenu().loadMenu();
				} catch (Exception e) {
				    log.error("Failed preloading data", e);
				}
				log.debug("Preloaded...");
			}
    	});
    }

}
