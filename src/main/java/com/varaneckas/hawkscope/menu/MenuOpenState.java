package com.varaneckas.hawkscope.menu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MenuOpenState extends State {

    private static final Log log = LogFactory.getLog(MenuOpenState.class);
    
    private static final MenuOpenState self = new MenuOpenState();
    
    private MenuOpenState() {}
    
    public static MenuOpenState getInstance() {
        return self;
    }
    
    @Override
    public void act(MouseEvent event) {
        log.info("Menu open: " + event);
        TrayPopupMenu.getInstance().setVisible(false);
        TrayPopupMenu.getInstance().setState(MenuClosedState.getInstance());
        
    }

}
