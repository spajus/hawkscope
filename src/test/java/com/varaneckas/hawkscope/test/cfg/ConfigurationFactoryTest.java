package com.varaneckas.hawkscope.test.cfg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class ConfigurationFactoryTest {

    private static final Log log = LogFactory
            .getLog(ConfigurationFactoryTest.class);
    
    @Test
    public void testConfigurationFactory() throws Exception {
        log.info(System.getenv().toString().replaceAll(", ", "\n"));
        log.info(System.getProperties().toString().replaceAll(", ", "\n"));
    }
    
}
