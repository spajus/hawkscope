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
package com.varaneckas.hawkscope.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * Basic {@link TableEditor}
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class BasicTableEditor {
	
	/**
	 * Logger
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Target table
	 */
	protected final Table table;
	
	/**
	 * Table Editor
	 */
	protected final TableEditor editor;

	/**
	 * Constructor that initializes the editor
	 * 
	 * @param table target
	 */
	public BasicTableEditor(final Table table) {
		this.table = table;		
		editor = new TableEditor(table);
		createEditor();
	}
	
	/**
	 * Creates the editor
	 */
	private void createEditor() {
        editor.horizontalAlignment = SWT.LEFT;
        editor.grabHorizontal = true;
        table.addListener(SWT.MouseDoubleClick, new Listener() {
            public void handleEvent(Event event) {
                Rectangle clientArea = table.getClientArea();
                Point pt = new Point(event.x, event.y);
                int index = table.getTopIndex();
                while (index < table.getItemCount()) {
                    boolean visible = false;
                    final TableItem item = table.getItem(index);
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        Rectangle rect = item.getBounds(i);
                        if (rect.contains(pt)) {
                            final int column = i;
                            try {
	                            if (doEditing(item, column, i)) {
	                            	return;
	                            }
                            } catch (final Exception e) {
                            	log.warn("Failure in editing", e);
                            }
                        }
                        if (!visible && rect.intersects(clientArea)) {
                            visible = true;
                        }
                    }
                    if (!visible)
                        return;
                    index++;
                }
            }

        });		
	}	

	/**
	 * Implement this to handle your own editing logic
	 * 
	 * @param item active {@link TableItem}
	 * @param column active column
	 * @param row active row
	 * @return done editing?
	 */
	protected abstract boolean doEditing(final TableItem item, final int column, 
			final int row);

}