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
        data.put(Configuration.HIDDEN_FILES_DISPLAYED, "0");
        return data;
    }

}
