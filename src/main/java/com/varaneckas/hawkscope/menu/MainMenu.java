package com.varaneckas.hawkscope.menu;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.listeners.AboutCommand;
import com.varaneckas.hawkscope.listeners.ExitCommand;
import com.varaneckas.hawkscope.listeners.HideCommand;
import com.varaneckas.hawkscope.menu.state.MenuClosedState;
import com.varaneckas.hawkscope.menu.state.State;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.PathUtils;

public abstract class MainMenu {

    protected State state = MenuClosedState.getInstance();
    
    private static final Log log = LogFactory.getLog(MainMenu.class);
    
    private Map<String, MenuItem> statics = new HashMap<String, MenuItem>();
    
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
        this.state = state;
        state.init();
    }

    public void loadMenu() {
        loadQuickAccessMenu();
        final File[] roots = File.listRoots();
        for (final File root : roots) {
            boolean loadFloppy = ConfigurationFactory.getConfigurationFactory()
                    .getConfiguration().isFloppyDrivesDisplayed();
            if (loadFloppy || !PathUtils.isFloppy(root)) {
            log.debug("Generating menu for: " + root.getAbsolutePath());
                final FolderMenu item = MenuFactory.getMenuFactory().newFolderMenu(root);
                item.setText(PathUtils.getFileName(root));
                item.setIcon(IconFactory.getIconFactory().getIcon(root));
                item.setToolTipText("" + root.getUsableSpace() / (1024*1024*1024) 
                        + "G free");
                addMenuItem(item);
            }
        }
        addStaticItems();
    }
    
    public abstract void addMenuItem(MenuItem item);
    
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
        if (statics.size() == 0) {
            addExecutableMenuItem("hide", "Hide", new HideCommand());
            addExecutableMenuItem("about", "About", new AboutCommand());
            addExecutableMenuItem("exit", "Exit", new ExitCommand());
        }
        addMenuItem(statics.get("hide"));
        addMenuItem(statics.get("about"));
        addMenuItem(statics.get("exit"));
    }

    private void addExecutableMenuItem(String name, String text, Command command) {
        ExecutableMenuItem item = MenuFactory.getMenuFactory().newExecutableMenuItem();
        item.setCommand(command);
        item.setText(text);
        item.setIcon(IconFactory.getIconFactory().getIcon(name));
        statics.put(name, item);
    }    
    
    /**
     * Clears menu contents
     */
    public abstract void clearMenu();

}