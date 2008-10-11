package com.varaneckas.hawkscope.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.varaneckas.hawkscope.gui.swing.SwingMainMenu;
import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * Mouse listener that controls the {@link SwingMainMenu}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class TrayPopupMenuMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(final MouseEvent e) {
        MenuFactory.getMainMenu().getState().act(e);
    }
    
}
