package com.varaneckas.hawkscope;

/**
 * Hawkscope Version information
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class Version {
    
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
    public static final String VERSION_NUMBER = "0.1.2";
    
    /**
     * Application version date
     */
    public static final String VERSION_DATE = "2008-09-28";
    
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
                .concat(") [").concat(HOMEPAGE);
    }
    
    /**
     * Gets application name and version
     * 
     * @return name and version
     */
    public static String formatString() {
        return APP_NAME.concat(" ").concat(VERSION_NUMBER);
    }
    
}
