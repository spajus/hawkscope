package com.varaneckas.hawkscope.listeners;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import com.varaneckas.hawkscope.menu.MainPopupMenu;

/**
 * File {@link ActionListener}
 * 
 * Opens file with default system application
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class FileActionListener implements ActionListener {
    
    /**
     * Target file
     */
    private final File file;
    
    /**
     * Constructor 
     * 
     * @param file target
     */
    public FileActionListener(final File file) {
        this.file = file;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        try {
            Desktop.getDesktop().open(file);
        } catch (final IOException e1) {
            throw new RuntimeException("Failed opening file: " + file, e1);
        }
        MainPopupMenu.getInstance().forceHide();
    }    
    
}
