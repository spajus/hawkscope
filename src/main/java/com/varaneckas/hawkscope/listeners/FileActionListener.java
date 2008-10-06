package com.varaneckas.hawkscope.listeners;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.menu.MainPopupMenu;
import com.varaneckas.hawkscope.util.IOUtils;
import com.varaneckas.hawkscope.util.IconFactory;

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
     * Logger
     */
    private static final Log log = LogFactory.getLog(FileActionListener.class);
    
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
            if (file.canRead()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(MainPopupMenu.getInstance(), 
                        "Cannot open this file!", 
                        Version.APP_NAME,
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (final IOException e1) {
            log.error("Failed opening file: " + file, e1);
            int choice = JOptionPane.showConfirmDialog(MainPopupMenu.getInstance(), 
                        "Error while opening file: " + file.getAbsolutePath() 
                        + "\nFile type may not be associated with any application."
                        + "\nCopy Hawkscope Error Report to Clipboard?", 
                        Version.APP_NAME, 
                        JOptionPane.OK_CANCEL_OPTION, 
                        JOptionPane.ERROR_MESSAGE,
                        IconFactory.getFileSystemIcon(file));
            if (choice == JOptionPane.OK_OPTION) {
                IOUtils.copyToClipboard(Version.getBugReport(e1));
            }
        }
        MainPopupMenu.getInstance().forceHide();
    }    
    
}
