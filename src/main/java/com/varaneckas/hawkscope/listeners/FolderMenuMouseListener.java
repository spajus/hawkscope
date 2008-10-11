package com.varaneckas.hawkscope.listeners;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import com.varaneckas.hawkscope.menu.DynamicFileFilter;
import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.MenuUtils;

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
     * Number of Menu Items in one Menu
     */
    private static final int MENU_SIZE = MenuUtils.getAutoMenuSize();
    
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
            long counter = 0L;
            JMenu workMenu = folderMenu;
            if (files.length == 0) {
                final JMenuItem empty = new JMenuItem(IconFactory.getIcon("empty"));
                empty.setText("Empty...");
                empty.setEnabled(false);
                workMenu.add(empty);
            }
            for (final File ff : files) {
                if (ff.isDirectory()) {
                    workMenu.add(new FolderMenu(ff));
                } else {
                    workMenu.add(new FileMenuItem(ff));
                }
                if (++counter % MENU_SIZE == 0) {
                    JMenu more = new JMenu("More");
                    more.setIcon(IconFactory.getIcon("more"));
                    workMenu.add(new JSeparator());
                    workMenu.add(more);
                    workMenu = more;
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
            MenuFactory.getMainMenu().forceHide();
        }
        else {
            folderMenu.removeAll();
            loaded = false;
        }
    }
}
