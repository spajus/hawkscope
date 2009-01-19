package com.varaneckas.hawkscope.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.menu.Command;
import com.varaneckas.hawkscope.menu.ExecutableMenuItem;

/**
 * {@link ExecutableMenuItem} - SWT implementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTExecutableMenuItem implements ExecutableMenuItem, SWTMenuItem {

    /**
     * Menu item text
     */
    private String text;

    /**
     * Menu item icon
     */
    private Object icon;

    /**
     * Menu item command
     */
    private Command command;

    /**
     * Is the item enabled?
     */
    private boolean enabled = true;
    
    @Override
    public void createMenuItem(final Menu parent) {
        final MenuItem mi = new MenuItem(parent, SWT.PUSH);
        mi.setImage((Image) icon);
        mi.setText(text);
        mi.setEnabled(enabled);
        mi.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                command.execute();
            }
        });
    }

    @Override
    public void setCommand(final Command command) {
        this.command = command;
    }

    @Override
    public void setIcon(final Object icon) {
        this.icon = icon;
    }

    @Override
    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public void setToolTipText(final String text) {
        //unsupported feature
    }

    @Override
    public Command getCommand() {
        return command;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }

}
