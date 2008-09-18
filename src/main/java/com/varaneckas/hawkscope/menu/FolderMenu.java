package com.varaneckas.hawkscope.menu;

import java.io.File;

import javax.swing.JMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.varaneckas.hawkscope.listeners.FolderMenuMouseListener;

public class FolderMenu extends JMenu {
    
    private static final long serialVersionUID = 5526827274886307261L;

    public FolderMenu(final File file) {
        if (file != null) {
            String name = file.getName();
            if (name == null || name.equals("")) {
                name = file.getPath();
            }
            setText(name);
            addMouseListener(new FolderMenuMouseListener(this, file));  
            addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    // TODO Auto-generated method stub
                    System.out.println(e);
                }
            });
        }
        setIcon(IconFactory.getIcon("folder"));
    }
}
