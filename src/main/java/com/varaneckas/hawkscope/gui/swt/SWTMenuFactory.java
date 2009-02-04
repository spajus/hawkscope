package com.varaneckas.hawkscope.gui.swt;

import java.io.File;

import com.varaneckas.hawkscope.menu.ExecutableMenuItem;
import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.menu.FolderMenu;

/**
 * SWT {@link MainMenu} factory
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTMenuFactory {
	
    /**
     * Gets the SWT imlementation of {@link MainMenu} 
     */
    public static SWTMainMenu getMainMenu() {
        return SWTMainMenu.getInstance();
    }

    public static ExecutableMenuItem newExecutableMenuItem() {
        return new SWTExecutableMenuItem();
    }

    public static FileMenuItem newFileMenuItem(final File file) {
        return new SWTFileMenuItem(file);
    }

    public static FolderMenu newFolderMenu(final File file) {
        return new SWTFolderMenu(file);
    }
    
}
