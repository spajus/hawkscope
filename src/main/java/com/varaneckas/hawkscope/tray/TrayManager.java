package com.varaneckas.hawkscope.tray;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.listeners.TrayPopupMenuMouseListener;
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * System Tray Manager
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
    private TrayManager() {
        //singleton constructor
    }
    
    /**
     * Singleton instance getter
     * 
     * @return instance
     */
    public static TrayManager getInstance() {
        return instance;
    }
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(TrayManager.class);
    
    /**
     * System Tray Icon
     */
    private TrayIcon trayIcon;
    
    /**
     * Gets system tray icon
     * 
     * @return system tray icon
     */
    public TrayIcon getTrayIcon() {
        return trayIcon;
    }

    /**
     * Loads system tray icon if it is supported
     */
    public void load() {
        if (SystemTray.isSupported()) {
            loadTray();
        } else {
            log.warn("System Tray not supported, sorry...");
            throw new UnsupportedOperationException(
            		"System Tray integration not supported. " +
            		"Make sure you are using Java 1.6+ in a " +
            		"compatible environment.");
        }
    }
    
    /**
     * Configures and adds system tray icon
     */
    private void loadTray() {
        trayIcon = IconFactory.getTrayIcon();
        trayIcon.setImageAutoSize(false);
        trayIcon.addMouseListener(new TrayPopupMenuMouseListener());
        
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (final AWTException e) {
            throw new RuntimeException("Could not add tray icon", e);
        }
    }

}