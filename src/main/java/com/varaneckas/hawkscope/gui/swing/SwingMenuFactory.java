package com.varaneckas.hawkscope.gui.swing;

import java.io.File;

import com.varaneckas.hawkscope.menu.ExecutableMenuItem;
import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MainMenu;
import com.varaneckas.hawkscope.menu.MenuFactory;

public class SwingMenuFactory extends MenuFactory {

    private MainMenu mainMenu;
    
    @Override
    public ExecutableMenuItem newExecutableMenuItem() {
        return new SwingExecutableMenuItem();
    }

    @Override
    public MainMenu getMainMenu() {
        if (mainMenu == null) {
            mainMenu = new SwingMainMenu();
        } 
        return mainMenu;
    }

    @Override
    public FileMenuItem newFileMenuItem(File file) {
        return new SwingFileMenuItem(file);
    }

    @Override
    public FolderMenu newFolderMenu(final File file) {
        return new SwingFolderMenu(file);
    }

}
