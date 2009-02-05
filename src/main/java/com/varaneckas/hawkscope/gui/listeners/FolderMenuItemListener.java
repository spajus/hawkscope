package com.varaneckas.hawkscope.gui.listeners;

import java.io.File;
import java.util.Arrays;

import org.eclipse.swt.program.Program;

import com.varaneckas.hawkscope.menu.ExecutableMenuItem;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.plugin.PluginManager;
import com.varaneckas.hawkscope.util.DynamicFileFilter;
import com.varaneckas.hawkscope.util.MenuUtils;
import com.varaneckas.hawkscope.util.OSUtils;
import com.varaneckas.hawkscope.util.SWTIconFactory;

/**
 * Folder Menu Item Listener
 * 
 * Loads menu contents on mouse over
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class FolderMenuItemListener {
    
    /**
     * Lazy loading flag
     */
    private boolean loaded = false;
    
    /**
     * Target menu
     */
    private final FolderMenu folderMenu;
    
    /**
     * Target folder
     */
    private final File file;
    
    /**
     * Number of Menu Items in one Menu
     */
    private static final int MENU_SIZE = MenuUtils.getAutoMenuSize();
    
    /**
     * Constructor
     * 
     * @param menu tagert
     * @param file target
     */
    public FolderMenuItemListener(final FolderMenu menu, final File file) {
        this.folderMenu = menu;
        this.file = file;
    }

    public synchronized void itemSelected() {
        if (!loaded && file != null && file.isDirectory()) {
            final File[] files = file.listFiles(DynamicFileFilter.getInstance());
            long counter = 0L;
            FolderMenu workMenu = folderMenu;
            if (files == null || files.length == 0) {                
                final ExecutableMenuItem empty = MenuFactory.newExecutableMenuItem();
                empty.setIcon(SWTIconFactory.getInstance().getIcon("empty"));
                empty.setText("Empty...");
                empty.setEnabled(false);
                workMenu.addMenuItem(empty);
            } else {
                Arrays.sort(files);
                for (final File ff : files) {
                    if (ff.isDirectory()) {
                        if (OSUtils.isMacApp(ff)) {
                            workMenu.addMenuItem(MenuFactory.newFileMenuItem(ff));
                        } else {
                            workMenu.addMenuItem(MenuFactory.newFolderMenu(ff));
                        }
                    } else {
                        workMenu.addMenuItem(MenuFactory.newFileMenuItem(ff));
                    }
                    if (++counter % MENU_SIZE == 0 && counter < files.length) {
                        FolderMenu more = MenuFactory.newFolderMenu(null);
                        more.setIcon(SWTIconFactory.getInstance().getIcon("more"));
                        more.setText("More");
                        workMenu.addSeparator();
                        workMenu.addMenuItem(more);
                        workMenu = more;
                    }
                }
            }
            loaded = true;
        }
    }
    
    public void itemClicked() {
        boolean launch = PluginManager.getInstance().interceptClick(file);
        if (launch) {
            Program.launch(file.getAbsolutePath());
        }
    }
}
