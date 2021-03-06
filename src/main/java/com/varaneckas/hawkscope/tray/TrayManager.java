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
package com.varaneckas.hawkscope.tray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TrayItem;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.OSUtils;
import com.varaneckas.hawkscope.util.UncaughtExceptionHandler;
import com.varaneckas.hawkscope.util.OSUtils.OS;

/**
 * {@link TrayManager} - SWT Implementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class TrayManager {
	
    /**
     * Singleton instance
     */
	private static final TrayManager instance = new TrayManager();
	
	/**
	 * Singleton constructor
	 */
	private TrayManager() {}
	
	/**
	 * Gets the singleton instance 
	 * 
	 * @return
	 */
	public static TrayManager getInstance() {
		return instance;
	}

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(TrayManager.class);
    
    /**
     * Tray Icon object
     */
    private TrayItem trayIcon;
    
    /**
     * Display in use
     */
    private static final Display d = Display.getDefault();
    
    /**
     * Shell in use
     */
    private static final Shell sh = new Shell(d);

    /**
     * Gets the {@link TrayItem}
     * 
     * @return tray icon object
     */
    public TrayItem getTrayIcon() {
        return trayIcon;
    }

    /**
     * Gets the shell
     * 
     * @return
     */
    public Shell getShell() {
        return sh;
    }
    
    /**
     * Loads the tray item
     */
    public void load() {
        trayIcon = new TrayItem(d.getSystemTray(), SWT.NONE);
        trayIcon.setImage(IconFactory.getInstance().getTrayIcon());
        final TrayIconListener listener = new TrayIconListener();
        trayIcon.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event ev) {
				final Configuration cfg = ConfigurationFactory
						.getConfigurationFactory().getConfiguration();
				if (OSUtils.CURRENT_OS.equals(OS.MAC) && cfg.getProperties()
						.get(Configuration.MAC_MENUBAR_BLUES_WORKAROUND)
						.equals("1")) {
					//mac blue icon hack
					//https://bugs.eclipse.org/bugs/show_bug.cgi?id=263589
					new Thread(new Runnable() {
						public void run() {
							OSUtils.sleep(150);
							d.asyncExec(new Runnable() {
								public void run() {
									listener.handleEvent(null);
								}
							});
						}
					}).start();
				} else {
					listener.handleEvent(ev);
				}
			}
        });
        trayIcon.addListener(SWT.MenuDetect, listener);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                log.debug("Removing Tray Icon");
                IconFactory.getInstance().cleanup();
            }
        }, "icon-disposer-hook"));
        while (!sh.isDisposed()) {
            try {
                if (!d.readAndDispatch()) {
                    d.sleep();
                }
            } catch (final Exception e) {
                new UncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
            }
        }
        d.dispose();
    }
}
