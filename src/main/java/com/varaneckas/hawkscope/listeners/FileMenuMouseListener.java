package com.varaneckas.hawkscope.listeners;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.varaneckas.hawkscope.menu.FileMenuItem;

public class FileMenuMouseListener extends MouseAdapter {
    
    private final FileMenuItem menuItem;
    private final File file;
    
    public FileMenuMouseListener(FileMenuItem menuItem, File file) {
        this.menuItem = menuItem;
        this.file = file;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        e.consume();
        if (e.getButton() != MouseEvent.BUTTON1) {
            final JPopupMenu pop = new JPopupMenu();
            pop.setInvoker(pop);
            final JMenuItem explore = new JMenuItem("Edit");
            explore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().open(file);
//                        Runtime.getRuntime().exec(
//                                "explorer.exe "
//                                        + file.getAbsolutePath());
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
