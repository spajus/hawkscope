package com.varaneckas.hawkscope.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.menu.TrayPopupMenu;

/**
 * Registers uncaught exception handler that displays error message inside
 * Swing dialog
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SimpleUncaughtExceptionHandler implements UncaughtExceptionHandler {

    /**
     * Logger
     */
    private static final Log log = LogFactory
            .getLog(SimpleUncaughtExceptionHandler.class);
    
    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        //workaround for some weird exception that has no impact for application
        if ((e instanceof ClassCastException) 
                && e.getMessage().matches(".*java.awt.TrayIcon.*")) {
            return;
        }
        final Writer stringWriter = new StringWriter();
        final PrintWriter w = new PrintWriter(stringWriter);
        e.printStackTrace(w);
        final String error = e.getMessage().replaceAll(": ", ":\n") 
                + "\n\n" + stringWriter.toString();
        final JTextArea text = new JTextArea(error);
        text.setColumns(60);
        text.setRows(6);
        text.setEditable(false);
        JOptionPane.showMessageDialog(null, 
                new JScrollPane(text)
                , "Hawkscope Error"
                , JOptionPane.ERROR_MESSAGE);
        TrayPopupMenu.getInstance().forceHide();
        log.error("Uncaught exception", e);
    }
}
