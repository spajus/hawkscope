package com.varaneckas.hawkscope.menu;

import java.io.File;

/**
 * SWT {@link MainMenu} factory
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class MenuFactory {
	
    /**
     * Gets the SWT imlementation of {@link MainMenu} 
     */
    public static MainMenu getMainMenu() {
        return MainMenu.getInstance();
    }

    public static ExecutableMenuItem newExecutableMenuItem() {
        return new ExecutableMenuItem();
    }

    public static FileMenuItem newFileMenuItem(final File file) {
        return new FileMenuItem(file);
    }

    public static FolderMenu newFolderMenu(final File file) {
        return new FolderMenu(file);
    }
    
}
