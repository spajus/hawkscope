package com.varaneckas.hawkscope.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.varaneckas.hawkscope.gui.WindowFactory;
import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * About {@link ActionListener}
 * 
 * Displays About dialog
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class AboutActionListener implements ActionListener {

    @Override
    public void actionPerformed(final ActionEvent e) {
        WindowFactory.getAboutWindow().showObject();
        MenuFactory.getMainMenu().forceHide();
    }

}
