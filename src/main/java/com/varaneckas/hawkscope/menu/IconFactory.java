package com.varaneckas.hawkscope.menu;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IconFactory {

    private static final Log log = LogFactory.getLog(IconFactory.class);
    
    private static final Map<String, Icon> icons = new HashMap<String, Icon>();
    
    static {
        icons.put("drive", new ImageIcon(ClassLoader.getSystemClassLoader().getResource("hdd24.png")));
        icons.put("folder", new ImageIcon(ClassLoader.getSystemClassLoader().getResource("folder24.png")));
        icons.put("file", new ImageIcon(ClassLoader.getSystemClassLoader().getResource("file24.png")));
    }
    
    public static Icon getIcon(String name) {
        return icons.get(name);
    }
    
    public static TrayIcon getTrayIcon() {
        String trayIconImage = getBestTrayIconSize();
        final URL iconURL = ClassLoader.getSystemClassLoader()
        .getResource(trayIconImage);
    if (iconURL == null) {
        throw new RuntimeException("Could not find tray icon " +
                "image: " + trayIconImage);
    }
    
        return new TrayIcon(new ImageIcon(iconURL).getImage());
    }
    
    private static String getBestTrayIconSize() {
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
    
}
