package com.varaneckas.hawkscope.menu;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.listeners.AboutCommand;
import com.varaneckas.hawkscope.gui.listeners.ExitCommand;
import com.varaneckas.hawkscope.gui.listeners.HideCommand;
import com.varaneckas.hawkscope.gui.listeners.SettingsCommand;
import com.varaneckas.hawkscope.gui.listeners.UpdateCommand;
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
    protected Log log = LogFactory.getLog(getClass());
    
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
     * Root partitions
     */
    protected File[] roots = File.listRoots();
    
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
        Configuration cfg = ConfigurationFactory.getConfigurationFactory()
                .getConfiguration();
        loadQuickAccessMenu();
        boolean addSeparator = false;
        for (final File root : roots) {
            if (cfg.getBlackList().contains(root)) {
                continue;
            }
            if (cfg.isFloppyDrivesDisplayed() || !PathUtils.isFloppy(root)) {
            log.debug("Generating menu for: " + root.getAbsolutePath());
                final FolderMenu item = MenuFactory.getMenuFactory()
                        .newFolderMenu(root);
                item.setText(PathUtils.getFileName(root));
                item.setIcon(IconFactory.getIconFactory().getIcon(root));
                addMenuItem(item);
                addSeparator = true;
            }
        }
        if (addSeparator) {
            addSeparator();
        }
        addStaticItems();
        reloadRoots();
    }
    
    /**
     * Lazy reload of root partitions
     */
    protected void reloadRoots() {
        log.debug("Performing root partition listing");
        roots = File.listRoots();
        log.debug("Root partition listing complete");
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
                try {
                    addMenuItem(MenuFactory.getMenuFactory()
                            .newFolderMenu(custom));
                } catch (final Exception e) {
                    log.warn("Skipping unloadable Quick Access List item", e);
                }
            }
            addSeparator();
        }
    }   
    
    /**
     * Adds static menu items
     */
    private void addStaticItems() {
        if (Version.isUpdateAvailable()) {
            addExecutableMenuItem("update", "Update Available!", 
                    new UpdateCommand());
        }
        addExecutableMenuItem("hide", "Hide", new HideCommand());
        addExecutableMenuItem("about", "About", new AboutCommand());
        addExecutableMenuItem("settings", "Settings", new SettingsCommand());
        addExecutableMenuItem("exit", "Exit", new ExitCommand());
    }

    /**
     * Adds an executable menu item 
     * 
     * @param name Item name
     * @param text Text on menu item
     * @param command Command to execute
     */
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
    
    /**
     * Reloads the main menu. Can be overloaded in concrete class for 
     * performance gain
     */
    public void reloadMenu() {
        clearMenu();
        loadMenu();
    }
    
}