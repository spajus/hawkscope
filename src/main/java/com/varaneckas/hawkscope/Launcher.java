package com.varaneckas.hawkscope;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.WindowFactory;
import com.varaneckas.hawkscope.gui.swt.SWTMenuFactory;
import com.varaneckas.hawkscope.tray.SWTTrayManager;

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
            ConfigurationFactory.getConfigurationFactory(args)
                    .getConfiguration();
        	Thread.setDefaultUncaughtExceptionHandler(WindowFactory
        	        .getUncaughtExceptionHandler());
        	WindowFactory.initialize();
        } catch (final Throwable e) {
            log.fatal("Failed starting Hawkscope", e);
        }
        preload();
        SWTTrayManager.getInstance().load();
    }
    
    /**
     * Preloads slow Hawkscope singletons for fast use later
     */
    private static void preload() {
		log.debug("Preloading data");
		SWTMenuFactory.getMainMenu().loadMenu();
		log.debug("Preloaded...");
    }

}
