package com.varaneckas.hawkscope.gui.listeners;

import java.awt.event.ActionListener;

import com.varaneckas.hawkscope.gui.WindowFactory;
import com.varaneckas.hawkscope.menu.Command;
import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * About {@link ActionListener}
 * 
 * Displays About dialog
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class AboutCommand implements Command {

    public void execute() {
        WindowFactory.getAboutWindow().showObject();
        MenuFactory.getMenuFactory().getMainMenu().forceHide();
    }

}
