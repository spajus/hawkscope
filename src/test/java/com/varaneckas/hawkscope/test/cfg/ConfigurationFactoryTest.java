package com.varaneckas.hawkscope.test.cfg;

import java.io.File;
import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;

public class ConfigurationFactoryTest {
    
    private static final Log log = LogFactory
            .getLog(ConfigurationFactoryTest.class);
    
    public void testFindCfg() throws Exception {
		File cfg = new File(URI.create("C:/Documents and Settings/tomasv/.hawkscope.properties".replaceAll(" ", "%20")));
		log.info(cfg.canRead());
	}
    
    @Test
    public void testConfigurationFactory() throws Exception {
        log.info(System.getenv().toString().replaceAll(", ", "\n"));
//        log.info(System.getProperties().toString().replaceAll(", ", "\n"));
        Configuration cfg = ConfigurationFactory.getConfigurationFactory().getConfiguration();
        cfg.getProperties().put("test", "works");
        ConfigurationFactory.getConfigurationFactory().write(cfg);
    }
    
}
