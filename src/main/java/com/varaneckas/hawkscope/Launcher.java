package com.varaneckas.hawkscope;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.menu.TrayPopupMenu;
import com.varaneckas.hawkscope.tray.TrayManager;

public class Launcher {
    
    private static final Log log = LogFactory.getLog(Launcher.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            registerExceptionHandler();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            final TrayManager tm = TrayManager.getInstance();
            tm.load();
            ConfigurationFactory.getConfigurationFactory().getConfiguration();
//            JFrame xframe = new JFrame("Hawkscope Config");
//            xframe.setContentPane(new SettingsPanel());
//            xframe.pack();
//            xframe.setVisible(true);
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
                TrayPopupMenu.getInstance().forceHide();
            }
        });
    }
    

}
