package com.varaneckas.hawkscope.util;

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
}
