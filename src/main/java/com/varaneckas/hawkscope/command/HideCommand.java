package com.varaneckas.hawkscope.command;

import com.varaneckas.hawkscope.gui.swt.SWTMenuFactory;
import com.varaneckas.hawkscope.menu.Command;

/**
 * Hide Menu Item {@link Command}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class HideCommand implements Command {

    public void execute() {
        SWTMenuFactory.getMainMenu().forceHide();
    }

}
