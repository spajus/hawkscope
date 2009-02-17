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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static List<File> pathToDirList(final String path, 
            final String delimiter) {
        if (path == null || path.equals("")) {
            return Collections.emptyList();
        }
        final String[] locations = interpret(path).split(delimiter);
        final List<File> files = new ArrayList<File>();
        for (final String location : locations) {
            File f = new File(unsanitizePath(location));
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
    
    /**
     * Interprets the location
     * 
     * It can either be a full path, a java property, 
     * like ${user.home} (default) or environmental variable like ${$JAVA_HOME}.
     * 
     * @param location
     * @return
     */
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
                    replacement = "" + System.getenv(matcher.group(1)
                            .substring(1));
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
    	String name = OSUtils.getSystemDisplayName(file);
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
    	return OSUtils.isFloppyDrive(file);
    }
    
    /**
     * Removes the sanitizing from path string
     * 
     * @param path
     * @return
     */
    public static String unsanitizePath(final String path) {
        if (OSUtils.CURRENT_OS.equals(OSUtils.OS.WIN)) {
            return path.replaceAll("/", "\\\\");
        }
        return path;
    }
    
    /**
     * Sanitizes the path string (cleanup Windows backslashes)
     * 
     * @param path
     * @return
     */
    public static String sanitizePath(final String path) {
        if (OSUtils.CURRENT_OS.equals(OSUtils.OS.WIN)) {
            return path.replaceAll("\\\\", "/");
        }
        return path;
    }
}
