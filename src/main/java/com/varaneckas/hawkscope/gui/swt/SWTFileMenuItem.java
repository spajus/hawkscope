package com.varaneckas.hawkscope.gui.swt;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.PathUtils;

public class SWTFileMenuItem implements FileMenuItem, SWTMenuItem {

    private String text;
    private Object icon;
    private boolean enabled = true;
    
    private MenuItem menuItem;
    
    public SWTFileMenuItem(File file) {
        text = PathUtils.getFileName(file);
        icon = IconFactory.getIconFactory().getIcon(file);
    }
    
    public void createMenuItem(Menu parent) {
        menuItem = new MenuItem(parent, SWT.PUSH);
        menuItem.setImage((Image) icon);
        menuItem.setText(text);
        menuItem.setEnabled(enabled);
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
