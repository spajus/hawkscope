package com.varaneckas.hawkscope.cfg;

import java.io.File;
import java.util.Map;

public class BasicConfigurationFactory extends ConfigurationFactory {


    protected String loadConfigFilePath() {
        return new File(".").getAbsolutePath();
    }

    @Override
    protected Map<String, String> getDefaults() {
        
        return null;
    }

}
