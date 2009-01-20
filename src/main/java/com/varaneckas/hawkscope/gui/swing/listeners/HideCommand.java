package com.varaneckas.hawkscope.gui.swing.listeners;

import java.awt.event.ActionListener;

import com.varaneckas.hawkscope.menu.Command;
import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * Hide Menu Item {@link ActionListener}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class HideCommand implements Command {

    @Override
    public void execute() {
        MenuFactory.getMenuFactory().getMainMenu().forceHide();
    }

}
