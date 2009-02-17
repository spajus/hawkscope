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

import com.varaneckas.hawkscope.menu.MainMenu;
import com.varaneckas.hawkscope.menu.MenuFactory;

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
        final MainMenu menu = MenuFactory.getMainMenu();
        menu.showMenu(event.getX(), event.getY());
        menu.setState(MenuOpenState.getInstance());
    }

    @Override
    public void init() {
        if (log.isDebugEnabled()) {
            log.debug("Menu closed. Free mem before cleanup: "
                    + Runtime.getRuntime().freeMemory() / (1024 * 1024));
        }
        MenuFactory.getMainMenu().reloadMenu(true);
    }
    
}
