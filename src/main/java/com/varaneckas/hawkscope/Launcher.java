package com.varaneckas.hawkscope;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.WindowFactory;
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
        //FIXME remove this bloody linux session autostart hack
        if (args != null) {
            if (args.length > 1) {
                if (args[0].startsWith("-delay")) {
                    try {
                        Thread.sleep(Long.parseLong(args[1].trim()));
                    } catch (final Exception e) {
                        log.warn("Insomnia", e);
                    }
                }
            }
        }
        //end bloody hack
        
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
