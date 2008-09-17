package com.varaneckas.hawkscope.cfg;

import java.io.File;

public abstract class ConfigurationFactory {

    protected static final String CONFIG_FILE_NAME = ".hawkscope.properties";

    protected String configFilePath;

    public static ConfigurationFactory getConfigurationFactory() {
        final String os = System.getProperty("os.name", "unknown").toLowerCase();
        final ConfigurationFactory factory;
        if (os.matches(".*u(..)?x.*|.*bsd.*|.*mac.*|.*solaris.*")) {
            factory = new PosixConfigurationFactory();
        } else if (os.matches(".*win.*")) {
            factory = new WindowsConfigurationFactory();
        } else {
            factory = new BasicConfigurationFactory();
        }
        factory.init();
        return factory;
    }

    public abstract Configuration getConfiguration();

    public abstract void write(Configuration configuration);


    protected final void init() {
        configFilePath = loadConfigFilePath();
    }

    abstract protected String loadConfigFilePath();

}
