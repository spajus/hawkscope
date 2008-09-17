package com.varaneckas.hawkscope.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.menu.FolderMenu;

public class FolderMenuMouseListener extends MouseAdapter {
    private boolean loaded = false;
    private final FolderMenu folderMenu;
    private final File file;
    
    public FolderMenuMouseListener(FolderMenu menu, File file) {
        this.folderMenu = menu;
        this.file = file;
    }

    @Override
    public synchronized void mouseEntered(final MouseEvent e) {
        if (!loaded && file != null && file.isDirectory()) {
            for (final File ff : file.listFiles()) {
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
    public void mouseClicked(MouseEvent e) {
        e.consume();
        if (e.getButton() != MouseEvent.BUTTON1) {
            final JPopupMenu pop = new JPopupMenu();
            pop.setInvoker(pop);
            final JMenuItem explore = new JMenuItem("Explore");
            explore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Runtime.getRuntime().exec(
                                "explorer.exe "
                                        + file.getAbsolutePath());
                        pop.setVisible(false);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            pop.setLocation(e.getXOnScreen(), e.getYOnScreen());
            pop.add(explore);
            pop.setVisible(true);

        }
    }
}
