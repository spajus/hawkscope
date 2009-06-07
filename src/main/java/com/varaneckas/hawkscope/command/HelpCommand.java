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
public class HelpCommand implements Command {

    /**
     * Opens the {@link AboutWindow}
     */
    public void execute() {
        WindowFactory.getHelpWindow().open();
    }

}
