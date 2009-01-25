package com.varaneckas.hawkscope;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.Proxy.Type;
import java.util.Date;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;

/**
 * Hawkscope Version information
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class Version {
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(Version.class);
    
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
    public static final String VERSION_NUMBER = "0.3.1";
    
    /**
     * Application version date
     */
    public static final String VERSION_DATE = "2009-??-??";
    
    /**
     * Application Homepage URL
     */
    public static final String HOMEPAGE = "http://hawkscope.googlecode.com";
    
    /**
     * URL where latest Hawkscope version is stored
     */
    public static final String VERSION_CHECK_URL 
            = "http://hawkscope.googlecode.com/svn/trunk/version";
    
    /**
     * URL where more recent version of Hawkscope can be downloaded
     */
    public static final String DOWNLOAD_URL 
            = "http://code.google.com/p/hawkscope/downloads/list";
    
    /**
     * Tells if update is available
     */
    private static boolean isUpdateAvailable = false; 
    
    /**
     * Asynchronious update check on startup.
     */
    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000L);
                } catch (final InterruptedException e) {
                    //yawn
                }
                checkForUpdate();
            }
        }, "version-updater").start();
    }
    
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
    
    /**
     * Asynchroniously checks if a newer version of Hawkscope is available
     * 
     * @return
     */
    public static void checkForUpdate() {
        final Configuration cfg = ConfigurationFactory.getConfigurationFactory()
                .getConfiguration();
        if (!cfg.checkForUpdates()) {
            return;
        }
        try {
            log.debug("Checking for updates...");
            URLConnection conn = null;
            final URL versionCheckUrl = new URL(VERSION_CHECK_URL);
            if (cfg.isHttpProxyInUse()) {
                final Proxy proxy = new Proxy(Type.HTTP, InetSocketAddress
                        .createUnresolved(cfg.getHttpProxyHost()
                                , cfg.getHttpProxyPort()));
                conn = versionCheckUrl.openConnection(proxy);
            } else {
                conn = versionCheckUrl.openConnection();
            }
            //timeout in 3 seconds
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            final InputStream io = conn.getInputStream();
            int c = 0;
            final StringBuilder version = new StringBuilder();
            while ((c = io.read()) != -1) {
                version.append((char) c);
            }
            log.debug("Check complete. Latest version: " + version.toString());
            if (VERSION_NUMBER.compareTo(version.toString()) < 0) {
                log.info("Newer version available (" + version.toString() 
                        + "). You should update Hawkscope!");
                isUpdateAvailable = true;
            } else {
                log.info("You have the latest available version of Hawkscope: " 
                        + VERSION_NUMBER);
            }
        } catch (final Exception e) {
            log.info("Failed checking for update: " + e.getMessage());
        }
    }
    
    /**
     * Tells is update is available
     * 
     * @return
     */
    public static boolean isUpdateAvailable() {
        return isUpdateAvailable;
    }
    
}
