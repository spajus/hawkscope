package com.varaneckas.hawkscope.menu;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.listeners.ExitActionListener;
import com.varaneckas.hawkscope.listeners.TrayPopupMenuMouseListener;
public class TrayPopupMenu extends JPopupMenu {

    private static final long serialVersionUID = -5946207879630091657L;

    private static final Log log = LogFactory.getLog(TrayPopupMenu.class);
    
    private static final TrayPopupMenu instance = new TrayPopupMenu();
    
    private State state = MenuClosedState.getInstance();
    
    public static TrayPopupMenu getInstance() {
        return instance;
    }
    
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    private TrayPopupMenu() {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                log.info("Hiding");
                setVisible(false);
            }
        });
        setLabel("Hard Drives");
        File[] roots = File.listRoots();
        // menubar.add(menu);
        for (File root : roots) {
            log.info("Listing roots");
            final FolderMenu item = new FolderMenu(root);
            item.setMnemonic('0');
            item.setText(root.getAbsolutePath());
            add(item);
        }
        add(new JSeparator(SwingConstants.HORIZONTAL));
        JMenuItem exit = new JMenuItem("Exit");
        addMouseListener(new TrayPopupMenuMouseListener());
        exit.addActionListener(new ExitActionListener());
        add(exit);
    }
}
