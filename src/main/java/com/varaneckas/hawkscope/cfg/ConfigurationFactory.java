package com.varaneckas.hawkscope.cfg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class ConfigurationFactory {

    private static final Log log = LogFactory
            .getLog(ConfigurationFactory.class);
    
    protected static final String CONFIG_FILE_NAME = "hawkscope";

    private static ConfigurationFactory concreteInstance = null;

    public static ConfigurationFactory getConfigurationFactory() {
        synchronized (ConfigurationFactory.class) {
            if (concreteInstance == null) {
                if (new File(System.getProperty("user.home")).canWrite()) {
                    concreteInstance = new UserHomeConfigurationFactory();
                } else {
                    concreteInstance = new BasicConfigurationFactory();
                }
            }
        }
        return concreteInstance;
    }

    public Configuration getConfiguration() {
        Map<String, String> cfg = new HashMap<String, String>();
        try {
            
            ResourceBundle data = ResourceBundle.getBundle(CONFIG_FILE_NAME, Locale.ENGLISH, new ClassLoader() {
                @Override
                protected URL findResource(String name) {
                    try {
                        return new URL("file://" 
                                + loadConfigFilePath() + "/." + name);
                    } catch (MalformedURLException e) {
                        log.error("Failed loading file", e);
                        return null;
                    }
                }
            });
            for (String key : data.keySet()) {
                cfg.put(key, data.getString(key));
            }
        } catch (MissingResourceException e) {
            log.debug("Configuration not found, using defaults. (" + e.getMessage() + ")");
            cfg = getDefaults();
        }
        return new Configuration(cfg);
    }

    public void write(Configuration configuration) {
        try {
            Writer config = new BufferedWriter(new FileWriter(
                    loadConfigFilePath() + "/." + CONFIG_FILE_NAME));
            for (String key : configuration.getMap().keySet()) {
                config.write(key);
                config.write(" = ");
                config.write(configuration.getMap().get(key));
                config.write('\n');
            }
            config.close();
        } catch (IOException e) {
            log.error("Failed writing config file", e);
        }
    }

    abstract protected String loadConfigFilePath();

    abstract protected Map<String, String> getDefaults();
    
}
