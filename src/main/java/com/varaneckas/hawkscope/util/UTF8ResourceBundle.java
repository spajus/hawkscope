package com.varaneckas.hawkscope.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

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
            ResourceBundle bundle) {
        if (!(bundle instanceof PropertyResourceBundle))
            return bundle;
        return new UTF8PropertyResourceBundle((PropertyResourceBundle) bundle);
    }

    private static class UTF8PropertyResourceBundle extends ResourceBundle {
        
        private PropertyResourceBundle bundle;

        private UTF8PropertyResourceBundle(PropertyResourceBundle bundle) {
            this.bundle = bundle;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Enumeration getKeys() {
            return bundle.getKeys();
        }

        @Override
        protected Object handleGetObject(final String key) {
            String value = bundle.getString(key);
            if (value == null)
                return null;
            try {
                return new String(value.getBytes("ISO-8859-1"), "UTF-8");
            } catch (final UnsupportedEncodingException e) {
                return null;
            }
        }
    }
}