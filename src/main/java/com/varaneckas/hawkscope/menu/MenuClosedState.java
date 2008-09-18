package com.varaneckas.hawkscope.menu;

import java.awt.event.MouseEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MenuClosedState extends State {

    private static final Log log = LogFactory.getLog(MenuClosedState.class);
    
    private static final MenuClosedState self = new MenuClosedState();
    
    public static MenuClosedState getInstance() {
        return self;
    }
    
    
    private MenuClosedState() {
        
    }
    
    @Override
    public void act(MouseEvent event) {
        log.info("menu closed: " + event);
        TrayPopupMenu menu = TrayPopupMenu.getInstance();
        menu.setLocation(event.getX(), event.getY());
        menu.setInvoker(menu);
        menu.setVisible(true);
        menu.setState(MenuOpenState.getInstance());
    }

}
