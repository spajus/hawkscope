package com.varaneckas.hawkscope.menu;

import java.io.File;

import com.varaneckas.hawkscope.gui.swing.SwingMainMenu;

public abstract class MenuFactory {

    private static MainMenu instance = null;
    
    public static MainMenu getMainMenu() {
        if (instance == null) {
            instance = new SwingMainMenu();
        }
        return instance;
    }
    
    public static FolderMenu newFolderMenu(final File file) {
        return null;
    }
    
    public static FileMenuItem newFileMenuItem(final File file) {
        return null;
    }
    
}
