package com.varaneckas.hawkscope.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.varaneckas.hawkscope.menu.MainPopupMenu;

/**
 * Mouse listener that controls the {@link MainPopupMenu}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class TrayPopupMenuMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(final MouseEvent e) {
        MainPopupMenu.getInstance().getState().act(e);
    }
    
}
