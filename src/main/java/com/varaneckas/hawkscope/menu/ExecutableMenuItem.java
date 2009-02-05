package com.varaneckas.hawkscope.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.command.Command;

/**
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class ExecutableMenuItem implements com.varaneckas.hawkscope.menu.MenuItem {

    /**
     * Menu item text
     */
    private String text;

    /**
     * Menu item icon
     */
    private Image icon;

    /**
     * Menu item command
     */
    private Command command;

    /**
     * Is the item enabled?
     */
    private boolean enabled = true;
    
    public void createMenuItem(final Menu parent) {
        final MenuItem mi = new MenuItem(parent, SWT.PUSH);
        mi.setImage((Image) icon);
        mi.setText(text);
        mi.setEnabled(enabled);
        mi.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                command.execute();
            }
        });
    }

    public void setCommand(final Command command) {
        this.command = command;
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

    public Command getCommand() {
        return command;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }

}
