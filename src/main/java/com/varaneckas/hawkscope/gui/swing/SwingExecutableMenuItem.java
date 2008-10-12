package com.varaneckas.hawkscope.gui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JMenuItem;

import com.varaneckas.hawkscope.menu.Command;
import com.varaneckas.hawkscope.menu.ExecutableMenuItem;

public class SwingExecutableMenuItem implements ExecutableMenuItem, SwingMenuItem {

    private final JMenuItem item;
    
    public SwingExecutableMenuItem() {
        item = new JMenuItem();
    }
    
    @Override
    public Command getCommand() {
        return null;
    }

    @Override
    public void setCommand(final Command command) {
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                command.execute();
            }
        });
    }

    @Override
    public void setEnabled(boolean enabled) {
        item.setEnabled(enabled);
    }

    @Override
    public void setIcon(Object icon) {
        item.setIcon((Icon) icon);
    }

    @Override
    public void setText(String text) {
        item.setText(text);
    }

    @Override
    public void setToolTipText(String text) {
        item.setToolTipText(text);
    }

    @Override
    public JMenuItem getMenuItem() {
        return item;
    }

}
