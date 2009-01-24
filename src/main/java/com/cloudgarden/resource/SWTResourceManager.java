package com.cloudgarden.resource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;

/**
 * Class to manage SWT resources (Font, Color, Image and Cursor)
 * There are no restrictions on the use of this code.
 *
 * You may change this code and your changes will not be overwritten,
 * but if you change the version number below then this class will be
 * completely overwritten by Jigloo.
 * #SWTResourceManager:version4.0.0#
 * 
 * @author Original Jigloo authors
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTResourceManager {

    /**
     * Registered resources in use
     */
    private static HashMap<String, Object> resources = 
            new HashMap<String, Object>();
    
    /**
     * Resource users
     */
	private static List<Object> users = new LinkedList<Object>();

	/**
	 * Widget dispose listener
	 */
	private static DisposeListener disposeListener = new DisposeListener() {
		public void widgetDisposed(DisposeEvent e) {
			users.remove(e.getSource());
			if (users.size() == 0)
				dispose();
		}
	};

	/**
	 * This method should be called by *all* Widgets which use resources
	 * provided by this SWTResourceManager. When widgets are disposed,
	 * they are removed from the "users" Vector, and when no more
	 * registered Widgets are left, all resources are disposed.
	 * <P>
	 * If this method is not called for all Widgets then it should not be called
	 * at all, and the "dispose" method should be explicitly called after all
	 * resources are no longer being used.
	 */
	public static void registerResourceUser(Widget widget) {
		if (users.contains(widget))
			return;
		users.add(widget);
		widget.addDisposeListener(disposeListener);
	}

	/**
	 * Disposes unused resources
	 */
	public static void dispose() {
		Iterator<String> it = resources.keySet().iterator();
		while (it.hasNext()) {
			Object resource = resources.get(it.next());
			if (resource instanceof Font)
				 ((Font) resource).dispose();
			else if (resource instanceof Color)
				 ((Color) resource).dispose();
			else if (resource instanceof Image)
				 ((Image) resource).dispose();
			else if (resource instanceof Cursor)
				 ((Cursor) resource).dispose();
		}
		resources.clear();
	}

	/**
	 * Gets a SWT font
	 * 
	 * @param name Font name
	 * @param size Font size
	 * @param style Font style
	 * @return
	 */
	public static Font getFont(final String name, final int size, 
	        final int style) {
        final String fontName = name + "|" + size + "|" + style;
        if (resources.containsKey(fontName))
            return (Font) resources.get(fontName);
        final FontData fd = new FontData(name, size, style);
        final Font font = new Font(Display.getDefault(), fd);
        resources.put(fontName, font);
        return font;
	}
	
	/**
	 * Workaround for Jigloo plugin
	 */
	public static Font getFont(final String name, final int size, 
	        final int style, final boolean a, final boolean b) {
	    return getFont(name, size, style);
	}

}
