package com.varaneckas.hawkscope.util;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.Closeable;
import java.io.IOException;

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
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(target), null);
    }
}
