package com.varaneckas.hawkscope.menu.state;

import java.awt.event.MouseEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.gui.swing.SwingMainMenu;
import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * State when {@link SwingMainMenu} is open (visible)
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
        MenuFactory.getMainMenu().forceHide();
    }
    
    @Override
    public void init() {
        if (log.isDebugEnabled()) {
            log.info("Menu open. Free mem: " 
                    + Runtime.getRuntime().freeMemory() / (1024*1024));
        }
    }

}
