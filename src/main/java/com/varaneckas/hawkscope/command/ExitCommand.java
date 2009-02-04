package com.varaneckas.hawkscope.command;

import org.eclipse.swt.widgets.Display;

import com.varaneckas.hawkscope.gui.swt.SWTIconFactory;
import com.varaneckas.hawkscope.menu.Command;

/**
 * Exit {@link Command}
 * 
 * Quits the application on left click
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class ExitCommand implements Command {

    public void execute() {
        SWTIconFactory.getInstance().cleanup();
        final Display d = Display.getDefault();
        if (!d.isDisposed()) {
        	d.dispose();
        }
        System.exit(0);
    }

}
