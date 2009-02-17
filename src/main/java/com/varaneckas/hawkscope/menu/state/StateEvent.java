/*
 * Copyright (c) 2008-2009 Tomas Varaneckas
 * http://www.varaneckas.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
