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
package com.varaneckas.hawkscope.plugin;

/**
 * A {@link Plugin} emulator that is used when actual plugin is disabled.
 * Prevents classloading of actual plugin
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class DisabledPlugin extends PluginAdapter {

    /**
     * Class name of actual plugin
     */
    private final String className;
    
    /**
     * Constructor with class name of actual plugin
     * 
     * @param className fully qualified class name
     */
    public DisabledPlugin(final String className) {
        this.className = className;
        this.enabled = false;
    }
    
    public String getDescription() {
        return "Plugin disabled.";
    }

    public String getName() {
        return className.replaceAll(".*\\.", "");
    }

    public String getVersion() {
        return "";
    }
    
    /**
     * Gets the class name of an emulated plugin
     * @return
     */
    public String getClassName() {
        return className;
    }
    
}
