package com.varaneckas.hawkscope.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Path processing utilities
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class PathUtils {

    /**
     * Property marker tag name
     */
    public static final String PROPERTY_TAG = "property#";
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(PathUtils.class);
    
    /**
     * Converts path separated with delimiter
     * @param path
     * @param delimiter
     * @return
     */
    public static List<File> pathToDirList(final String path, final String delimiter) {
        if (path == null || path.equals("")) {
            return null;
        }
        final String[] locations = path.split(delimiter);
        final List<File> files = new ArrayList<File>();
        for (String location : locations) {
            if (location.startsWith(PROPERTY_TAG)) {
                location = System.getProperty(location.replaceFirst(PROPERTY_TAG, ""));
            } 
            final File f = new File(location);
            if (!f.isDirectory()) {
                log.warn(f.getAbsolutePath() + " is not a directory!");
            } else if (!f.canRead()) {
                log.warn(f.getAbsolutePath() + " can not be read!");
            } else {
                files.add(f);
            }
        }
        return files;
    }
    
}
