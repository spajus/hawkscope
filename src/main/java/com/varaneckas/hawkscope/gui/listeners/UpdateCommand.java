package com.varaneckas.hawkscope.gui.listeners;

import java.awt.event.ActionListener;

import org.eclipse.swt.program.Program;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.menu.Command;

/**
 * Update Hawkscope Menu Item {@link ActionListener}
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class UpdateCommand implements Command {

    @Override
    public void execute() {
        Program.launch(Version.DOWNLOAD_URL);
    }

}
