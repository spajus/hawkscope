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
import java.io.FileFilter;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;

/**
 * {@link FileFilter} that is responsible for building the menu according to
 * {@link Configuration} parameters
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class DynamicFileFilter implements FileFilter {

    /**
     * Singleton Instance
     */
    private static final DynamicFileFilter instance = new DynamicFileFilter();

    /**
     * Configuration
     */
    private final Configuration cfg;
    
    /**
     * Singleton constructor
     */
    private DynamicFileFilter() {
         cfg = ConfigurationFactory.getConfigurationFactory()
             .getConfiguration();
    }

    /**
     * Singleton instance getter
     * 
     * @return instance
     */
    public static DynamicFileFilter getInstance() {
        return instance;
    }
    
    /**
     * Accepts file according to Hawkscope settings
     * 
     * @see FileFilter#accept(File)
     */
    public boolean accept(final File file) {
        if (!cfg.isHiddenFilesDisplayed()) {
            if (file.isHidden()) {
                return false;
            }
        }
        if (file.isDirectory() && cfg.getBlackList().contains(file)) {
            return false;
        }
        return true;
    }
}
