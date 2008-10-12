package com.varaneckas.hawkscope.tray;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.swing.SwingTrayManager;
import com.varaneckas.hawkscope.gui.swt.SWTTrayManager;

public abstract class TrayManagerFactory {
    
    private static TrayManager instance = null;
    
    public static TrayManager getTrayManager() {
        if (instance == null) {
            if (ConfigurationFactory.getConfigurationFactory()
                    .getConfiguration().getGuiImplementation().equals("SWT")) {
                instance = new SWTTrayManager();
            } else {
                instance = new SwingTrayManager();
            }
        }
        return instance;
    }

}
