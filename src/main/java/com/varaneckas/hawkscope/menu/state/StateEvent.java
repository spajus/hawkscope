package com.varaneckas.hawkscope.menu.state;

/**
 * Menu {@link State} Event 
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class StateEvent {
    
    /**
     * X Coordinate of the event
     */
    private int x;
    
    /**
     * Y Coordinate of the eent
     */
    private int y;
    
    /**
     * Gets X coordinate
     * 
     * @return
     */
    public int getX() {
        return x;
    }
    
    /**
     * Sets X coordinate
     * 
     * @param x
     */
    public void setX(final int x) {
        this.x = x;
    }
    
    /**
     * Gets Y coordinate
     * 
     * @return
     */
    public int getY() {
        return y;
    }
    
    /**
     * Sets Y coordinate
     * 
     * @param y
     */
    public void setY(final int y) {
        this.y = y;
    }
    
    

}
