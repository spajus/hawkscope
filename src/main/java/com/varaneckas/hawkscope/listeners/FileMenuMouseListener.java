package com.varaneckas.hawkscope.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.menu.TrayPopupMenu;

public class FileMenuMouseListener extends MouseAdapter {
    
    private static final Log log = LogFactory
            .getLog(FileMenuMouseListener.class);
    
    private final FileMenuItem menuItem;
    private final File file;
    
    public FileMenuMouseListener(FileMenuItem menuItem, File file) {
        this.menuItem = menuItem;
        this.file = file;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        log.info("File clicked: " + file);
        log.info(e);
        TrayPopupMenu.getInstance().getState().act(e);
    }    
    
}
