package com.varaneckas.hawkscope.menu;

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
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * Main popup menu of Hawkscope application
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class TrayPopupMenu extends JPopupMenu {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -5946207879630091657L;

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(TrayPopupMenu.class);
    
    /**
     * Singleton instance
     */
    private static final TrayPopupMenu instance = new TrayPopupMenu();
    
    /**
     * Current {@link State}
     */
    private State state = MenuClosedState.getInstance();
    
    /**
     * Menu separator
     */
    private final JSeparator separatorItem = 
        new JSeparator(SwingConstants.HORIZONTAL);
    
    /**
     * "Hide" menu item
     */
    private final JMenuItem hideItem = new JMenuItem("Hide");
    
    /**
     * "Exit" menu item
     */
    private final JMenuItem exitItem = new JMenuItem("Exit");    
    
    /**
     * Singleton instance getter
     * 
     * @return instance
     */
    public static TrayPopupMenu getInstance() {
        return instance;
    }
    
    /**
     * Forces menu to go to {@link MenuClosedState}
     */
    public void forceHide() {
        setState(MenuClosedState.getInstance());
        setVisible(false);
    }
    
    /**
     * Gets current {@link State}
     * 
     * @return current state
     */
    public State getState() {
        return state;
    }

    /**
     * Sets menu {@link State}
     * 
     * @param state new state
     */
    public void setState(final State state) {
        this.state = state;
        state.init();
    }
    
    /**
     * Singleton constructor
     */
    private TrayPopupMenu() {
        hideItem.addActionListener(new HideActionListener());
        hideItem.setIcon(IconFactory.getIcon("hide"));
        exitItem.addActionListener(new ExitActionListener());
        exitItem.setIcon(IconFactory.getIcon("exit"));
        addMouseListener(new TrayPopupMenuMouseListener());
    }

    /**
     * Loads menu contents
     */
    public void loadMenu() {
        final File[] roots = File.listRoots();
        for (final File root : roots) {
            log.info("Generating menu for: " + root.getAbsolutePath());
            final FolderMenu item = new FolderMenu(root);
            item.setText(root.getAbsolutePath());
            item.setIcon(IconFactory.getIcon("drive"));
            item.setToolTipText("" + root.getUsableSpace() / (1024*1024*1024) 
                    + "G free");
            add(item);
        }
        addStaticItems();
    }

    /**
     * Adds static menu items
     */
    private void addStaticItems() {
        add(separatorItem);
        add(hideItem);
        add(exitItem);
    }
}
