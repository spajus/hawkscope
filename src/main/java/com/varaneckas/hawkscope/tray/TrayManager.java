package com.varaneckas.hawkscope.tray;

import java.awt.TrayIcon;

public interface TrayManager {

    /**
     * Gets system tray icon
     * 
     * @return system tray icon
     */
    public abstract TrayIcon getTrayIcon();

    /**
     * Loads system tray icon if it is supported
     */
    public abstract void load();

}