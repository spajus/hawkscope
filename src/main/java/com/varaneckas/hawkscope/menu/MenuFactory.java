package com.varaneckas.hawkscope.menu;

import java.io.File;

import com.varaneckas.hawkscope.gui.swt.SWTMenuFactory;

/**
 * Hawkscope Menu factory
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class MenuFactory {
    
    /**
     * Singleton instance
     */
    private static MenuFactory instance = null;
    
    /**
     * Lazy singleton getter
     * 
     * @return singleton instance
     */
    public static MenuFactory getMenuFactory() {
        if (instance == null) {
            instance = new SWTMenuFactory();
        }
        return instance;   
    }

    /**
     * Gets the {@link MainMenu}
     * 
     * @return main menu
     */
    public abstract MainMenu getMainMenu();

    /**
     * Creates new File menu item
     * 
     * @param file target {@link File}
     * @return new menu item
     */
    public abstract FileMenuItem newFileMenuItem(final File file);
    
    /**
     * Creates executable menu item with command
     * 
     * @return new executable menu item
     */
    public abstract ExecutableMenuItem newExecutableMenuItem();
    
    /**
     * Creates folder menu
     * 
     * @param file target folder
     * @return new folder menu
     */
    public abstract FolderMenu newFolderMenu(final File file);
    
}
