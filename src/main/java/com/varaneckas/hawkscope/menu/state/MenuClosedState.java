package com.varaneckas.hawkscope.menu.state;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.gui.swt.SWTMainMenu;
import com.varaneckas.hawkscope.gui.swt.SWTMenuFactory;

/**
 * State when {@link MainMenu} is closed (invisible)
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
        // nothing to do
    }

    @Override
    public void act(final StateEvent event) {
        final SWTMainMenu menu = SWTMenuFactory.getMainMenu();
        menu.showMenu(event.getX(), event.getY());
        menu.setState(MenuOpenState.getInstance());
    }

    @Override
    public void init() {
        if (log.isDebugEnabled()) {
            log.debug("Menu closed. Free mem before cleanup: "
                    + Runtime.getRuntime().freeMemory() / (1024 * 1024));
        }
        SWTMenuFactory.getMainMenu().reloadMenu(true);
    }
    
}
