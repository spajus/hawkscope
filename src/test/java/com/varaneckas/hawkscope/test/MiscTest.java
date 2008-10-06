package com.varaneckas.hawkscope.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class MiscTest {

    private static final Log log = LogFactory.getLog(MiscTest.class);

    @Test
    public void testAnything() throws Exception {
        log.info(System.getenv().toString().replaceAll(", ", "\n"));
        log.info(System.getProperties().toString().replaceAll(", ", "\n"));
    }

}
