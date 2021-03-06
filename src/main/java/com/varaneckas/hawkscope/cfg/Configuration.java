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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.Constants;
import com.varaneckas.hawkscope.menu.MainMenu;
import com.varaneckas.hawkscope.util.Base64;
import com.varaneckas.hawkscope.util.PathUtils;
import com.varaneckas.hawkscope.util.RC4Crypt;
import com.varaneckas.hawkscope.util.UTF8ResourceBundle;

/**
 * Hawkscope configuration.
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class Configuration {
    
    /**
     * Theme list
     */
    public static final Map<String, String> THEMES = new HashMap<String, String>();
    
    /**
     * Initialize available themes
     */
    static {
        THEMES.put("default", "Ubuntu");
        THEMES.put("mac", "Mac OS X");
        THEMES.put("vista", "Vista");
        THEMES.put("xp", "Windows XP");
    }
    
    /**
     * Logger 
     */
    private static final Log log = LogFactory.getLog(Configuration.class);
    
    /**
     * Hawkscope properties bundle
     */
    public static final ResourceBundle APP_PROPS = UTF8ResourceBundle
    		.getBundle("internal", Configuration.class.getClassLoader());
    
    /**
     * Display hidden files property name
     */
    public static final String HIDDEN_FILES_DISPLAYED = "display.hidden";

    /**
     * Display floppy drives
     */
    public static final String FLOPPY_DRIVES_DISPLAYED = "display.floppy";
    
    /**
     * Quick folder access list property name
     */
    public static final String QUICK_ACCESS_LIST = "quick.access.list";

    /**
     * Quick folder access names list property name
     */
    public static final String QUICK_ACCESS_NAMES_LIST = "quick.access.names.list";
    
    /**
     * Property name for blacklist of file/folder names that will be filtered out 
     */
    public static final String FILESYSTEM_BLACKLIST = "filesystem.blacklist";
    
    /**
     * Delay for reloading menu after it was hidden (milliseconds)
     */
    public static final String MENU_RELOAD_DELAY = "menu.reload.delay";
    
    /**
     * Should Hawkscope check for updates?
     */
    public static final String CHECK_FOR_UPDATES = "update.check";
    
    /**
     * HTTP proxy in use?
     */
    public static final String HTTP_PROXY_USE = "http.proxy.use";
    
    /**
     * HTTP proxy host
     */
    public static final String HTTP_PROXY_HOST = "http.proxy.host";
    
    /**
     * HTTP proxy port
     */
    public static final String HTTP_PROXY_PORT = "http.proxy.port";
    
    /**
     * Use HTTP Proxy authentication
     */
    public static final String HTTP_PROXY_AUTH_USE = "http.proxy.auth.use";
    
    /**
     * HTTP Proxy authentication username
     */
    public static final String HTTP_PROXY_AUTH_USERNAME = 
            "http.proxy.auth.username";
    
    /**
     * HTTP Proxy authentication password
     */
    public static final String HTTP_PROXY_AUTH_PASSWORD = 
            "http.proxy.auth.password";
    
    /**
     * Tells Hawkscope to use operating system icons where possible;
     */
    public static final String USE_OS_ICONS = "use.os.icons";
    
    /**
     * Icons theme
     */
    public static final String ICONS_THEME = "icons.theme";
    
    /**
     * Hides known file extensions
     */
    public static final String HIDE_FILE_EXT = "hide.known.file.ext";
    
    /**
     * Global hotkey enabled
     */
    public static final String HOTKEY_ENABLED = "global.hotkey.enabled";
    
    /**
     * Global hotkey modifier (i.e.: Shift + Space)
     */
    public static final String HOTKEY_REPR = "global.hotkey";
    
    /**
     * Mac menubar blues workaround
     * 
     * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=263589
     */
    public static final String MAC_MENUBAR_BLUES_WORKAROUND = 
    	"mac.menubar.blues.workaround";
    
    /**
     * Plugin location directory
     */
    public static final String PLUGIN_DIR = "plugin.dir";
    
    /**
     * Salt
     */
    public static final String SALT = "a bit of tasty salt";
    
    /**
     * Properties {@link Map}
     */
    private final Map<String, String> properties;
    
    /**
     * Properties changed flag. If true, ConfigurationFactory should persist
     * data to disk.
     */
    private boolean changed = false;
    
    /**
     * Tells if configuration is changed
     * 
     * @return changed
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * Constructor that initializes properties
     * 
     * @param properties map
     */
    public Configuration(final Map<String, String> properties) {
        this.properties = properties;
    }
    
    /**
     * Gets properties map
     */
    public Map<String, String> getProperties() {
        return properties;
    }
    
    /**
     * Tells if to display hidden files
     * 
     * @return display
     */
    public boolean isHiddenFilesDisplayed() {
        return properties.get(Configuration.HIDDEN_FILES_DISPLAYED).equals("1");
    }
    
    /**
     * Tells if to display floppy drives
     * 
     * @return display 
     */
    public boolean isFloppyDrivesDisplayed() {
        return properties.get(Configuration.FLOPPY_DRIVES_DISPLAYED).equals("1");
    }
    
    /**
     * Gets list of {@link File}s that can be accessed quickly from 
     * {@link MainMenu}
     * 
     * @return list of files
     */
    public List<File> getQuickAccessList() {
        return PathUtils.pathToDirList(
                properties.get(Configuration.QUICK_ACCESS_LIST), ";");
    }
    
    /**
     * Gets raw quick access list for settings window
     * 
     * @return
     */
    public List<String> getRawQuickAccessList() {
        return Arrays.asList(properties.get(Configuration.QUICK_ACCESS_LIST)
                .split(";"));
    }
    
    /**
     * Gets a list of quick access names in format entry -> name
     * 
     * @return
     */
    public Map<String, String> getQuickAccessNames() {
        final String prop = properties.get(Configuration.QUICK_ACCESS_NAMES_LIST);
        if (prop == null) {
            return Collections.emptyMap();
        }
        final List<String> raw = Arrays.asList(prop.split(";"));
        final Map<String, String> names = new HashMap<String, String>();
        for (final String name : raw) {
            String[] n = name.split("\\:\\:");
            if (n != null && n.length == 2) {
                names.put(n[0].trim(), n[1].trim());
            } else {
                log.warn("Weird quick access name entry: " + name);
            }
        }
        return names;
    }
    
    /**
     * Gets blacklist of skipped items
     * 
     * @return
     */
    public List<File> getBlackList() {
        return PathUtils.pathToDirList(properties.get(Configuration
                .FILESYSTEM_BLACKLIST), ";");
    }
    
    /**
     * Gets raw blacklist of skipped items for settings window
     * 
     * @return
     */
    public List<String> getRawBlackList() {
        return Arrays.asList(properties.get(Configuration.FILESYSTEM_BLACKLIST)
                .split(";"));
    }
    
    /**
     * Gets menu reload delay. 3 seconds (3000) default.
     * 
     * @return menu reload delay in milliseconds
     */
    public long getMenuReloadDelay() {
        long millis = 3000L; //default in case of missing
        try {
            millis = Long.valueOf(properties.get(MENU_RELOAD_DELAY));
        } catch (final Exception e) {
            log.warn("Could not read " + MENU_RELOAD_DELAY 
                    + ", defaulting to 10 seconds: " + e.getMessage());
        }
        return millis;
    }
    
    /**
     * Tells if Hawkscope should check for updates
     * 
     * @return
     */
    public boolean checkForUpdates() {
        return properties.get(CHECK_FOR_UPDATES).equals("1");
    }
    
    /**
     * Tells if HTTP proxy is in use
     * 
     * @return
     */
    public boolean isHttpProxyInUse() {
        return properties.get(HTTP_PROXY_USE).equals("1");
    }
    
    /**
     * Gets HTTP proxy host
     * 
     * @return
     */
    public String getHttpProxyHost() {
        return properties.get(HTTP_PROXY_HOST);
    }
    
    /**
     * Tells if HTTP proxy authentication is in use
     * 
     * @return
     */
    public boolean isHttpProxyAuthInUse() {
        return properties.get(HTTP_PROXY_AUTH_USE).equals("1");
    }
    
    /**
     * Gets HTTP Proxy authentication username
     * 
     * @return
     */
    public String getHttpProxyAuthUsername() {
        return properties.get(HTTP_PROXY_AUTH_USERNAME);
    }
    
    /**
     * Gets HTTP Proxy authentication password
     * 
     * @return
     */
    public String getHttpProxyAuthPassword() {
    	return getPasswordProperty(HTTP_PROXY_AUTH_PASSWORD);
    }
    
    /**
     * Tells if to use OS icons when possible
     * 
     * @return
     */
    public boolean useOsIcons() {
        return properties.get(USE_OS_ICONS).equals("1");
    }
    
    /**
     * Gets Icons theme name
     * 
     * @return
     */
    public String getIconsTheme() {
        final String theme = properties.get(ICONS_THEME);
        if (theme == null || theme.equals("")) {
            return "default";
        }
        return theme;
    }
    
    /**
     * Gets nice theme name
     * 
     * @return
     */
    public String getIconsThemeName() {
        return THEMES.get(getIconsTheme());
    }
    
    /**
     * Gets theme id by name
     * 
     * @param name
     * @return
     */
    public String getThemeByName(final String name) {
        if (name == null || name.equals("")) {
            return "default";
        }
        for (Entry<String, String> entry : THEMES.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(name)) {
                return entry.getKey();
            }
        }
        return "default";
    }
    
    /**
     * Tells if to hide known file extensions
     * 
     * @return
     */
    public boolean isKnownFileExtHidden() {
        return properties.get(HIDE_FILE_EXT).equals("1");
    }
 
    /**
     * Gets HTTP proxy port
     * 
     * @return port number. 8080 on invalid value
     */
    public int getHttpProxyPort() {
        int port = 8080;
        try {
            port = Integer.valueOf(properties.get(HTTP_PROXY_PORT));
        } catch (final Exception e) {
            log.warn("Could not read " + HTTP_PROXY_PORT 
                    + ", defaulting to 8080: " + e.getMessage());
        }
        return port;
    }
    
    /**
     * Gets plugin location directory
     * 
     * @return
     */
    public File getPluginLocation() {
       final String dir = PathUtils.interpret(properties.get(PLUGIN_DIR));
       final File pluginDir = new File(dir);
       if (!pluginDir.isDirectory() && !pluginDir.mkdir()) {
           return null;
       }
       return pluginDir;
    }
    
    /**
     * Encrypts a password property
     * 
     * @param propName
     * @param pass
     */
    public void setPasswordProperty(final String propName, final String pass) {
    	try {
			getProperties().put(propName, Base64.encodeToString(
					RC4Crypt.encrypt(pass.getBytes(Constants.ENCODING), 
					SALT.getBytes(Constants.ENCODING)), false));
		} catch (final Exception e) {
			log.warn("Failed encrypting password property: " + propName);
			getProperties().put(propName, "");
		}
    }
    
    /**
     * Decrypts a password property
     * 
     * @param propName
     * @return
     */
    public String getPasswordProperty(final String propName) {
        try {
			final String prop = properties.get(propName);
			if (prop == null || prop.equals("")) {
				return ""; 
			}
			return new String(RC4Crypt.decrypt(Base64.decode(prop), 
					SALT.getBytes(Constants.ENCODING)), Constants.ENCODING);
		} catch (final Exception e) {
			log.warn("Failed decrypting password property: " + propName);
			return "";
		}
    }

    /**
     * Tells if global hotkey is enabled
     * 
     * @return true / false
     */
    public boolean isHotkeyEnabled() {
        return properties.get(HOTKEY_ENABLED).equals("1");
    }
    
    /**
     * Parses hotkey string and returns int value of modifier.
     * I.e.: InputEvent.CTRL_DOWN_MASK
     * 
     * @return InputEvent modifier value
     */
    public int getHotkeyModifier() {
        final String hotkey = properties.get(HOTKEY_REPR);
        if (hotkey != null && hotkey.contains("+")) {
            //good hotkey
            if (hotkey.startsWith("Shift")) {
                return InputEvent.SHIFT_DOWN_MASK;
            } 
            if (hotkey.startsWith("Ctrl")) {
                return InputEvent.CTRL_DOWN_MASK;
            }
            if (hotkey.startsWith("Alt")) {
                return InputEvent.ALT_DOWN_MASK;
            }
            if (hotkey.startsWith("Win") || hotkey.startsWith("Command")) {
                return InputEvent.META_DOWN_MASK;
            }
        }
        //bad hotkey or no hotkey
        return -1;
    }
    
    /**
     * Parses hotkey string and returns char value of the shortcut key
     *  
     * @return int (char) of ascii key ranged from 32 to 126
     */
    public int getHotkey() {
        final String hotkey = properties.get(HOTKEY_REPR);
        if (hotkey != null && hotkey.contains("+")) {
            //good hotkey
            if (hotkey.endsWith("Space")) {
                return KeyEvent.VK_SPACE;
            } else {
                return hotkey.charAt(hotkey.length() - 1);
            }
        }
        //bad or no hotkey
        return -1;
    }
    
}
