package com.varaneckas.hawkscope.test.cfg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;

public class ConfigurationFactoryTest {

    private static final Log log = LogFactory
            .getLog(ConfigurationFactoryTest.class);
    
    @Test
    public void testConfigurationFactory() throws Exception {
//        log.info(System.getenv().toString().replaceAll(", ", "\n"));
//        log.info(System.getProperties().toString().replaceAll(", ", "\n"));
        Configuration cfg = ConfigurationFactory.getConfigurationFactory().getConfiguration();
        cfg.getProperties().put("test", "works");
        ConfigurationFactory.getConfigurationFactory().write(cfg);
    }
    
}
