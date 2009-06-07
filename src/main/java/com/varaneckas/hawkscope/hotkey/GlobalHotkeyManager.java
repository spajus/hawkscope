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
package com.varaneckas.hawkscope.hotkey;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.util.IOUtils;
import com.varaneckas.hawkscope.util.OSUtils;
import com.varaneckas.hawkscope.util.OSUtils.OS;

/**
 * Global hotkey listener for invoking hawkscope menu
 * 
 * TODO implementation for Mac OS X
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class GlobalHotkeyManager {
    
    /**
     * Global Hotkey Listener instance
     */
    private static GlobalHotkeyManager instance = null;
    
    /**
     * Loads the required instance if possible
     * 
     * @return instance
     */
    public static synchronized GlobalHotkeyManager getInstance() {
        if (instance == null) {
            instance = chooseImpl();
        }
        return instance;
    }
    
    /**
     * Implementation should clear all defined hotkeys
     */
    public abstract void clearHotkeys();
    
    /**
     * Implementation should register AWT hotkey
     * 
     * @param specKey AWT modifier, like InputEvent.CRTL_MASK
     * @param key AWT key, like InputEvent.VK_SPACE
     */
    public abstract void registerHotkey(int specKey, int key);
    
    /**
     * Chooses {@link GlobalHotkeyManager} implementation according to OS
     * 
     * @return GlobalHotkeyManager or null in case OS does not support one
     */
    private static GlobalHotkeyManager chooseImpl() {
        switch (OSUtils.CURRENT_OS) {
        case UNIX:
            return new X11GlobalHotkeyManager();
        case WIN:
            return new WinGlobalHotkeyManager();
        default:
            return null;
        }
    }
    
    /**
     * Loads native library located inside some jar in classpath
     * 
     * @param jarLib library name
     * @return success?
     */
    protected boolean loadJarLibrary(final String jarLib) {
        final String tempLib = System.getProperty("java.io.tmpdir") 
                + File.separator + jarLib;
        boolean copied = IOUtils.copyFile(jarLib, tempLib);
        if (!copied) {
        	return false;
        }
        System.load(tempLib);
        return true;
    }

    /**
     * Configures the GlobalHotkeyManager according to settings. Uses
     * {@link Configuration} to get required parameters.
     */
    public void configure() {
        final Configuration cfg = ConfigurationFactory.getConfigurationFactory()
                .getConfiguration();
        if (cfg.isHotkeyEnabled()) {
            final int modifier = cfg.getHotkeyModifier();
            final int key = cfg.getHotkey();
            if (modifier > 0 && key > 0) {
                registerHotkey(modifier, key);
            }
        }
    }
    
    /**
     * Interprets KeyEvent and returns something like "Ctrl + H" or 
     * "Alt + Space". Works only with ASCII keys from 32 to 126.
     * 
     * @param ev KeyEvent to interpret
     * @return interpretation string
     */
    public static String interpretKeyEvent(final KeyEvent ev) {
        String repr = "";
        ev.doit = false;
        switch (ev.stateMask) {
        case SWT.SHIFT:   repr = "Shift + "; break;
        case SWT.CTRL:    repr = "Ctrl + ";  break;
        case SWT.ALT:     repr = "Alt + "; break;
        case SWT.COMMAND: repr = OSUtils.CURRENT_OS.equals(OS.MAC) 
            ? "Command + " : "Win + "; break;
        default: return "";
        }
        if (ev.keyCode < 32 || ev.keyCode > 126) {
            return "";
        }
        char c = (char) ev.keyCode;
        if (c == ' ') {
            repr += "Space";
        } else {
            repr += ("" + c).toUpperCase();
        }
        return repr;
    }

}
