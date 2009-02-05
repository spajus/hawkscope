package com.varaneckas.hawkscope.gui.swt;

import java.io.File;

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

    public static SWTExecutableMenuItem newExecutableMenuItem() {
        return new SWTExecutableMenuItem();
    }

    public static SWTFileMenuItem newFileMenuItem(final File file) {
        return new SWTFileMenuItem(file);
    }

    public static SWTFolderMenu newFolderMenu(final File file) {
        return new SWTFolderMenu(file);
    }
    
}
