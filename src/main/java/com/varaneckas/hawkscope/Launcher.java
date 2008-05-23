package com.varaneckas.hawkscope;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.varaneckas.hawkscope.menu.TrayPopupMenu;
import com.varaneckas.hawkscope.tray.TrayManager;

public class Launcher {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            registerExceptionHandler();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            final TrayPopupMenu tpm = new TrayPopupMenu();
            final TrayManager tm = TrayManager.getInstance();
            tm.setTrayPopupMenu(tpm);
            tm.setTrayIconImage("hawk16.png");
            tm.load();
        } catch (final Throwable e) {
            e.printStackTrace();
        }
    }
    
    private static void registerExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(
                new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if ((e instanceof ClassCastException) 
                        && e.getMessage().matches(".*java.awt.TrayIcon.*")) {
                    return;
                }
                JTextArea text = new JTextArea(e.getMessage()
                        .replaceAll(": ", ":\n"));
                text.setColumns(50);
                text.setRows(5);
                text.setEditable(false);
                JOptionPane.showMessageDialog(null, 
                        new JScrollPane(text)
                        , "Error"
                        , JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    

}
