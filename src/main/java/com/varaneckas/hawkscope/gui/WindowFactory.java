package com.varaneckas.hawkscope.gui;

import com.varaneckas.hawkscope.gui.swing.SwingAboutFrame;

public class WindowFactory {

    private static AboutWindow aboutWindow = null;
    
    public static AboutWindow getAboutWindow() {
        if (aboutWindow == null) {
            aboutWindow = new SwingAboutFrame();
        }
        return aboutWindow;
    }
    
}
