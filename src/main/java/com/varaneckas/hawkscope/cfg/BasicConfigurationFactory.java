package com.varaneckas.hawkscope.cfg;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic Configuration Factory
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class BasicConfigurationFactory extends ConfigurationFactory {

    @Override
    protected String loadConfigFilePath() {
        return new File(".").getAbsolutePath();
    }

    @Override
    protected Map<String, String> getDefaults() {
        final Map<String, String> data = new HashMap<String, String>();
        //hidden files are not displayed
        data.put(Configuration.HIDDEN_FILES_DISPLAYED, "0");
        //quick access list contains one entry - user home, read from system properties
        data.put(Configuration.QUICK_ACCESS_LIST, "${user.home}");
        return data;
    }

}
