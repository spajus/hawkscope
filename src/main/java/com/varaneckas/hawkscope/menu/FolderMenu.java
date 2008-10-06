package com.varaneckas.hawkscope.menu;

import java.io.File;

import javax.swing.JMenu;

import com.varaneckas.hawkscope.listeners.FolderMenuMouseListener;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.PathUtils;

/**
 * Folder {@link JMenu}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class FolderMenu extends JMenu {
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 5526827274886307261L;

    /**
     * Constructor
     */
    public FolderMenu(final File file) {
        if (file != null) {
            setText(PathUtils.getFileName(file));
            addMouseListener(new FolderMenuMouseListener(this, file));  
        }
        setIcon(IconFactory.getIcon(file));
    }
}
