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
import com.varaneckas.hawkscope.listeners.HideActionListener;
import com.varaneckas.hawkscope.listeners.TrayPopupMenuMouseListener;
public class TrayPopupMenu extends JPopupMenu {

    private static final long serialVersionUID = -5946207879630091657L;

    private static final Log log = LogFactory.getLog(TrayPopupMenu.class);
    
    private static final TrayPopupMenu instance = new TrayPopupMenu();
    
    private State state = MenuClosedState.getInstance();
    
    public static TrayPopupMenu getInstance() {
        return instance;
    }
    
    public void forceHide() {
        setState(MenuClosedState.getInstance());
        setVisible(false);
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
            item.setText(root.getAbsolutePath());
            item.setIcon(IconFactory.getIcon("drive"));
            add(item);
        }
        add(new JSeparator(SwingConstants.HORIZONTAL));
        JMenuItem hide = new JMenuItem("Hide");
        hide.addActionListener(new HideActionListener());
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitActionListener());
        addMouseListener(new TrayPopupMenuMouseListener());
        add(hide);
        add(exit);
    }
}
