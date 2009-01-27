package com.varaneckas.hawkscope.gui.listeners;

import java.awt.event.ActionListener;

import org.eclipse.swt.widgets.Display;

import com.varaneckas.hawkscope.menu.Command;
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * Exit {@link ActionListener}
 * 
 * Quits the application on left click
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class ExitCommand implements Command {

    public void execute() {
        IconFactory.getIconFactory().cleanup();
        final Display d = Display.getDefault();
        if (!d.isDisposed()) {
        	d.dispose();
        }
        System.exit(0);
    }

}
