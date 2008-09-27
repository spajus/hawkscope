package com.varaneckas.hawkscope.listeners;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.varaneckas.hawkscope.menu.DynamicFileFilter;
import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.TrayPopupMenu;

/**
 * Folder Menu {@link MouseListener}
 * 
 * Loads menu contents on mouse over
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class FolderMenuMouseListener extends MouseAdapter {
    
    /**
     * Lazy loading flag
     */
    private boolean loaded = false;
    
    /**
     * Target menu
     */
    private final FolderMenu folderMenu;
    
    /**
     * Target folder
     */
    private final File file;
    
    /**
     * Constructor
     * 
     * @param menu tagert
     * @param file target
     */
    public FolderMenuMouseListener(final FolderMenu menu, final File file) {
        this.folderMenu = menu;
        this.file = file;
    }

    @Override
    public synchronized void mouseEntered(final MouseEvent e) {
        if (!loaded && file != null && file.isDirectory()) {
            final File[] files = file.listFiles(DynamicFileFilter.getInstance());
            Arrays.sort(files);
            for (final File ff : files) {
                if (ff.isDirectory()) {
                    folderMenu.add(new FolderMenu(ff));
                } else {
                    folderMenu.add(new FileMenuItem(ff));
                }

            }
            loaded = true;
        }
        e.consume();
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        e.consume();
        if (e.getButton() == MouseEvent.BUTTON1) {
            try {
                Desktop.getDesktop().open(file);
            } catch (final IOException e1) {
                e1.printStackTrace();
            }
            TrayPopupMenu.getInstance().forceHide();
        }
        else {
            folderMenu.removeAll();
            loaded = false;
        }
    }
}
