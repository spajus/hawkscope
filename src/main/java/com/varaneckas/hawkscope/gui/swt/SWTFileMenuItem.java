package com.varaneckas.hawkscope.gui.swt;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.plugin.PluginManager;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.PathUtils;

/**
 * {@link FileMenuItem} - SWT implementation
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTFileMenuItem implements FileMenuItem, SWTMenuItem {

    /**
     * Menu item text
     */
    private String text;
    
    /**
     * Menu item icon
     */
    private Object icon;
    
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
    public SWTFileMenuItem(final File file) {
        text = PathUtils.getFileName(file);
        icon = IconFactory.getIconFactory().getIcon(file);
        this.file = file;
    }
    
    public void createMenuItem(final Menu parent) {
        menuItem = new MenuItem(parent, SWT.PUSH);
        menuItem.setImage((Image) icon);
        menuItem.setText(text);
        menuItem.setEnabled(enabled);
        menuItem.addSelectionListener(new SelectionAdapter() {
           @Override
            public void widgetSelected(SelectionEvent e) {
                if (!Program.launch(file.getAbsolutePath())) {
                    throw new RuntimeException("Cannot find program for opening " + file);
                }
                SWTMenuFactory.getMainMenu().forceHide();
            } 
        });
        PluginManager.getInstance().enhanceFileMenuItem(menuItem, this.file);
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

    public void setToolTipText(final String text) {
        //unsupported feature
    }

}
