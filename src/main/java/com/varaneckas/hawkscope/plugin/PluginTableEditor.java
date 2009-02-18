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

import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.varaneckas.hawkscope.gui.BasicTableEditor;

/**
 * Plugin {@link TableEditor}
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class PluginTableEditor extends BasicTableEditor {

    /**
     * Creates the TableEditor on {@link Table}
     * 
     * @param table Preferred Application {@link Table}
     */
    public PluginTableEditor(final Table table) {
       super(table);
    }

    protected boolean doEditing(final TableItem item, final int column, final int row) {
    	if (column == 0) {
    		if (item.getText(0).equals("Yes")) {
    			item.setText(0, "No");
    			item.setGrayed(true);
    		} else {
    			item.setText(0, "Yes");
    			item.setGrayed(false);
    		}
    		return true;
    	} else {
    		return false;
    	}
    }
}
