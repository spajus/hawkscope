package com.varaneckas.hawkscope.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * Exit {@link ActionListener}
 * 
 * Quits the application on left click
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class ExitActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
            System.exit(0);
        } else {
            MenuFactory.getMainMenu().forceHide();
        }
    }

}
