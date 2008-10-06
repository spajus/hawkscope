package com.varaneckas.hawkscope.menu;

import java.io.File;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.listeners.AboutActionListener;
import com.varaneckas.hawkscope.listeners.ExitActionListener;
import com.varaneckas.hawkscope.listeners.HideActionListener;
import com.varaneckas.hawkscope.listeners.TrayPopupMenuMouseListener;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.PathUtils;

/**
 * Main popup menu of Hawkscope application
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class MainPopupMenu extends JPopupMenu {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -5946207879630091657L;

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(MainPopupMenu.class);
    
    /**
     * Singleton instance
     */
    private static final MainPopupMenu instance = new MainPopupMenu();
    
    /**
     * Current {@link State}
     */
    private State state = MenuClosedState.getInstance();
    
    /**
     * "Hide" menu item
     */
    private final JMenuItem hideItem = new JMenuItem("Hide");
    
    /**
     * "Exit" menu item
     */
    private final JMenuItem exitItem = new JMenuItem("Exit");  
    
    /**
     * "About" menu item
     */
    private final JMenuItem aboutItem = new JMenuItem("About");
    
    /**
     * Singleton instance getter
     * 
     * @return instance
     */
    public static MainPopupMenu getInstance() {
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
    private MainPopupMenu() {
        hideItem.addActionListener(new HideActionListener());
        hideItem.setIcon(IconFactory.getIcon("hide"));
        exitItem.addActionListener(new ExitActionListener());
        exitItem.setIcon(IconFactory.getIcon("exit"));
        aboutItem.addActionListener(new AboutActionListener());
        aboutItem.setIcon(IconFactory.getIcon("about"));
        addMouseListener(new TrayPopupMenuMouseListener());
    }

    /**
     * Loads menu contents
     */
    public void loadMenu() {
        loadQuickAccessMenu();
        final File[] roots = File.listRoots();
        for (final File root : roots) {
            boolean loadFloppy = ConfigurationFactory.getConfigurationFactory()
                    .getConfiguration().isFloppyDrivesDisplayed();
            if (loadFloppy || !PathUtils.isFloppy(root)) {
            log.debug("Generating menu for: " + root.getAbsolutePath());
                final FolderMenu item = new FolderMenu(root);
                item.setText(PathUtils.getFileName(root));
                item.setIcon(IconFactory.getIcon(root));
                item.setToolTipText("" + root.getUsableSpace() / (1024*1024*1024) 
                        + "G free");
                add(item);
            }
        }
        addStaticItems();
    }

    /**
     * Loads quick access menu
     */
    private void loadQuickAccessMenu() {
        final List<File> quick = ConfigurationFactory.getConfigurationFactory()
                .getConfiguration().getQuickAccessList();
        if (quick != null && quick.size() > 0) {
            for (final File custom : quick) {
                add(new FolderMenu(custom));
            }
            add(new JSeparator());
        }
    }

    /**
     * Adds static menu items
     */
    private void addStaticItems() {
        add(new JSeparator());
        add(hideItem);
        add(aboutItem);
        add(exitItem);
    }
}
