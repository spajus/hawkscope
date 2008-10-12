package com.varaneckas.hawkscope.gui.swing;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.util.IOUtils;

/**
 * Registers uncaught exception handler that displays error message inside
 * Swing dialog
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SwingUncaughtExceptionHandler implements UncaughtExceptionHandler {

    /**
     * Logger
     */
    private static final Log log = LogFactory
            .getLog(SwingUncaughtExceptionHandler.class);
    
    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        //workaround for some weird exception that has no impact for application
        if ((e instanceof ClassCastException) 
                && e.getMessage().matches(".*java.awt.TrayIcon.*")) {
            return;
        }
        final JTextArea text = new JTextArea(Version.getBugReport(e));
        text.setColumns(60);
        text.setRows(6);
        text.setEditable(false);
        final int choice = JOptionPane.showOptionDialog(new JDialog(), 
                new JScrollPane(text), 
                Version.APP_NAME, 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.ERROR_MESSAGE, 
                null, 
                new String[] {"Copy to Clipboard", "Cancel"}, 
                "Copy to Clipboard");
        if (choice == 0) {
            IOUtils.copyToClipboard(text.getText());
        }
        MenuFactory.getMenuFactory().getMainMenu().forceHide();
        log.error("Uncaught exception", e);
    }
}
