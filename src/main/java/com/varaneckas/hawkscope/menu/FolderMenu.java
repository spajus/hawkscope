package com.varaneckas.hawkscope.menu;

import java.io.File;

/**
 * Abstract Folder menu 
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class FolderMenu implements MenuItem {

    /**
     * Target folder
     */
    final protected File file;
    
    /**
     * Initializing constructor
     * 
     * @param file target folder
     */
    public FolderMenu(final File file) {
        this.file = file;
    }
    
    /**
     * Adds a menu item to this menu
     * 
     * @param item child menu item or menu
     */
    public abstract void addMenuItem(MenuItem item);
    
    /**
     * Adds a separator to this menu
     */
    public abstract void addSeparator();
    
}