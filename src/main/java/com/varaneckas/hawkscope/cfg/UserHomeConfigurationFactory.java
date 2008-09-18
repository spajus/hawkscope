package com.varaneckas.hawkscope.cfg;

public class UserHomeConfigurationFactory extends BasicConfigurationFactory {

    @Override
    protected String loadConfigFilePath() {
        return System.getProperty("user.home"); 
           
    }

}
