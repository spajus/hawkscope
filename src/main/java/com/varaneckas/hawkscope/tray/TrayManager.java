package com.varaneckas.hawkscope.tray;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.listeners.TrayPopupMenuMouseListener;
import com.varaneckas.hawkscope.menu.IconFactory;

public class TrayManager {
    
    private static final TrayManager instance = new TrayManager();
    
    private TrayManager() {
        //singleton constructor
    }
    
    public static TrayManager getInstance() {
        return instance;
    }
    
    private static final Log log = LogFactory.getLog(TrayManager.class);
    
    private String trayIconImage;
    
    private String trayToolTip;
    
    private TrayIcon trayIcon;
    
    public TrayIcon getTrayIcon() {
        return trayIcon;
    }

    public void setTrayIconImage(final String trayIconImage) {
        this.trayIconImage = trayIconImage;
    }

    public void load() {
        if (SystemTray.isSupported()) {
            loadTray();
        } else {
            log.warn("System Tray not supported, working in desktop mode");
        }
    }
    
    private void loadTray() {
        trayIcon = IconFactory.getTrayIcon();
        trayIcon.setImageAutoSize(false);
        trayIcon.setToolTip(trayToolTip);
        trayIcon.addMouseListener(new TrayPopupMenuMouseListener());
        
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            throw new RuntimeException("Could not add tray icon", e);
        }
    }
    


    public void unload() {
        if (trayIcon != null) {
            log.debug("Unloading tray icon");
            SystemTray.getSystemTray().remove(trayIcon);
            trayIcon = null;
            log.debug("Unloaded");
        }
    }

    public void setTrayToolTip(String trayToolTip) {
        this.trayToolTip = trayToolTip;
    }

}