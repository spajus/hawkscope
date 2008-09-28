package com.varaneckas.hawkscope.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.varaneckas.hawkscope.gui.AboutFrame;
import com.varaneckas.hawkscope.menu.TrayPopupMenu;

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
        AboutFrame.getInstance().setVisible(true);
        TrayPopupMenu.getInstance().forceHide();
    }

}
