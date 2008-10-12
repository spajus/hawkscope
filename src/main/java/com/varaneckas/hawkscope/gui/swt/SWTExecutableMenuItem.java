package com.varaneckas.hawkscope.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.menu.Command;
import com.varaneckas.hawkscope.menu.ExecutableMenuItem;

public class SWTExecutableMenuItem implements ExecutableMenuItem {

    private String text;

    private Object icon;

    private String toolTipText;

    private Command command;

    private boolean enabled;
    
    public MenuItem getMenuItem(Menu parent) {
        MenuItem mi = new MenuItem(parent, SWT.PUSH);
        mi.setImage((Image) icon);
        mi.setText(text);
        mi.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                command.execute();
            }
        });
        return mi;
    }

    @Override
    public void setCommand(final Command command) {
        this.command = command;
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
        this.toolTipText = text;
    }

    @Override
    public Command getCommand() {
        return command;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }

}
