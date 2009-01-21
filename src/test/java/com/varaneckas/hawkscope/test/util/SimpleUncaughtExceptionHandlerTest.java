package com.varaneckas.hawkscope.test.util;

import java.security.MessageDigest;

import org.junit.Before;
import org.junit.Test;

import com.varaneckas.hawkscope.gui.WindowFactory;

public class SimpleUncaughtExceptionHandlerTest {
    
    @Before
    public void startup() {
        WindowFactory.initialize();
        Thread.setDefaultUncaughtExceptionHandler(WindowFactory
                .getUncaughtExceptionHandler());
    }
    
    @Test
    public void testShortException() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("This is how short error looks.");
            }
        }).start();
        Thread.sleep(100l);
    }
    
    @Test
    public void testLongException() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("This is how loooong error looks: " +
                		"A quick brown fox jumps over a lazy dog! " +
                		"A quick brown fox jumps over a lazy dog! " +
                        "A quick brown fox jumps over a lazy dog! " +
                        "A quick brown fox jumps over a lazy dog! " +
                        "A quick brown fox jumps over a lazy dog! " +
                        "A quick brown fox jumps over a lazy dog! " +
                		"A quick brown fox jumps over a lazy dog! " +
                		"A quick brown fox jumps over a lazy dog! " +
                		"A quick brown fox jumps over a lazy dog!");
            }
        }).start();
        Thread.sleep(100l);
    }
    
    @Test
    public void testExceptionWithLongStackTrace() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageDigest.getInstance("456").digest("test".getBytes(),
                            0, 1555);
                } catch (Exception e) {
                    RuntimeException x = new RuntimeException("Starting nest", e);
                    for (int i = 0; i < 10; i++) {
                        x = new RuntimeException("Nesting: " + i, x);
                    }
                    throw x;
                }
            }
        }).start();
        Thread.sleep(100l);
    }    
    
}
