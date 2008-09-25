package com.varaneckas.hawkscope.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.menu.TrayPopupMenu;

public class TrayPopupMenuMouseListener extends MouseAdapter {
    private static final Log log = LogFactory
            .getLog(TrayPopupMenuMouseListener.class);
    
    @Override
    public void mouseClicked(MouseEvent e) {
        TrayPopupMenu.getInstance().getState().act(e);
    }
    
}
