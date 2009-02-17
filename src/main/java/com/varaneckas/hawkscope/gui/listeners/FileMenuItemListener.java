package com.varaneckas.hawkscope.gui.listeners;

import java.io.File;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.program.Program;

import com.varaneckas.hawkscope.menu.MenuItem;
import com.varaneckas.hawkscope.plugin.PluginManager;

/**
 * Listener for {@link File} {@link MenuItem}
 * 
 * Handles the "Click" on files.
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class FileMenuItemListener extends SelectionAdapter {
   
    /**
     * Target {@link File}
     */
    private final File file;

    /**
     * Constructor with target {@link File}
     * 
     * @param file target
     */
    public FileMenuItemListener(final File file) {
        this.file = file;
    }

    /**
     * Triggers plugins and opens the file if necessary
     */
    @Override
    public void widgetSelected(final SelectionEvent e) {
        //Plugins can intercept the default execution. 
        boolean launch = PluginManager.getInstance().interceptClick(file);
        if (launch) {
            if (!Program.launch(file.getAbsolutePath())) {
                throw new RuntimeException("Cannot find program for opening "
                        + file);
            }
        }
    }
}
