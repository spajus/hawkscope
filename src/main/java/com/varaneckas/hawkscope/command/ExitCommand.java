/*
 * Copyright (c) 2008-2009 Tomas Varaneckas
 * http://www.varaneckas.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
