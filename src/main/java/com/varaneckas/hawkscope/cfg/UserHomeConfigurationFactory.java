package com.varaneckas.hawkscope.cfg;

/**
 * User Home Configuration Factory
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class UserHomeConfigurationFactory extends BasicConfigurationFactory {

    @Override
    protected String loadConfigFilePath() {
        return System.getProperty("user.home"); 
           
    }

}
