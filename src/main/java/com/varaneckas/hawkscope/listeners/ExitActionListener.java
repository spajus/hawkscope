package com.varaneckas.hawkscope.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import com.varaneckas.hawkscope.menu.TrayPopupMenu;

public class ExitActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
            System.exit(0);
        } else {
            TrayPopupMenu.getInstance().forceHide();
        }
    }

}
