package com.varaneckas.hawkscope.test;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.program.Program;
import org.junit.Test;

import com.varaneckas.hawkscope.util.PathUtils;

public class MacTest {
	
	private static final Log log = LogFactory.getLog(MacTest.class);
	
	@Test
	public void testMac() throws Exception {
		if (!System.getProperty("os.name").startsWith("Mac")) {
			return;
		}
		File[] roots = File.listRoots();
		for (File root : roots) {
			log.debug(root.getAbsolutePath());
		}
		for (File entry : PathUtils.pathToDirList("${user.home}", ";")) {
			log.debug("Entry : " + entry.getAbsolutePath());
		}
		for (String ext : Program.getExtensions()) {
			log.debug(ext);
		}
		for (Program p : Program.getPrograms()) {
			log.debug(p.getName());
		}
		log.debug(Program.findProgram(".mp3").getName());
	}

}
