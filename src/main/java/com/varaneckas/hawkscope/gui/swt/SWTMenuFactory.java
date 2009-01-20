package com.varaneckas.hawkscope.gui.swt;

import java.io.File;

import com.varaneckas.hawkscope.menu.ExecutableMenuItem;
import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MainMenu;
import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * SWT {@link MainMenu} factory
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTMenuFactory extends MenuFactory {

    /**
     * Gets the SWT imlementation of {@link MainMenu} 
     */
    public MainMenu getMainMenu() {
        return SWTMainMenu.getInstance();
    }

    @Override
    public ExecutableMenuItem newExecutableMenuItem() {
        return new SWTExecutableMenuItem();
    }

    @Override
    public FileMenuItem newFileMenuItem(final File file) {
        return new SWTFileMenuItem(file);
    }

    @Override
    public FolderMenu newFolderMenu(final File file) {
        return new SWTFolderMenu(file);
    }
    
}
