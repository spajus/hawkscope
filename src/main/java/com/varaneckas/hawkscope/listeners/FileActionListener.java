package com.varaneckas.hawkscope.listeners;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import com.varaneckas.hawkscope.menu.TrayPopupMenu;

public class FileActionListener implements ActionListener {
    
    private final File file;
    
    public FileActionListener(File file) {
        this.file = file;
    }

 
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e1) {
            throw new RuntimeException("Failed opening file: " + file);
        }
        TrayPopupMenu.getInstance().forceHide();
        
    }    
    
}
