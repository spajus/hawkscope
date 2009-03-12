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
    
    //not used
    public void testFindCfg() throws Exception {
		File cfg = new File(URI.create("C:/Documents and Settings/tomasv/.hawkscope.properties".replaceAll(" ", "%20")));
		log.info(cfg.canRead());
	}
    
    @Test
    public void testConfigurationFactory() throws Exception {
        Configuration cfg = ConfigurationFactory.getConfigurationFactory().getConfiguration();
        cfg.getProperties().put("test", "works");
        ConfigurationFactory.getConfigurationFactory().write(cfg);
    }
    
    @Test
	public void testPasswords() throws Exception {
    	Configuration cfg = ConfigurationFactory.getConfigurationFactory().getConfiguration();
    	String pass = cfg.getPasswordProperty("plugins.twitter.pass");
    	log.debug("pass: " + pass);
	}
    
}
