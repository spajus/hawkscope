package com.varaneckas.hawkscope.gui.listeners;

import org.eclipse.swt.program.Program;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.menu.Command;

/**
 * Update Hawkscope Menu Item {@link Command}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class UpdateCommand implements Command {

    public void execute() {
        Program.launch(Version.DOWNLOAD_URL);
    }

}
