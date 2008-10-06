package com.varaneckas.hawkscope.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileSystemView;

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
     * Property marker regex
     */
    public static final String INTERPRET_REGEX = "\\$\\{([^\\{]+)\\}";
    
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
        final String[] locations = interpret(path).split(delimiter);
        final List<File> files = new ArrayList<File>();
        for (final String location : locations) {
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
    
    
    private static String interpret(final String location) {
        if (!location.matches(".*" + INTERPRET_REGEX + ".*")) {
            return location;
        } else {
            String newLocation = location;
            final Pattern grep = Pattern.compile(INTERPRET_REGEX);
            final Matcher matcher = grep.matcher(location);
            while (matcher.find()) {
                log.debug("Parsing: " + matcher.group(1));
                String replacement;
                if (matcher.group(1).startsWith("$")) {
                    replacement = "" + System.getenv(matcher.group(1).substring(1));
                } else {
                    replacement = "" + System.getProperty(matcher.group(1));
                }
                newLocation = newLocation.replaceFirst(Pattern.quote(
                        matcher.group()), replacement.replaceAll("\\\\", "/"));
            }
            return newLocation;
        }
    }
    
    /**
     * Gets friendly file name
     * 
     * @param file input file
     * @return friendly file name
     */
    public static String getFileName(final File file) {
        final FileSystemView fsw = FileSystemView.getFileSystemView();
        String name = fsw.getSystemDisplayName(file);
        if (name == null || name.equals("")) {
            name = file.getName();
        } 
        if (name == null || name.equals("")) {
            name = file.getPath();
        } 
        if (name == null || name.equals("")) {
            name = "Untitled";
        }
        return name;
    }
    
    /**
     * Convenience method for finding out whether file is a floppy drive
     * 
     * @param file suspect
     * @return is floppy
     */
    public static boolean isFloppy(final File file) {
        return FileSystemView.getFileSystemView().isFloppyDrive(file);
    }
}
