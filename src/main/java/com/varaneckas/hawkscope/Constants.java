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

import com.varaneckas.hawkscope.cfg.Configuration;

/**
 * A class for storing constant values
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class Constants {
    
    /**
     * A "UTF-8" String litteral
     */
    public static final String ENCODING = Configuration.APP_PROPS
    		.getString("encoding");
    
    /**
     * Slash for regex
     */
    public static final String REGEX_SLASH = "/";
    
    /**
     * Backslash for regex
     */
    public static final String REGEX_BACKSLASH = "\\\\";
    
    /**
     * Hawkscope URL base
     */
    public static final String HAWKSCOPE_URL_ROOT = Configuration.APP_PROPS
    		.getString("googlecode.url");
        

}
