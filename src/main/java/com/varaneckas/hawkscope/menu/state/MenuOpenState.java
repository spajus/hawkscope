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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * State when {@link MainMenu} is open (visible)
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
    public void act(final StateEvent event) {
        MenuFactory.getMainMenu().forceHide();
    }
    
    @Override
    public void init() {
        if (log.isDebugEnabled()) {
            log.debug("Menu open. Free mem: " 
                    + Runtime.getRuntime().freeMemory() / (1024*1024));
        }
    }

}
