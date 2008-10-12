package com.varaneckas.hawkscope.gui.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;

/**
 * Mouse listener that controls the {@link SwingMainMenu}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SwingTrayIconListener extends MouseAdapter {

    @Override
    public void mouseClicked(final MouseEvent e) {
        StateEvent se = new StateEvent();
        se.setX(e.getX());
        se.setY(e.getY());
        MenuFactory.getMenuFactory().getMainMenu().getState().act(se);
    }
    
}
