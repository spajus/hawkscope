package com.varaneckas.hawkscope.menu;

import java.io.File;
import java.io.FileFilter;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;

public class DynamicFileFilter implements FileFilter {

    private static final DynamicFileFilter instance = new DynamicFileFilter();

    private Configuration cfg;
    
    private DynamicFileFilter() {
         cfg = ConfigurationFactory.getConfigurationFactory()
             .getConfiguration();
    }

    public static DynamicFileFilter getInstance() {
        return instance;
    }
    
    @Override
    public boolean accept(File file) {
        if (!cfg.isHiddenFilesDisplayed()) {
            if (file.isHidden()) {
                return false;
            }
        }
        return true;
    }

}
