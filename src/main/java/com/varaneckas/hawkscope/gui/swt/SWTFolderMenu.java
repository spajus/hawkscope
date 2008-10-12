package com.varaneckas.hawkscope.gui.swt;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;

import com.varaneckas.hawkscope.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MenuItem;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.PathUtils;

public class SWTFolderMenu extends FolderMenu implements SWTMenuItem {
    
    private static final Log log = LogFactory.getLog(SWTFolderMenu.class);
    
    private String text;
    private Object icon;

    private org.eclipse.swt.widgets.MenuItem menu;
    private Menu submenu;
    
    public void createMenuItem(Menu parent) {
        log.info("Creating menu item for parent: " + parent);
        log.info("Text: " + text);
        menu = new org.eclipse.swt.widgets.MenuItem(parent, SWT.CASCADE);
        submenu = new Menu(parent);
        menu.setMenu(submenu);
        menu.setText(text);
        menu.setImage((Image) icon);
        final FolderMenuItemListener listener = new FolderMenuItemListener(this, this.file);
        if (this.file != null) {
            submenu.addMenuListener(new MenuAdapter() {
                @Override
                public void menuShown(MenuEvent e) {
                    listener.itemSelected();
                }
            });
            menu.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    listener.itemClicked();
                }
            });
        }
    }
    
    public SWTFolderMenu(File file) {
        super(file);
        if (file != null) {
            this.text = PathUtils.getFileName(file);
            this.icon = IconFactory.getIconFactory().getIcon(file);
        } 
    }
    
    @Override
    public void addMenuItem(MenuItem item) {
        if (item instanceof SWTMenuItem) {
            ((SWTMenuItem) item).createMenuItem(submenu);
        }
    }

    @Override
    public void addSeparator() {
        new org.eclipse.swt.widgets.MenuItem(menu.getMenu(), SWT.SEPARATOR);
    }

    @Override
    public void setEnabled(boolean enabled) {
        menu.setEnabled(enabled);
    }

    @Override
    public void setIcon(Object icon) {
        this.icon = icon;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setToolTipText(String text) {
    }

}
