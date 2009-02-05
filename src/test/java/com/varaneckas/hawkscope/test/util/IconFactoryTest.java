package com.varaneckas.hawkscope.test.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.varaneckas.hawkscope.util.SWTIconFactory;

public class IconFactoryTest {

    @Test
    public void testIconFactory() {
        Object drive = SWTIconFactory.getInstance().getIcon("drive");
        assertNotNull(drive);
    }
    
}
