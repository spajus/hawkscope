package com.varaneckas.hawkscope.command;

import com.varaneckas.hawkscope.gui.WindowFactory;
import com.varaneckas.hawkscope.gui.swt.SWTMenuFactory;
import com.varaneckas.hawkscope.menu.Command;

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
        SWTMenuFactory.getMainMenu().forceHide();
    }

}
