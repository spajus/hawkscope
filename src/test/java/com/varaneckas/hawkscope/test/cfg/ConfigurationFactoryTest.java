package com.varaneckas.hawkscope.test.cfg;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.junit.Test;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;

public class ConfigurationFactoryTest {
    
    @Test
    public void testConfigurationFactory() throws Exception {
//        log.info(System.getenv().toString().replaceAll(", ", "\n"));
//        log.info(System.getProperties().toString().replaceAll(", ", "\n"));
        Configuration cfg = ConfigurationFactory.getConfigurationFactory().getConfiguration();
        cfg.getProperties().put("test", "works");
        ConfigurationFactory.getConfigurationFactory().write(cfg);
        
        final StringBuilder props = new StringBuilder();
        for (final Object p : System.getProperties().keySet()) {
            props.append(p).append(": ").append(System.getProperty("" + p));
            props.append('\n');
        }
        System.out.println(props);
        StringSelection ss = new StringSelection("testas");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        
    }
    
}
