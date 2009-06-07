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

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.util.IOUtils;
import com.varaneckas.hawkscope.util.OSUtils;

/**
 * Global hotkey listener for invoking hawkscope menu
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
     * @return
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
    protected boolean loadJarLibrary(String jarLib) {
        final String tempLib = System.getProperty("java.io.tmpdir") 
                + File.separator + jarLib;
        boolean copied = IOUtils.copyFile(jarLib, tempLib);
        if (!copied) {
        	return false;
        }
        System.load(tempLib);
        return true;
    }

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

}
