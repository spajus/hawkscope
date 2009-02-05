package com.varaneckas.hawkscope.gui.listeners;

import java.io.File;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.program.Program;

import com.varaneckas.hawkscope.plugin.PluginManager;

public class FileMenuItemListener extends SelectionAdapter {
    private final File file;

    public FileMenuItemListener(final File file) {
        this.file = file;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        boolean launch = PluginManager.getInstance().interceptClick(file);
        if (launch) {
            if (!Program.launch(file.getAbsolutePath())) {
                throw new RuntimeException("Cannot find program for opening "
                        + file);
            }
        }
    }
}
