package com.varaneckas.hawkscope.command;

import com.varaneckas.hawkscope.gui.AboutWindow;
import com.varaneckas.hawkscope.gui.WindowFactory;

/**
 * About {@link Command}
 * 
 * Displays About dialog
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class AboutCommand implements Command {

    /**
     * Opens the {@link AboutWindow}
     */
    public void execute() {
        WindowFactory.getAboutWindow().open();
    }

}
