package com.varaneckas.hawkscope.tray;

import com.varaneckas.hawkscope.gui.swing.SwingTrayManager;

public abstract class TrayManagerFactory {
    
    private static TrayManager instance = null;
    
    public static TrayManager getTrayManager() {
        if (instance == null) {
            instance = new SwingTrayManager();
        }
        return instance;
    }

}
