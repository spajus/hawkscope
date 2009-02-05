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
