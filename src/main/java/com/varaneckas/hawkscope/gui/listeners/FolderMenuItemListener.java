package com.varaneckas.hawkscope.gui.listeners;

import java.io.File;
import java.util.Arrays;

import org.eclipse.swt.program.Program;

import com.varaneckas.hawkscope.gui.swt.SWTExecutableMenuItem;
import com.varaneckas.hawkscope.gui.swt.SWTFolderMenu;
import com.varaneckas.hawkscope.gui.swt.SWTIconFactory;
import com.varaneckas.hawkscope.gui.swt.SWTMenuFactory;
import com.varaneckas.hawkscope.menu.DynamicFileFilter;
import com.varaneckas.hawkscope.plugin.PluginManager;
import com.varaneckas.hawkscope.util.MenuUtils;
import com.varaneckas.hawkscope.util.OSUtils;

/**
 * Folder Menu Item Listener
 * 
 * Loads menu contents on mouse over
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class FolderMenuItemListener implements MenuItemListener {
    
    /**
     * Lazy loading flag
     */
    private boolean loaded = false;
    
    /**
     * Target menu
     */
    private final SWTFolderMenu folderMenu;
    
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
    public FolderMenuItemListener(final SWTFolderMenu menu, final File file) {
        this.folderMenu = menu;
        this.file = file;
    }

    public synchronized void itemSelected() {
        if (!loaded && file != null && file.isDirectory()) {
            final File[] files = file.listFiles(DynamicFileFilter.getInstance());
            long counter = 0L;
            SWTFolderMenu workMenu = folderMenu;
            if (files == null || files.length == 0) {                
                final SWTExecutableMenuItem empty = SWTMenuFactory.newExecutableMenuItem();
                empty.setIcon(SWTIconFactory.getInstance().getIcon("empty"));
                empty.setText("Empty...");
                empty.setEnabled(false);
                workMenu.addMenuItem(empty);
            } else {
                Arrays.sort(files);
                for (final File ff : files) {
                    if (ff.isDirectory()) {
                        if (OSUtils.isMacApp(ff)) {
                            workMenu.addMenuItem(SWTMenuFactory.newFileMenuItem(ff));
                        } else {
                            workMenu.addMenuItem(SWTMenuFactory.newFolderMenu(ff));
                        }
                    } else {
                        workMenu.addMenuItem(SWTMenuFactory.newFileMenuItem(ff));
                    }
                    if (++counter % MENU_SIZE == 0 && counter < files.length) {
                        SWTFolderMenu more = SWTMenuFactory.newFolderMenu(null);
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
