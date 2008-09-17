package com.varaneckas.hawkscope.menu;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import com.varaneckas.hawkscope.listeners.ExitActionListener;
public class TrayPopupMenu extends JPopupMenu {

    private static final long serialVersionUID = -5946207879630091657L;

    public TrayPopupMenu() {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                setVisible(false);
            }
        });
        setLabel("Hard Drives");
        File[] roots = File.listRoots();
        // menubar.add(menu);
        for (File root : roots) {
            final FolderMenu item = new FolderMenu(root);
            item.setMnemonic('0');
            item.setText(root.getAbsolutePath());
            add(item);
        }
        add(new JSeparator(SwingConstants.HORIZONTAL));
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitActionListener());
        add(exit);
    }
}
