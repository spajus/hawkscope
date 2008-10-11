package com.varaneckas.hawkscope.menu.state;

import java.awt.event.MouseEvent;

import com.varaneckas.hawkscope.gui.swing.SwingMainMenu;

/**
 * State of {@link SwingMainMenu} 
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
