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
package com.varaneckas.hawkscope.util;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;

/**
 * Modular {@link Authenticator} for Hawkscope.
 * 
 * Should be used with plugins and version checks. 
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class ModularAuthenticator extends java.net.Authenticator {
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(ModularAuthenticator.class);
    
    /**
     * Singleton instance
     */
    private static final ModularAuthenticator instance = 
        new ModularAuthenticator();
    
    /**
     * {@link PasswordAuthentication} instances that perform authentication 
     * for different URLs.
     */
    private final Map<String, PasswordAuthentication> modules = 
        new HashMap<String, PasswordAuthentication>();
    
    /**
     * Proxy authentication
     */
    private PasswordAuthentication proxyAuth;

    /**
     * Singleton constructor
     */
    private ModularAuthenticator() {
        //nothing to do
    }
    
    /**
     * Gets authentication modules
     * 
     * @return Map of URL -> {@link PasswordAuthentication}
     */
    public Map<String, PasswordAuthentication> getModules() {
        return this.modules;
    }

    /**
     * Singleton instance getter
     * 
     * @return #instance
     */
    public static ModularAuthenticator getInstance() {
        return instance;
    }
    
    /**
     * Registers this authenticator in JVM
     */
    public static void register() {
        Authenticator.setDefault(getInstance());        
        getInstance().loadProxyAuth(ConfigurationFactory
                .getConfigurationFactory().getConfiguration());
    }
    
    /**
     * Loads proxy configuration
     * 
     * @param cfg configuration
     */
    public void loadProxyAuth(final Configuration cfg) {
        if (cfg.isHttpProxyInUse() && cfg.isHttpProxyAuthInUse()) {
            log.debug("Loading proxy authentication data");
            getInstance().proxyAuth = new PasswordAuthentication(
                    cfg.getHttpProxyAuthUsername(),
                    cfg.getHttpProxyAuthPassword().toCharArray());
        } else {
            log.debug("Proxy authentication is disabled");
            proxyAuth = null;
        }
    }
    
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        if (getRequestorType().equals(RequestorType.PROXY)) {
            if (log.isDebugEnabled()) {
                log.debug("Proxy authentication requested: " 
                        + getRequestingPrompt());
                log.debug("Proxy user/pass auth available: " 
                        + proxyAuth != null);
            }
            return proxyAuth;
        }
        final PasswordAuthentication auth = modules.get(getRequestingHost());
        if (auth != null) {
            if (log.isDebugEnabled()) {
                log.debug("Password authentication found for host: " 
                        + getRequestingHost());
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Authentication not found for host: " 
                        + getRequestingHost());
            }
        }
        return auth;
    }
    
    @Override
    protected void finalize() throws Throwable {
        log.debug("Destroying ModularAuthentication");
        modules.clear();
        proxyAuth = null;
        super.finalize();
    }

}
