package com.varaneckas.hawkscope.command;

import com.varaneckas.hawkscope.gui.WindowFactory;
import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * About {@link Command}
 * 
 * Displays About dialog
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class AboutCommand implements Command {

    public void execute() {
        WindowFactory.getAboutWindow().showObject();
        MenuFactory.getMainMenu().forceHide();
    }

}
