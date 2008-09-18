package com.varaneckas.hawkscope.cfg;

import java.util.HashMap;
import java.util.Map;


public class Configuration {

    public static Map<String, String> getDefaults() {
        return new HashMap<String, String>();
    }

    private final Map<String, String> properties;
    
    private boolean changed = false;
    
    public boolean isChanged() {
        return changed;
    }

    public Configuration(final Map<String, String> properties) {
        this.properties = properties;
    }
    
    public Map<String, String> getMap() {
        return properties;
    }
    
    
}
