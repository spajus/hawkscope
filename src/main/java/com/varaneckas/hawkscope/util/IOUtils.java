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
package com.varaneckas.hawkscope.util;

import java.io.Closeable;
import java.io.IOException;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

/**
 * Input/Output Utilities
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class IOUtils {

    /**
     * Closes a closeable without the annoying {@link IOException} or a 
     * null check
     * 
     * @param closeable
     */
    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException e) { 
            //Be vewy vewy quiet. I'm hunting wabbits 
        }
    }
    
    /**
     * Copies String to System Clipboard
     * 
     * @param target text to copy
     */
    public static void copyToClipboard(final String target) {
    	Clipboard clip = new Clipboard(Display.getDefault());
    	clip.setContents(new Object[] { target }, new Transfer[] { TextTransfer.getInstance() });
    }
}
