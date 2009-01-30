package com.varaneckas.hawkscope.gui.listeners;

import com.varaneckas.hawkscope.menu.Command;
import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * Hide Menu Item {@link Command}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class HideCommand implements Command {

    public void execute() {
        MenuFactory.getMenuFactory().getMainMenu().forceHide();
    }

}
