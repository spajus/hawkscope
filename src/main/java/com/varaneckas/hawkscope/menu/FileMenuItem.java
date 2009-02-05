package com.varaneckas.hawkscope.menu;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.gui.listeners.FileMenuItemListener;
import com.varaneckas.hawkscope.plugin.PluginManager;
import com.varaneckas.hawkscope.util.PathUtils;
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * {@link FileMenuItem} - SWT implementation
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class FileMenuItem implements com.varaneckas.hawkscope.menu.MenuItem {

    /**
     * Menu item text
     */
    private String text;
    
    /**
     * Menu item icon
     */
    private Image icon;
    
    /**
     * Is menu item enabled?
     */
    private boolean enabled = true;
    
    /**
     * Menu item data
     */
    private MenuItem menuItem;
    
    /**
     * Associated {@link File}
     */
    private File file;
    
    /**
     * Constructor that sets the target {@link File}
     * 
     * @param file associated target {@link File}
     */
    public FileMenuItem(final File file) {
        text = PathUtils.getFileName(file);
        icon = IconFactory.getInstance().getIcon(file);
        this.file = file;
    }
    
    public void createMenuItem(final Menu parent) {
        menuItem = new MenuItem(parent, SWT.PUSH);
        menuItem.setImage((Image) icon);
        menuItem.setText(text);
        menuItem.setEnabled(enabled);
        menuItem.addSelectionListener(new FileMenuItemListener(this.file));
        PluginManager.getInstance().enhanceFileMenuItem(menuItem, this.file);
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

    public void setToolTipText(final String text) {
        //unsupported feature
    }

}
