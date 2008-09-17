package com.varaneckas.hawkscope.cfg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ResourceBundle;

public class BasicConfigurationFactory extends ConfigurationFactory {


    protected String loadConfigFilePath() {
        return new File("./" + CONFIG_FILE_NAME).getAbsolutePath();
    }


    @Override
    public Configuration getConfiguration() {
        return new Configuration(ResourceBundle.getBundle(configFilePath));
    }

    @Override
    public void write(Configuration configuration) {
        try {
            Writer config = new BufferedWriter(new FileWriter(configFilePath));
            for (String key : configuration.getResourceBundle().keySet()) {
                config.write(key);
                config.write(" = ");
                config.write(configuration.getResourceBundle().getString(key));
                config.write('\n');
            }
            config.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
