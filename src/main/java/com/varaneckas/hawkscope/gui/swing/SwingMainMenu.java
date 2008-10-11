package com.varaneckas.hawkscope.gui.swing;

import java.io.File;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.listeners.AboutActionListener;
import com.varaneckas.hawkscope.listeners.ExitActionListener;
import com.varaneckas.hawkscope.listeners.HideActionListener;
import com.varaneckas.hawkscope.listeners.TrayPopupMenuMouseListener;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MainMenu;
import com.varaneckas.hawkscope.menu.state.MenuClosedState;
import com.varaneckas.hawkscope.menu.state.State;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.PathUtils;

/**
 * Main popup menu of Hawkscope application
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SwingMainMenu extends JPopupMenu implements MainMenu {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -5946207879630091657L;

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(SwingMainMenu.class);
    
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
    
    /* (non-Javadoc)
     * @see com.varaneckas.hawkscope.menu.MainMenu#forceHide()
     */
    public void forceHide() {
        setState(MenuClosedState.getInstance());
        setVisible(false);
    }
    
    /* (non-Javadoc)
     * @see com.varaneckas.hawkscope.menu.MainMenu#getState()
     */
    public State getState() {
        return state;
    }

    /* (non-Javadoc)
     * @see com.varaneckas.hawkscope.menu.MainMenu#setState(com.varaneckas.hawkscope.menu.state.State)
     */
    public void setState(final State state) {
        this.state = state;
        state.init();
    }
    
    /**
     * Constructor
     */
    public SwingMainMenu() {
        hideItem.addActionListener(new HideActionListener());
        hideItem.setIcon(IconFactory.getIcon("hide"));
        exitItem.addActionListener(new ExitActionListener());
        exitItem.setIcon(IconFactory.getIcon("exit"));
        aboutItem.addActionListener(new AboutActionListener());
        aboutItem.setIcon(IconFactory.getIcon("about"));
        addMouseListener(new TrayPopupMenuMouseListener());
    }
    
    /* (non-Javadoc)
     * @see com.varaneckas.hawkscope.menu.MainMenu#loadMenu()
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
            addSeparator();
        }
    }

    /**
     * Adds static menu items
     */
    private void addStaticItems() {
        addSeparator();
        add(hideItem);
        add(aboutItem);
        add(exitItem);
    }
    
    @Override
    public void clearMenu() {
        removeAll();
    }
    
    @Override
    public void showMenu(int x, int y) {
        setLocation(x, y);
        setInvoker(this);
        setVisible(true);
        SwingUtilities.windowForComponent(this).setAlwaysOnTop(true);
    }
}
