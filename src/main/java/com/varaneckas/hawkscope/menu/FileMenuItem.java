package com.varaneckas.hawkscope.menu;

import java.io.File;

import javax.swing.JMenuItem;

import com.varaneckas.hawkscope.listeners.FileActionListener;

public class FileMenuItem extends JMenuItem {
    private static final long serialVersionUID = 2512823752109399974L;

    public FileMenuItem(final File file) {
        setText(file.getName());
        addActionListener(new FileActionListener(file));
        setIcon(IconFactory.getIcon("file"));
    }

}