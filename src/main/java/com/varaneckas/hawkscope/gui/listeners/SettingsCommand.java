package com.varaneckas.hawkscope.gui.listeners;

import java.awt.event.ActionListener;

import com.varaneckas.hawkscope.gui.WindowFactory;
import com.varaneckas.hawkscope.menu.Command;
import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * About {@link ActionListener}
 * 
 * Displays Settings dialog
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SettingsCommand implements Command {

    @Override
    public void execute() {
        WindowFactory.getSettingsWindow().showObject();
        MenuFactory.getMenuFactory().getMainMenu().forceHide();
    }

}
