package com.varaneckas.hawkscope.menu;

import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * State when {@link MainPopupMenu} is closed (invisible) 
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
        final MainPopupMenu menu = MainPopupMenu.getInstance();
        menu.setLocation(event.getX(), event.getY());
        menu.setInvoker(menu);
        menu.setVisible(true);
        menu.getRootPane().requestFocus(true);
        SwingUtilities.windowForComponent(menu).setAlwaysOnTop(true);
        menu.setState(MenuOpenState.getInstance());
    }

    @Override
    public void init() {
        if (log.isDebugEnabled()) {
            log.debug("Menu closed. Free mem before cleanup: " 
                    + Runtime.getRuntime().freeMemory() / (1024*1024));
        }
        MainPopupMenu.getInstance().removeAll();
        MainPopupMenu.getInstance().loadMenu();
        Runtime.getRuntime().gc();
    }
    
}
