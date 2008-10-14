package com.varaneckas.hawkscope.test.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.varaneckas.hawkscope.util.IconFactory;

public class IconFactoryTest {

    @Test
    public void testIconFactory() {
        Object drive = IconFactory.getIconFactory().getIcon("drive");
        assertNotNull(drive);
    }
    
}
