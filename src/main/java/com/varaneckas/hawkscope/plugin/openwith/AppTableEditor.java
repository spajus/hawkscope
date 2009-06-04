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
package com.varaneckas.hawkscope.plugin.openwith;

import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.varaneckas.hawkscope.gui.ExecutableInputDialog;
import com.varaneckas.hawkscope.gui.PlainTextTableEditor;
import com.varaneckas.hawkscope.util.Updater;

/**
 * Preferred Application {@link TableEditor}
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class AppTableEditor extends PlainTextTableEditor {

    /**
     * Creates the TableEditor on {@link Table}
     * 
     * @param table Preferred Application {@link Table}
     */
    public AppTableEditor(final Table table) {
    	super(table);
    }

    @Override
    protected boolean doEditing(final TableItem item, final int column, 
			final int row) {
		if (column == 1) {
            new ExecutableInputDialog().open("Please enter executable",
            		item.getText(column), table.getShell(), new Updater() {
                        public void setValue(final String value) {
                            item.setText(column, value);
                            table.getColumn(1).pack();
                        }
                    });
            return true;
        }
		return super.doEditing(item, column, row);
    }
}
