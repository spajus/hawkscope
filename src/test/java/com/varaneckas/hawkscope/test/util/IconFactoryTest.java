package com.varaneckas.hawkscope.test.util;

import static org.junit.Assert.assertNotNull;

import javax.swing.Icon;

import org.junit.Test;

import com.varaneckas.hawkscope.util.IconFactory;

public class IconFactoryTest {

    @Test
    public void testIconFactory() {
        Icon drive = (Icon) IconFactory.getIconFactory().getIcon("drive");
        assertNotNull(drive);
    }
    
}
