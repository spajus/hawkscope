package com.varaneckas.hawkscope.cfg;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BasicConfigurationFactory extends ConfigurationFactory {


    protected String loadConfigFilePath() {
        return new File(".").getAbsolutePath();
    }

    @Override
    protected Map<String, String> getDefaults() {
        Map<String, String> data = new HashMap<String, String>();
        data.put(Configuration.HIDDEN_FILES_DISPLAYED, "0");
        return data;
    }

}
