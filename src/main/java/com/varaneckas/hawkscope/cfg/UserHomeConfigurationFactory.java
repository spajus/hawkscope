package com.varaneckas.hawkscope.cfg;


public class UserHomeConfigurationFactory extends ConfigurationFactory {

    @Override
    protected String loadConfigFilePath() {
        return System.getProperty("user.home"); 
           
    }

}
