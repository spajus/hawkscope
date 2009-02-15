package com.varaneckas.hawkscope.command;

import com.varaneckas.hawkscope.gui.settings.SettingsWindow;
import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * Settings {@link Command}
 * 
 * Displays Settings dialog
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SettingsCommand implements Command {

    public void execute() {
//        WindowFactory.getSettingsWindow().showObject();
    	new SettingsWindow().open();
        MenuFactory.getMainMenu().forceHide();
    }

}
