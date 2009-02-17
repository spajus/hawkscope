package com.varaneckas.hawkscope.command;

import org.eclipse.swt.program.Program;

import com.varaneckas.hawkscope.Version;

/**
 * Update Hawkscope Menu Item {@link Command}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class UpdateCommand implements Command {

    /**
     * Update is available!
     */
    public void execute() {
        Program.launch(Version.DOWNLOAD_URL);
    }

}
