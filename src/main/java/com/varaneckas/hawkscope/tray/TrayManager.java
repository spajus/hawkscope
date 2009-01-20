package com.varaneckas.hawkscope.tray;

/**
 * System Tray Manager interface
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public interface TrayManager {

    /**
     * Gets system tray icon
     * 
     * @return system tray icon
     */
    public abstract Object getTrayIcon();

    /**
     * Loads system tray icon if it is supported
     */
    public abstract void load();

}