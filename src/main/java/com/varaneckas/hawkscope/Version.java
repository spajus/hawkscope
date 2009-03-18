/*
 * Copyright (c) 2008-2009 Tomas Varaneckas
 * http://www.varaneckas.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.varaneckas.hawkscope;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
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
import com.varaneckas.hawkscope.plugin.PluginManager;
import com.varaneckas.hawkscope.util.OSUtils;

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
    public static final String APP_NAME = Configuration.APP_PROPS
    		.getString("name");
    
    /**
     * Application slogan
     */
    public static final String APP_SLOGAN = Configuration.APP_PROPS
    		.getString("slogan");
    
    /**
     * Application version number   
     */
    public static final String VERSION_NUMBER = Configuration.APP_PROPS
    		.getString("version.number");
    
    /**
     * Application version date
     */
    public static final String VERSION_DATE = Configuration.APP_PROPS
    		.getString("version.date");
    
    /**
     * Application Homepage URL
     */
    public static final String HOMEPAGE = Configuration.APP_PROPS
    		.getString("homepage");
    
    /**
     * Configuration instance
     */
    final static Configuration cfg = ConfigurationFactory
    		.getConfigurationFactory().getConfiguration();
    
    /**
     * URL where latest Hawkscope version is stored
     */
    public static final String VERSION_CHECK_URL = Configuration.APP_PROPS
    		.getString("version.url")
    		.concat(OSUtils.CURRENT_OS.toString().toLowerCase());
    
    /**
     * URL where plugin versions are stored
     */
    public static final String PLUGIN_VERSION_CHECK_URL = Configuration.APP_PROPS
            .getString("version.url").concat("plugins");

    /**
     * Plugins download URL
     */
    public static final String PLUGINS_URL 
    		= Constants.HAWKSCOPE_URL_ROOT.concat("wiki/Plugins");
    
    /**
     * URL where more recent version of Hawkscope can be downloaded
     */
    public static final String DOWNLOAD_URL 
            = Constants.HAWKSCOPE_URL_ROOT.concat("downloads/list");
    
    /**
     * Tells if update is available
     */
    private static Boolean isUpdateAvailable = null; 
    
    /**
     * Asynchronious update check on startup.
     */
    static {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000L);
                } catch (final InterruptedException e) {
                	log.warn("Version check interrupted", e);
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
            props.append(p).append(": ").append(System.getProperty((String) p));
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
        for (final Entry<String, String> entry : cfg.getProperties().entrySet()) {
            props.append(entry.getKey()).append(": ");
            //we don't want user passwords in bug reports...
            if (entry.getKey().contains("pass")) {
                props.append("******\n");
            } else {
                props.append(entry.getValue()).append('\n');
            }
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
        if (!cfg.checkForUpdates()) {
            return;
        }
        try {
            log.debug("Checking for updates...");
            URLConnection conn = null;
            final URL versionCheckUrl = new URL(VERSION_CHECK_URL);
            Proxy proxy = null;
            if (cfg.isHttpProxyInUse()) {
                if (cfg.isHttpProxyAuthInUse()) {
                    Authenticator.setDefault(new Authenticator() {
                        @Override
                        protected PasswordAuthentication 
                                getPasswordAuthentication() {
                            if (getRequestorType().equals(RequestorType.PROXY)) {
                                log.debug("Performing HTTP Proxy Authentication");
                                return new PasswordAuthentication(
                                        cfg.getHttpProxyAuthUsername(), 
                                        cfg.getHttpProxyAuthPassword()
                                                .toCharArray());
                            } else {
                                return null;
                            }
                        }
                    });
                }
                proxy = new Proxy(Type.HTTP, InetSocketAddress
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
            if (log.isDebugEnabled()) {
            	log.debug("Check complete. Latest " + OSUtils.CURRENT_OS 
            			+ " version: " + version.toString());
            }
            if (VERSION_NUMBER.compareTo(version.toString()) < 0) {
                log.info("Newer " + OSUtils.CURRENT_OS + " version available (" 
                        + version.toString() 
                        + "). You should update Hawkscope!");
                isUpdateAvailable = true;
            } else {
                log.info("You have the latest available version of Hawkscope: " 
                        + VERSION_NUMBER);
                isUpdateAvailable = false;
            }
            PluginManager.getInstance().checkPluginUpdates(
                    PLUGIN_VERSION_CHECK_URL, proxy);
        } catch (final Exception e) {
            log.info("Failed checking for update: " + e.getMessage());
        }
    }
    
    /**
     * Tells is update is available
     * 
     * @return null if unavailable, otherwise a Boolean
     */
    public static Boolean isUpdateAvailable() {
        return isUpdateAvailable;
    }
    
}
