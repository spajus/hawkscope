package com.varaneckas.hawkscope.cfg;

import java.util.ResourceBundle;


public class Configuration {

    private final ResourceBundle properties;
    
    private boolean changed = false;
    
    public boolean isChanged() {
        return changed;
    }

    public Configuration(final ResourceBundle properties) {
        this.properties = properties;
    }
    
    public ResourceBundle getResourceBundle() {
        return properties;
    }
    
    
}
