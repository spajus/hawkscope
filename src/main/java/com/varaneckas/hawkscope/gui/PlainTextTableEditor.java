package com.varaneckas.hawkscope.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;


public class PlainTextTableEditor extends BasicTableEditor {

    /**
     * Creates the TableEditor on {@link Table}
     * 
     * @param table A {@link Table}
     */    
    public PlainTextTableEditor(final Table table) {
        super(table);
    }

    @Override
    protected boolean doEditing(final TableItem item, final int column, 
            final int row) {
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
                        item.setText(column, text.getText());
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
        editor.setEditor(text, item, row);
        text.setText(item.getText(row));
        text.selectAll();
        text.setFocus();
        return true;
    }

}
