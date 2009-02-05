package com.varaneckas.hawkscope.gui.swt;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;

import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.plugin.PluginManager;
import com.varaneckas.hawkscope.util.PathUtils;

/**
 * {@link FolderMenu} - SWT implementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTFolderMenu implements SWTMenuItem {
    
    /**
     * Target folder
     */
    final protected File file;
    
    /**
     * Menu text
     */
    private String text;
    
    /**
     * Menu icon
     */
    private Image icon;
    
    /**
     * Is menu enabled?
     */
    private boolean enabled = true;

    /**
     * SWT Menu item object
     */
    private org.eclipse.swt.widgets.MenuItem menu;
    
    /**
     * SWT Submenu object
     */
    private Menu submenu;

    public void createMenuItem(final Menu parent) {
        menu = new org.eclipse.swt.widgets.MenuItem(parent, SWT.CASCADE);
        submenu = new Menu(parent);
        final FolderMenuItemListener listener = new FolderMenuItemListener(this, 
                this.file);
        addOpenAction(listener);
        PluginManager.getInstance()
                .enhanceFolderMenu(file, menu, submenu, listener);
        menu.setMenu(submenu);
        menu.setText(text);
        menu.setEnabled(enabled);
        menu.setImage((Image) icon);
        if (this.file != null) {
            submenu.addMenuListener(new MenuAdapter() {
                @Override
                public void menuShown(final MenuEvent e) {
                    listener.itemSelected();
                }
            });
        }
    }

    /**
     * Adds "Open" action to the menu
     * 
     * @param listener Folder menu item listener
     */
    private void addOpenAction(final FolderMenuItemListener listener) {
        if (file != null) {
            org.eclipse.swt.widgets.MenuItem open = 
                new org.eclipse.swt.widgets.MenuItem(submenu, SWT.PUSH);
            open.setImage(SWTIconFactory.getInstance().getIcon("open"));
            open.setText("Open");
            open.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent e) {
                    listener.itemClicked();
                }
            });
        }
    }
    
    /**
     * Initializing constructor
     * 
     * @param file target folder
     */
    public SWTFolderMenu(final File file) {
        this.file = file;
        if (file != null) {
            this.text = PathUtils.getFileName(file);
            this.icon = SWTIconFactory.getInstance().getIcon(file);
        } 
    }
    
    public void addMenuItem(final SWTMenuItem item) {
        item.createMenuItem(submenu);
    }

    public void addSeparator() {
        new org.eclipse.swt.widgets.MenuItem(menu.getMenu(), SWT.SEPARATOR);
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setIcon(final Image icon) {
        this.icon = icon;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setToolTipText(String text) {
        //unsupported feature
    }

}
