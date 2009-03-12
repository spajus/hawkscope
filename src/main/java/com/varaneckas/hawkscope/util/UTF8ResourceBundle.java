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

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.varaneckas.hawkscope.Constants;

/**
 * Unicode resource bundle implementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class UTF8ResourceBundle {
    
    /**
     * Gets the utf8 friendly bundle
     * 
     * @param baseName
     * @param loader
     * @return
     */
    public static final ResourceBundle getBundle(final String baseName, 
            final ClassLoader loader) {
        final ResourceBundle bundle = ResourceBundle.getBundle(baseName, 
                Locale.ENGLISH, loader);
        return createUtf8PropertyResourceBundle(bundle);
    }

    private static ResourceBundle createUtf8PropertyResourceBundle(
            final ResourceBundle bundle) {
        if (!(bundle instanceof PropertyResourceBundle)) {
            return bundle;
        }
        return new UTF8PropertyResourceBundle((PropertyResourceBundle) bundle);
    }

    private static class UTF8PropertyResourceBundle extends ResourceBundle {
        
        private PropertyResourceBundle bundle;

        private UTF8PropertyResourceBundle(final PropertyResourceBundle bundle) {
            this.bundle = bundle;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Enumeration getKeys() {
            return bundle.getKeys();
        }

        @Override
        protected Object handleGetObject(final String key) {
            final String value = bundle.getString(key);
            if (value == null) {
                return null;
            }
            try {
                return new String(value.getBytes("ISO-8859-1"), Constants.UTF8);
            } catch (final UnsupportedEncodingException e) {
                return null;
            }
        }
    }
}