package com.varaneckas.hawkscope.menu;

import java.awt.event.MouseEvent;

/**
 * State of {@link MainPopupMenu} 
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class State {

    /**
     * Action to MouseEvent
     * 
     * @param event mouse event
     */
    public abstract void act(final MouseEvent event);
    
    /**
     * State initialization
     */
    public abstract void init();

}
