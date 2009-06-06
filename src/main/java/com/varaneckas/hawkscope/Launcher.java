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
package com.varaneckas.hawkscope;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.WindowFactory;
import com.varaneckas.hawkscope.hotkey.GlobalHotkeyListener;
import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.tray.TrayManager;
import com.varaneckas.hawkscope.util.ModularAuthenticator;

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
            ModularAuthenticator.register();
        	Thread.setDefaultUncaughtExceptionHandler(WindowFactory
        	        .getUncaughtExceptionHandler());
        	WindowFactory.initialize();
        } catch (final Throwable e) {
            log.fatal("Failed starting Hawkscope", e);
        }
        preload();
        GlobalHotkeyListener.getInstance();
        TrayManager.getInstance().load();
    }
    
    /**
     * Preloads slow Hawkscope singletons for fast use later
     */
    private static void preload() {
		log.debug("Preloading data");
		MenuFactory.getMainMenu().reloadMenu(false);
		log.debug("Preloaded...");
    }

}
