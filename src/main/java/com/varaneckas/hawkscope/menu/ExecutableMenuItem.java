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
    
    /**
     * Creates the Executable menu item
     */
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

    /**
     * Sets the command to execute on click
     * 
     * @param command
     */
    public void setCommand(final Command command) {
        this.command = command;
    }

    /**
     * Sets the menu item icon image
     */
    public void setIcon(final Image icon) {
        this.icon = icon;
    }

    /**
     * Sets the menu item text
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * Gets the {@link Command} to execute
     * 
     * @return command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Sets the Enabled flag
     */
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * Tells if item is enabled
     * 
     * @return
     */
    public boolean isEnabled() {
        return this.enabled;
    }

}
