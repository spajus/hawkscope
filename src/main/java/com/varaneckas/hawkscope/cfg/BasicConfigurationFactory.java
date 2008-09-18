package com.varaneckas.hawkscope.cfg;

import java.io.File;

public class BasicConfigurationFactory extends ConfigurationFactory {


    protected String loadConfigFilePath() {
        return new File(".").getAbsolutePath();
    }

}
