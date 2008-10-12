package com.varaneckas.hawkscope.listeners;

import java.awt.event.ActionListener;

import com.varaneckas.hawkscope.menu.Command;

/**
 * Exit {@link ActionListener}
 * 
 * Quits the application on left click
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class ExitCommand implements Command {

    @Override
    public void execute() {
        System.exit(0);
    }

}
