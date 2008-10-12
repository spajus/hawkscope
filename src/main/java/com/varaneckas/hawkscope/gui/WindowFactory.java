package com.varaneckas.hawkscope.gui;

import java.lang.Thread.UncaughtExceptionHandler;

import com.varaneckas.hawkscope.gui.swing.SwingAboutFrame;
import com.varaneckas.hawkscope.gui.swing.SwingUncaughtExceptionHandler;

public class WindowFactory {

    private static AboutWindow aboutWindow = null;
    
    public static AboutWindow getAboutWindow() {
        if (aboutWindow == null) {
            aboutWindow = new SwingAboutFrame();
        }
        return aboutWindow;
    }
    
    public static UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return new SwingUncaughtExceptionHandler();
    }
    
}
