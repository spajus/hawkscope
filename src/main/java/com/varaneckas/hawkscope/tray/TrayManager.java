package com.varaneckas.hawkscope.tray;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.listeners.TrayPopupMenuMouseListener;

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
        
        trayIconImage = getBestTrayIcon();
        
        final URL iconURL = ClassLoader.getSystemClassLoader()
            .getResource(trayIconImage);
        if (iconURL == null) {
            throw new RuntimeException("Could not find tray icon " +
                    "image: " + trayIconImage);
        }
        
        trayIcon = new TrayIcon(new ImageIcon(iconURL).getImage());
        trayIcon.setImageAutoSize(false);
        trayIcon.setToolTip(trayToolTip);
        trayIcon.addMouseListener(new TrayPopupMenuMouseListener());
        
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            throw new RuntimeException("Could not add tray icon", e);
        }
    }
    
    private String getBestTrayIcon() {
        float height = SystemTray.getSystemTray().getTrayIconSize().height;
        int[] sizes = new int[] { 64, 48, 32, 24, 16 };
        int best = 64;
        for (int i = 0; i < sizes.length; i++) {
            if (sizes[i] / height >= 1) {
                best = sizes[i];
            }
            else {
                break;
            }
        }
        String res = "hawkscope" + best + ".png";
        if (log.isDebugEnabled()) {
            log.debug("Chose best icon for " + (int) height + " pixel tray: " + res);
        }
        return res;
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