package com.varaneckas.hawkscope.tray;

public abstract class TrayManagerFactory {
    
    private static TrayManager instance = null;
    
    public static TrayManager getTrayManager() {
        if (instance == null) {
            instance = new AWTTrayManager();
        }
        return instance;
    }

}
