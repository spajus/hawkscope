package com.varaneckas.hawkscope.gui.swt;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Menu;

import com.varaneckas.hawkscope.gui.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MenuItem;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.OSUtils;
import com.varaneckas.hawkscope.util.PathUtils;

/**
 * {@link FolderMenu} - SWT implementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTFolderMenu extends FolderMenu implements SWTMenuItem {
    
    /**
     * Menu text
     */
    private String text;
    
    /**
     * Menu icon
     */
    private Object icon;
    
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
        if (OSUtils.isMacApp(file)) {
            addExecuteAction(listener);
        }
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
            open.setImage((Image) IconFactory.getIconFactory().getIcon("open"));
            open.setText("Open");
            open.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent e) {
                    listener.itemClicked();
                }
            });
        }
    }
    
    private void addExecuteAction(final FolderMenuItemListener listener) {
        if (file != null) {
            org.eclipse.swt.widgets.MenuItem exec =
                new org.eclipse.swt.widgets.MenuItem(submenu, SWT.PUSH);
            exec.setImage((Image) IconFactory.getIconFactory().getIcon("executable"));
            exec.setText("Run Application");
            exec.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent event) {
                    Program.launch(file.getAbsolutePath());
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
        super(file);
        if (file != null) {
            this.text = PathUtils.getFileName(file);
            this.icon = IconFactory.getIconFactory().getIcon(file);
        } 
    }
    
    @Override
    public void addMenuItem(final MenuItem item) {
        if (item instanceof SWTMenuItem) {
            ((SWTMenuItem) item).createMenuItem(submenu);
        }
    }

    @Override
    public void addSeparator() {
        new org.eclipse.swt.widgets.MenuItem(menu.getMenu(), SWT.SEPARATOR);
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setIcon(final Object icon) {
        this.icon = icon;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setToolTipText(String text) {
        //unsupported feature
    }

}
