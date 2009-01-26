package com.varaneckas.hawkscope.test;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.varaneckas.hawkscope.util.PathUtils;

public class WindowsTest {

    private static final Log log = LogFactory.getLog(WindowsTest.class);
    
    @Test
    public void testFileSystem() throws Exception {
        if (!System.getProperty("os.name").startsWith("Win")) {
            return;
        }
        File c = new File("C:/");
        log.debug(c.getAbsolutePath());
        List<File> list = PathUtils.pathToDirList(
                "C:/;C:\\\\;C:\\\\Program\\ Files;", ";");
        log.debug(list);
    }
    
}
