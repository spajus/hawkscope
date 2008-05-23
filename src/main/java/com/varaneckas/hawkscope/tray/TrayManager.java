package com.varaneckas.hawkscope.tray;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    
    private JPopupMenu trayPopupMenu;
    
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
        
        final URL iconURL = ClassLoader.getSystemClassLoader()
            .getResource(trayIconImage);
        if (iconURL == null) {
            throw new RuntimeException("Could not find tray icon " +
                    "image: " + trayIconImage);
        }
        
        trayIcon = new TrayIcon(new ImageIcon(iconURL).getImage());
        trayIcon.setImageAutoSize(false);
        trayIcon.setToolTip(trayToolTip);
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    e.consume();
                    if (e.getButton() != MouseEvent.BUTTON1) {
                        if (trayPopupMenu.isVisible()) {
                            trayPopupMenu.setVisible(false);
                        } else {
                            trayPopupMenu.setLocation(e.getX(), e.getY());
                            trayPopupMenu.setInvoker(trayPopupMenu);
                            trayPopupMenu.setVisible(true);
                        }
                    }
                }
                catch (Exception ae) {
                    System.out.println("tEst");
                    ae.printStackTrace();
                }
            }
        });
        
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

    public void setTrayPopupMenu(JPopupMenu trayPopupMenu) {
        this.trayPopupMenu = trayPopupMenu;
    }
}