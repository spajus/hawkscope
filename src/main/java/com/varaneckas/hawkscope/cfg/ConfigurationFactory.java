package com.varaneckas.hawkscope.cfg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.util.IOUtils;

/**
 * Hawkscope configuration factory
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class ConfigurationFactory {

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(ConfigurationFactory.class);
    
    /**
     * Configuration file name. 
     * 
     * It will be prefixed with "." and suffixed with ".properties".
     */
    protected static final String CONFIG_FILE_NAME = "hawkscope";

    /**
     * Concrete Singleton instance
     */
    private static ConfigurationFactory concreteInstance = null;
    
    /**
     * Configuration
     */
    private Configuration configuration = null;

    /**
     * Gets concrete implementation of {@link ConfigurationFactory} with use of
     * command line arguments
     * 
     * @param args command line arguments
     * @return concrete instance
     */
    public static ConfigurationFactory getConfigurationFactory(
            final String[] args) {
        final ConfigurationFactory cf = getConfigurationFactory();
        cf.setCommandLineArgs(args);
        return cf;
    }
    
    /**
     * Gets concrete implementation of {@link ConfigurationFactory}
     * 
     * @return concrete instance
     */
    public static ConfigurationFactory getConfigurationFactory() {
        synchronized (ConfigurationFactory.class) {
            if (concreteInstance == null) {
                if (new File(System.getProperty("user.home")
                		.replaceAll("\\\\", "/")).canWrite()) {
                    concreteInstance = new UserHomeConfigurationFactory();
                } else {
                    concreteInstance = new BasicConfigurationFactory();
                }
            }
        }
        return concreteInstance;
    }

    /**
     * Gets {@link Configuration} that can be used within application
     * 
     * @return configuration
     */
    public Configuration getConfiguration() {
        if (configuration == null) {
            loadConfiguration();
        }
        return configuration;
    }

    /**
     * Loads {@link Configuration}
     * 
     * @see #configuration
     */
    private void loadConfiguration() {
        final Map<String, String> cfg = getDefaults();
        try {
            
            final ResourceBundle data = ResourceBundle
                    .getBundle(CONFIG_FILE_NAME, Locale.ENGLISH
                            , new ClassLoader() {
                @Override
                protected URL findResource(final String name) {
                    try {
                        final String file = loadConfigFilePath() + "/." + name;
                        log.debug("Resolving config file: " + file);
                        return new File(file).toURI().toURL();
                    } catch (final MalformedURLException e) {
                        log.error("Failed loading file", e);
                        return null;
                    }
                }
            });
            final Enumeration<String> keys = data.getKeys();
            while (keys.hasMoreElements()) {
            	String key = keys.nextElement();
            	cfg.put(key, data.getString(key));
            }
        } catch (final MissingResourceException e) {
            log.debug("Configuration not found, using defaults. (" 
                    + e.getMessage() + ")");
            write(new Configuration(cfg));
        }
        configuration = new Configuration(cfg);
    }

    /**
     * Writes {@link Configuration} to file
     * 
     * @param configuration configuration to be written
     */
    public void write(final Configuration cfg) {
        Writer config = null;
        try {
            final String file = loadConfigFilePath() + "/." 
                    + CONFIG_FILE_NAME + ".properties";
            log.debug("Writing config file: " + file);
            config = new BufferedWriter(new FileWriter(file));
            for (final String key : cfg.getProperties().keySet()) {
                config.write(key);
                config.write(" = ");
                config.write(cfg.getProperties().get(key));
                config.write('\n');
            }
            config.close();
        } catch (final IOException e) {
            log.error("Failed writing config file", e);
        }
        finally {
            IOUtils.closeQuietly(config);
        }
    }
    
    /**
     * Sets command line arguments. Should influence the default parameter map.
     * 
     * @param args
     */
    private void setCommandLineArgs(final String[] args) {
        if (args.length == 0) return;
        if (args.length % 2 != 0) {
            log.warn("Odd count of '-key value' argument pairs. " +
            		"Skipping arguments.");
            return;
        }
        final Map<String, String> argPairs = new HashMap<String, String>();
        for (int i = 0; i < args.length; i+=2) {
            String key = args[i];
            String value = args[i+1];
            if (!key.startsWith("-")) {
                log.warn("Skipping invalid argument pair: " + key + ": " + value);
                continue;
            }
            argPairs.put(key.substring(1), value);
        }
        //FIXME for http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6438179
        if (argPairs.containsKey("delay")) {
            try {
                final long delay = Long.parseLong(argPairs.remove("delay"));
                log.info("Delaying Hawkscope startup for " 
                        + (delay / 1000.0) + " seconds.");
                Thread.sleep(delay);
                log.info("Delay complete, resuming.");
            } catch (final Exception e) {
                log.warn("Failed delaying hawkscope startup", e);
            }
        }
        for (final Entry<String, String> pair : argPairs.entrySet()) {
            if (log.isDebugEnabled()) {
                log.debug("Overriding " + pair.getKey() + ". Old: " 
                        + getConfiguration().getProperties().get(pair.getKey())
                        + ". New: " + pair.getValue());
            }
            getConfiguration().getProperties().put(pair.getKey(), 
                    pair.getValue());
        }
    }

    /**
     * Loads configuration file path
     * 
     * @return
     */
    abstract protected String loadConfigFilePath();

    /**
     * Gets default configuration parameters
     * 
     * @return
     */
    abstract protected Map<String, String> getDefaults();
    
}
