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
package com.varaneckas.hawkscope.menu;

import java.io.File;

/**
 * SWT {@link MainMenu} factory
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class MenuFactory {
	
    /**
     * Gets the SWT imlementation of {@link MainMenu} 
     */
    public static MainMenu getMainMenu() {
        return MainMenu.getInstance();
    }

    public static ExecutableMenuItem newExecutableMenuItem() {
        return new ExecutableMenuItem();
    }

    public static FileMenuItem newFileMenuItem(final File file) {
        return new FileMenuItem(file);
    }

    public static FolderMenu newFolderMenu(final File file) {
        return new FolderMenu(file);
    }
    
}
