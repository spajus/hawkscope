package com.varaneckas.hawkscope.menu;

import java.awt.event.MouseEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * State when {@link TrayPopupMenu} is closed (invisible) 
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class MenuClosedState extends State {

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(MenuClosedState.class);
    
    /**
     * Singleton instance
     */
    private static final MenuClosedState instance = new MenuClosedState();
    
    /**
     * Singleton instance getter
     * 
     * @return instance
     */
    public static MenuClosedState getInstance() {
        return instance;
    }
    
    /**
     * Singleton constructor
     */
    private MenuClosedState() {
        //nothing to do
    }
    
    @Override
    public void act(final MouseEvent event) {
        final TrayPopupMenu menu = TrayPopupMenu.getInstance();
        menu.setLocation(event.getX(), event.getY());
        menu.setInvoker(menu);
        menu.setVisible(true);
        menu.setState(MenuOpenState.getInstance());
    }

    @Override
    public void init() {
        if (log.isDebugEnabled()) {
            log.debug("Menu closed. Free mem before cleanup: " 
                    + Runtime.getRuntime().freeMemory() / (1024*1024));
        }
        TrayPopupMenu.getInstance().removeAll();
        TrayPopupMenu.getInstance().loadMenu();
        Runtime.getRuntime().gc();
    }
    
}
