package com.varaneckas.hawkscope.menu;

import java.io.File;


public abstract class FolderMenu implements MenuItem {

    final protected File file;
    
    public FolderMenu(final File file) {
        this.file = file;
    }
    
    public abstract void addMenuItem(MenuItem item);
    
    public abstract void addSeparator();
    
}