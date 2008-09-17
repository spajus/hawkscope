package com.varaneckas.hawkscope.listeners;

import java.awt.event.MouseAdapter;
import java.io.File;

import com.varaneckas.hawkscope.menu.FileMenuItem;

public class FileMenuMouseListener extends MouseAdapter {
    
    private final FileMenuItem menuItem;
    private final File file;
    
    public FileMenuMouseListener(FileMenuItem menuItem, File file) {
        this.menuItem = menuItem;
        this.file = file;
    }
    
}
