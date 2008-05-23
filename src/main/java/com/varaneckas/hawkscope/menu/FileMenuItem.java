package com.varaneckas.hawkscope.menu;

import java.io.File;

import javax.swing.JMenuItem;

public class FileMenuItem extends JMenuItem {
    private static final long serialVersionUID = 2512823752109399974L;
    private volatile File file;

    public FileMenuItem(final File f) {
        this.file = f;
        setText(file.getName());
    }

    public File getFile() {
        return file;
    }
}