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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.gui.ExecutableInputDialog;
import com.varaneckas.hawkscope.util.Updater;

/**
 * Preferred Application {@link TableEditor}
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class AppTableEditor {

    /**
     * Creates the TableEditor on {@link Table}
     * 
     * @param table Preferred Application {@link Table}
     */
    public AppTableEditor(final Table table) {
        final TableEditor editor = new TableEditor(table);
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
                            // executable
                            if (column == 1) {
                                ExecutableInputDialog.open(
                                        "Please enter executable", item
                                                .getText(column), table
                                                .getShell(), new Updater() {
                                            public void setValue(String value) {
                                                item.setText(column, value);
                                                table.getColumn(1).pack();
                                            }
                                        });
                                return;
                            }
                            final Text text = new Text(table, SWT.NONE);
                            final Listener textListener = new Listener() {
                                public void handleEvent(final Event e) {
                                    switch (e.type) {
                                    case SWT.FocusOut:
                                        item.setText(column, text.getText());
                                        text.dispose();
                                        break;
                                    case SWT.Traverse:
                                        switch (e.detail) {
                                        case SWT.TRAVERSE_RETURN:
                                            item
                                                    .setText(column, text
                                                            .getText());
                                            // FALL THROUGH
                                        case SWT.TRAVERSE_ESCAPE:
                                            text.dispose();
                                            e.doit = false;
                                        }
                                        break;
                                    }
                                    table.getColumn(0).pack();
                                }
                            };
                            text.addListener(SWT.FocusOut, textListener);
                            text.addListener(SWT.Traverse, textListener);
                            editor.setEditor(text, item, i);
                            text.setText(item.getText(i));
                            text.selectAll();
                            text.setFocus();
                            return;
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
}
