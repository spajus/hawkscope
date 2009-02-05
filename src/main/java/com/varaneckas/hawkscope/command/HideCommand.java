package com.varaneckas.hawkscope.command;

import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * Hide Menu Item {@link Command}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class HideCommand implements Command {

    public void execute() {
        MenuFactory.getMainMenu().forceHide();
    }

}
