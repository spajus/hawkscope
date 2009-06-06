package com.varaneckas.hawkscope.plugin;

public class DisabledPlugin extends PluginAdapter {

    private final String className;
    
    public DisabledPlugin(String className) {
        this.className = className;
        this.enabled = false;
    }
    
    public String getDescription() {
        return "Disabled Plugin.";
    }

    public String getName() {
        return className.replaceAll(".*\\.", "");
    }

    public String getVersion() {
        return "0";
    }
    
    public String getClassName() {
        return className;
    }
    
}
