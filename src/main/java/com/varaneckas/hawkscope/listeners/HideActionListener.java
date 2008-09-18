package com.varaneckas.hawkscope.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.varaneckas.hawkscope.menu.MenuClosedState;
import com.varaneckas.hawkscope.menu.TrayPopupMenu;

public class HideActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        TrayPopupMenu.getInstance().forceHide();
    }
    
    

}
