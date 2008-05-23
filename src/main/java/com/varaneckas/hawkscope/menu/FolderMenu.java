package com.varaneckas.hawkscope.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class FolderMenu extends JMenu {
    
    private static final long serialVersionUID = 5526827274886307261L;

    private File file;
    public FolderMenu(final File f) {
        this.file = f;
        if (file != null) {
            setText(file.getName());
            addMouseListener(new MouseAdapter() {
                private boolean loaded = false;

                @Override
                public synchronized void mouseEntered(final MouseEvent e) {
                    if (!loaded && file != null && file.isDirectory()) {
                        for (final File ff : file.listFiles()) {
                            if (ff.isDirectory()) {
                                add(new FolderMenu(ff));
                            } else {
                                add(new FileMenuItem(ff));
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
            });
        }
    }
    public File getFile() {
        return file;
    }
}
