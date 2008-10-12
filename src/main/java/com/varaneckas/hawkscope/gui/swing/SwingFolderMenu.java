package com.varaneckas.hawkscope.gui.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.varaneckas.hawkscope.listeners.FolderMenuItemListener;
import com.varaneckas.hawkscope.listeners.MenuItemListener;
import com.varaneckas.hawkscope.menu.FolderMenu;
import com.varaneckas.hawkscope.menu.MenuItem;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.PathUtils;

/**
 * Folder {@link JMenu}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SwingFolderMenu extends FolderMenu implements SwingMenuItem {
    
    private JMenu menu;
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 5526827274886307261L;

    /**
     * Constructor
     */
    public SwingFolderMenu(final File file) {
        super(file);
        menu = new JMenu();
        if (this.file != null) {
            final MenuItemListener listener = new FolderMenuItemListener(this, this.file);  
            if (this.file != null) {
                menu.setText(PathUtils.getFileName(this.file));
                menu.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        listener.itemSelected();
                    }
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        listener.itemClicked();
                    }
                    
                });
            }
            menu.setIcon((Icon) IconFactory.getIconFactory().getIcon(this.file));
        }
    }

    @Override
    public void setIcon(Object icon) {
        menu.setIcon((Icon) icon);
    }

    @Override
    public void setText(String text) {
        menu.setText(text);
    }

    @Override
    public void setToolTipText(String text) {
        menu.setToolTipText(text);
    }

    @Override
    public void setEnabled(boolean enabled) {
        menu.setEnabled(enabled);
    }

    @Override
    public void addMenuItem(MenuItem item) {
        menu.add(((SwingMenuItem) item).getMenuItem());
    }

    @Override
    public JMenuItem getMenuItem() {
        return menu;
    }

    @Override
    public void addSeparator() {
        menu.addSeparator();
    }

}
