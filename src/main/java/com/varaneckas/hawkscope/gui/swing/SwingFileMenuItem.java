package com.varaneckas.hawkscope.gui.swing;

import java.io.File;

import javax.swing.Icon;
import javax.swing.JMenuItem;

import com.varaneckas.hawkscope.listeners.FileActionListener;
import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * File {@link JMenuItem}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SwingFileMenuItem implements FileMenuItem, SwingMenuItem {
    
    private JMenuItem item;
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 2512823752109399974L;

    /**
     * Constructor
     * 
     * @param file target
     */
    public SwingFileMenuItem(final File file) {
        item = new JMenuItem();
        item.setText(file.getName());
        item.addActionListener(new FileActionListener(file));
        item.setIcon((Icon) IconFactory.getIconFactory().getIcon(file));
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
    public void setEnabled(boolean enabled) {
        item.setEnabled(enabled);
    }

    @Override
    public JMenuItem getMenuItem() {
        return item;
    }

}