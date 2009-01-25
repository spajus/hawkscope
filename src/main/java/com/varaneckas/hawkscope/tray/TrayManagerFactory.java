package com.varaneckas.hawkscope.tray;

import com.varaneckas.hawkscope.gui.swt.SWTTrayManager;

/**
 * System {@link TrayManager} factory
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class TrayManagerFactory {
    
    /**
     * Current instance of {@link TrayManager}
     */
    private static TrayManager instance = null;
    
    /**
     * Lazy {@link TrayManager} instance getter
     * 
     * @return current {@link TrayManager}
     */
    public static TrayManager getTrayManager() {
        if (instance == null) {
            instance = new SWTTrayManager();
        }
        return instance;
    }

}
