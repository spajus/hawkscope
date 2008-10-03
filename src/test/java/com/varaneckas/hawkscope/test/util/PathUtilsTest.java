package com.varaneckas.hawkscope.test.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.varaneckas.hawkscope.util.PathUtils;

public class PathUtilsTest {

    private static final Log log = LogFactory.getLog(PathUtilsTest.class);

    @Test
    public void testInterpreter() {
        List<File> files = PathUtils.pathToDirList("${user.home}/Desktop;${$JAVA_HOME}/bin", ";");
        assertEquals("Found two entries", 2, files.size());
        log.info(files);
    }
    
    
}
