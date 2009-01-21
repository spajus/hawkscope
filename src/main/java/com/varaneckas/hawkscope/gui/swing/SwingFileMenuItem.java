package com.varaneckas.hawkscope.gui.swing;

import java.io.File;

import javax.swing.Icon;
import javax.swing.JMenuItem;

import com.varaneckas.hawkscope.gui.listeners.FileActionListener;
import com.varaneckas.hawkscope.menu.FileMenuItem;
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * {@link FileMenuItem} - Swing implementation
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SwingFileMenuItem implements FileMenuItem, SwingMenuItem {
    
    /**
     * Swing menu item
     */
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
    public void setIcon(final Object icon) {
        item.setIcon((Icon) icon);
    }

    @Override
    public void setText(final String text) {
        item.setText(text);
    }

    @Override
    public void setToolTipText(final String text) {
        item.setToolTipText(text);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        item.setEnabled(enabled);
    }

    @Override
    public JMenuItem getMenuItem() {
        return item;
    }

}