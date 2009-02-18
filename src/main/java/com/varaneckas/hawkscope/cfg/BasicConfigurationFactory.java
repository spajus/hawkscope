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
package com.varaneckas.hawkscope.cfg;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.varaneckas.hawkscope.util.OSUtils;
import com.varaneckas.hawkscope.util.OSUtils.OS;

/**
 * Basic Configuration Factory
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class BasicConfigurationFactory extends ConfigurationFactory {

    @Override
    protected String loadConfigFilePath() {
        return new File(".").getAbsolutePath();
    }

    @Override
    protected Map<String, String> getDefaults() {
        final Map<String, String> data = new HashMap<String, String>();
        //hidden files are not displayed
        data.put(Configuration.HIDDEN_FILES_DISPLAYED, "0");
        //quick access list contains one entry - user home, 
        //read from system properties
        if (OSUtils.CURRENT_OS.equals(OS.MAC)) {
        	data.put(Configuration.QUICK_ACCESS_LIST, 
        	        "${user.home};/Applications");
        } else {
        	data.put(Configuration.QUICK_ACCESS_LIST, "${user.home}");
        }
        //floppy drives are not displayed
        data.put(Configuration.FLOPPY_DRIVES_DISPLAYED, "0");
        //blacklist is empty
        data.put(Configuration.FILESYSTEM_BLACKLIST, "");
        //3 seconds menu reload delay
        data.put(Configuration.MENU_RELOAD_DELAY, "3000");
        //check for update by default
        data.put(Configuration.CHECK_FOR_UPDATES, "1");
        //no proxy by default
        data.put(Configuration.HTTP_PROXY_USE, "0");
        //no proxy host by default
        data.put(Configuration.HTTP_PROXY_HOST, "");
        //8080 default proxy port
        data.put(Configuration.HTTP_PROXY_PORT, "8080");
        //no proxy auth by default
        data.put(Configuration.HTTP_PROXY_AUTH_USE, "0");
        //no proxy auth username by default
        data.put(Configuration.HTTP_PROXY_AUTH_USERNAME, "");
        //no proxy auth password by default
        data.put(Configuration.HTTP_PROXY_AUTH_PASSWORD, "");
        //use OS icons - off by default for unix
        String useOsIcons = "1";
        switch (OSUtils.CURRENT_OS) {
        case UNIX:
            useOsIcons = "0";
            break;
        }
        data.put(Configuration.USE_OS_ICONS, useOsIcons);
        
        //plugin dir
        String pluginDir = "${user.home}/.hawkscope-plugins";
        switch (OSUtils.CURRENT_OS) {
        case UNIX:
            pluginDir = "/usr/share/hawkscope/plugins";
            break;
        }
        data.put(Configuration.PLUGIN_DIR, pluginDir);

        if (OSUtils.CURRENT_OS.equals(OS.MAC)) {
        	//Mac menubar blues 
        	data.put(Configuration.MAC_MENUBAR_BLUES_WORKAROUND, "1");
        }
        return data;
    }

}
