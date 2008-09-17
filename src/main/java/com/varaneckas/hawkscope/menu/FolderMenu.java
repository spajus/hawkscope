package com.varaneckas.hawkscope.menu;

import java.io.File;

import javax.swing.JMenu;

import com.varaneckas.hawkscope.listeners.FolderMenuMouseListener;

public class FolderMenu extends JMenu {
    
    private static final long serialVersionUID = 5526827274886307261L;

    public FolderMenu(final File file) {
        if (file != null) {
            setText(file.getName());
            addMouseListener(new FolderMenuMouseListener(this, file));               
        }
    }
}
