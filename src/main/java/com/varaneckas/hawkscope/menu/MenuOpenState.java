package com.varaneckas.hawkscope.menu;

import java.awt.event.MouseEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * State when {@link TrayPopupMenu} is open (visible)
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class MenuOpenState extends State {

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(MenuOpenState.class);
    
    /**
     * Singleton instance
     */
    private static final MenuOpenState instance = new MenuOpenState();
    
    /**
     * Singleton constructor
     */
    private MenuOpenState() {
        //nothing to do
    }
    
    /**
     * Singleton instance getter
     * 
     * @return instance
     */
    public static MenuOpenState getInstance() {
        return instance;
    }
    
    @Override
    public void act(final MouseEvent event) {
        TrayPopupMenu.getInstance().setVisible(false);
        TrayPopupMenu.getInstance().setState(MenuClosedState.getInstance());
    }
    
    @Override
    public void init() {
        if (log.isDebugEnabled()) {
            log.info("Menu open. Free mem: " 
                    + Runtime.getRuntime().freeMemory() / (1024*1024));
        }
    }

}
