package com.varaneckas.hawkscope.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.varaneckas.hawkscope.menu.TrayPopupMenu;

/**
 * Mouse listener that controls the {@link TrayPopupMenu}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class TrayPopupMenuMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(final MouseEvent e) {
        TrayPopupMenu.getInstance().getState().act(e);
    }
    
}
