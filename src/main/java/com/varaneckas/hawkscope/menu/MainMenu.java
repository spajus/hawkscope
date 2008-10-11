package com.varaneckas.hawkscope.menu;

import com.varaneckas.hawkscope.menu.state.MenuClosedState;
import com.varaneckas.hawkscope.menu.state.State;

public interface MainMenu {

    /**
     * Displays the menu
     * 
     * @param x coordinate x
     * @param y coordinate y
     */
    public abstract void showMenu(int x, int y);
    
    /**
     * Forces menu to go to {@link MenuClosedState}
     */
    public abstract void forceHide();

    /**
     * Gets current {@link State}
     * 
     * @return current state
     */
    public abstract State getState();

    /**
     * Sets menu {@link State}
     * 
     * @param state new state
     */
    public abstract void setState(final State state);

    /**
     * Loads menu contents
     */
    public abstract void loadMenu();
    
    /**
     * Clears menu contents
     */
    public abstract void clearMenu();

}