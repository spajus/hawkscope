package com.varaneckas.hawkscope.cfg;

import java.util.Map;


public class UserHomeConfigurationFactory extends ConfigurationFactory {

    @Override
    protected String loadConfigFilePath() {
        return System.getProperty("user.home"); 
           
    }

    @Override
    protected Map<String, String> getDefaults() {
        // TODO Auto-generated method stub
        return null;
    }

}
