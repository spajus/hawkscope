package com.varaneckas.hawkscope.gui.swt;

import java.io.File;

import com.varaneckas.hawkscope.menu.ExecutableMenuItem;
import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MainMenu;
import com.varaneckas.hawkscope.menu.MenuFactory;

public class SWTMenuFactory extends MenuFactory {

    public MainMenu getMainMenu() {
        return SWTMainMenu.getInstance();
    }

    @Override
    public ExecutableMenuItem newExecutableMenuItem() {
        return new SWTExecutableMenuItem();
    }

    @Override
    public FileMenuItem newFileMenuItem(File file) {
        return null;
    }

    @Override
    public FolderMenu newFolderMenu(File file) {
        return null;
    }
    
}
