package com.varaneckas.hawkscope.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Display;

import com.varaneckas.hawkscope.gui.SharedStyle;
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * Exit {@link Command}
 * 
 * Quits the application on left click
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class ExitCommand implements Command {
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(ExitCommand.class);

    /**
     * Releases the resources and exits Hawkscope
     */
    public void execute() {
        try {
            IconFactory.getInstance().cleanup();
            SharedStyle.releaseResources();
            final Display d = Display.getDefault();
            if (!d.isDisposed()) {
            	d.dispose();
            }
        } catch (final Exception e) {
            log.warn("Error while cleaning up resources before exit", e);
        }
        System.exit(0);
    }

}
