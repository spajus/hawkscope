package com.varaneckas.hawkscope.test.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.varaneckas.hawkscope.util.PathUtils;

public class PathUtilsTest {

    private static final Log log = LogFactory.getLog(PathUtilsTest.class);

    public void testReplace() throws Exception {
    	System.out.println("${x.y}".replaceAll(Pattern.quote("${x.y}")
    			, "C:\\\\stuff\\\\bla"));
	}
    
    @Test
    public void testInterpreter() {
        List<File> files = PathUtils.pathToDirList("${user.home};${user.dir}", ";");
        assertEquals("Found two entries", 2, files.size());
        log.info(files);
    }
    
    
}
