package com.varaneckas.hawkscope;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Map.Entry;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;

/**
 * Hawkscope Version information
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class Version {
    
    /**
     * Report separator
     */
    private static final String SEPARATOR = 
            "--------------------------------------\n";
    
    /**
     * Application name
     */
    public static final String APP_NAME = "Hawkscope";
    
    /**
     * Application slogan
     */
    public static final String APP_SLOGAN = "Access anything with single click!";
    
    /**
     * Application version number   
     */
    public static final String VERSION_NUMBER = "0.3.0";
    
    /**
     * Application version date
     */
    public static final String VERSION_DATE = "2009-01-23";
    
    /**
     * Application Homepage URL
     */
    public static final String HOMEPAGE = "http://hawkscope.googlecode.com";
    
    /**
     * Gets a nice application version string
     * 
     * @return long version string
     */
    public static String formatFullString() {
        return APP_NAME.concat(" ").concat(VERSION_NUMBER)
                .concat(" (").concat(VERSION_DATE)
                .concat(") [").concat(HOMEPAGE).concat("]");
    }
    
    /**
     * Gets application name and version
     * 
     * @return name and version
     */
    public static String formatString() {
        return APP_NAME.concat(" ").concat(VERSION_NUMBER);
    }
    
    /**
     * Gets system properties as single formatted string
     * 
     * @return environment
     */
    public static String getSystemProperties() {
        final StringBuilder props = new StringBuilder();
        for (final Object p : System.getProperties().keySet()) {
            props.append(p).append(": ").append(System.getProperty("" + p));
            props.append('\n');
        }
        return props.toString().trim();
    }
    
    /**
     * Formats current configuration properties as string
     * 
     * @return configuration
     */
    public static String formatConfigurationProperties() {
        final StringBuilder props = new StringBuilder();
        for (Entry<String, String> entry : ConfigurationFactory
                .getConfigurationFactory()
                .getConfiguration().getProperties().entrySet()) {
            props.append(entry.getKey()).append(": ");
            props.append(entry.getValue()).append('\n');
        }
        return props.toString().trim();
    }
    
    /**
     * Generates Environmental report
     * 
     * @return report
     */
    public static String getEnvironmentReport() {
        final StringBuilder env = 
                new StringBuilder("Hawkscope Environment Report\n");
        env.append(SEPARATOR);
        env.append(formatFullString()).append('\n');
        env.append(SEPARATOR);
        env.append(formatConfigurationProperties()).append('\n');
        env.append(SEPARATOR);
        env.append(getSystemProperties()).append('\n');
        env.append(SEPARATOR);
        env.append(new Date()).append('\n');
        env.append(SEPARATOR);
        return env.toString();
    }
    
    /**
     * Formats bug report from exception
     * 
     * @param e cause of possible bug
     * @return formatted report
     */
    public static String getBugReport(final Throwable e) {
        final StringBuilder sb = new StringBuilder(300);
        final Writer stringWriter = new StringWriter();
        final PrintWriter w = new PrintWriter(stringWriter);
        e.printStackTrace(w);
        sb.append("Hawkscope Bug Report").append('\n')
            .append(SEPARATOR)
            .append(e.getMessage().replaceAll(": ", ":\n")).append('\n')
            .append(SEPARATOR)
            .append(stringWriter.toString()).append('\n')  
            .append(SEPARATOR)
            .append(getEnvironmentReport());
        return sb.toString();
    }
    
}
