package com.varaneckas.hawkscope.menu;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.listeners.AboutCommand;
import com.varaneckas.hawkscope.gui.listeners.ExitCommand;
import com.varaneckas.hawkscope.gui.listeners.HideCommand;
import com.varaneckas.hawkscope.menu.state.MenuClosedState;
import com.varaneckas.hawkscope.menu.state.State;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.PathUtils;

/**
 * Abstract Main menu 
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class MainMenu {

    /**
     * Current menu state
     */
    protected State state = MenuClosedState.getInstance();
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(MainMenu.class);
    
    /**
     * Displays the menu
     * 
     * @param x coordinate x
     * @param y coordinate y
     */
    public abstract void showMenu(int x, int y);
    
    /**
     * Forces menu to go to {@link MenuClosedState}
     */
    public abstract void forceHide();

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
        if (!this.state.equals(state)) {
            this.state = state;
            state.init();
        }
    }

    /**
     * Loads the menu
     */
    public void loadMenu() {
        loadQuickAccessMenu();
        final File[] roots = File.listRoots();
        for (final File root : roots) {
            boolean loadFloppy = ConfigurationFactory.getConfigurationFactory()
                    .getConfiguration().isFloppyDrivesDisplayed();
            if (loadFloppy || !PathUtils.isFloppy(root)) {
            log.debug("Generating menu for: " + root.getAbsolutePath());
                final FolderMenu item = MenuFactory.getMenuFactory()
                        .newFolderMenu(root);
                item.setText(PathUtils.getFileName(root));
                item.setIcon(IconFactory.getIconFactory().getIcon(root));
                item.setToolTipText("" + root.getUsableSpace() / (1024*1024*1024) 
                        + "G free");
                addMenuItem(item);
            }
        }
        addStaticItems();
    }
    
    /**
     * Adds a menu item
     * 
     * @param item menu item to add
     */
    public abstract void addMenuItem(MenuItem item);
    
    /**
     * Adds a separator
     */
    public abstract void addSeparator();
    
    /**
     * Loads quick access menu
     */
    private void loadQuickAccessMenu() {
        final List<File> quick = ConfigurationFactory.getConfigurationFactory()
                .getConfiguration().getQuickAccessList();
        if (quick != null && quick.size() > 0) {
            for (final File custom : quick) {
                addMenuItem(MenuFactory.getMenuFactory().newFolderMenu(custom));
            }
            addSeparator();
        }
    }   
    
    /**
     * Adds static menu items
     */
    private void addStaticItems() {
        addSeparator();
        addExecutableMenuItem("hide", "Hide", new HideCommand());
        addExecutableMenuItem("about", "About", new AboutCommand());
        addExecutableMenuItem("exit", "Exit", new ExitCommand());
    }

    private void addExecutableMenuItem(final String name, 
            final String text, final Command command) {
        final ExecutableMenuItem item = MenuFactory.getMenuFactory()
                .newExecutableMenuItem();
        item.setCommand(command);
        item.setText(text);
        item.setIcon(IconFactory.getIconFactory().getIcon(name));
        addMenuItem(item);
    }    
    
    /**
     * Clears menu contents
     */
    public abstract void clearMenu();

}