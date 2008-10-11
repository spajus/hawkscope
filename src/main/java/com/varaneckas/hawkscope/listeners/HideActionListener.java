package com.varaneckas.hawkscope.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * Hide Menu Item {@link ActionListener}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class HideActionListener implements ActionListener {

    @Override
    public void actionPerformed(final ActionEvent e) {
        MenuFactory.getMainMenu().forceHide();
    }

}
