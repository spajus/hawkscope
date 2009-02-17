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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.Updater;

/**
 * Dialog with text input
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class InputDialog {
    
    /**
     * Opens the dialog 
     * 
     * @param prompt Prompt text
     * @param maxLength maximum length of the input
     * @param parent Parent {@link Shell}
     * @param updater Updater for the value
     */
    public static void open(final String prompt, final int maxLength, 
            final Shell parent, final Updater updater) {
        final Shell dialog = new Shell(parent, SWT.DIALOG_TRIM 
                | SWT.APPLICATION_MODAL);
        dialog.setImage(IconFactory.getInstance().getUncachedIcon(
                "hawkscope16.png"));
        dialog.setText("Input Dialog");
        dialog.setLayout(SharedStyle.LAYOUT);

        final Label label = new Label(dialog, SWT.NONE);
        label.setText(prompt);
        label.setLayoutData(SharedStyle.relativeTo(null, null));

        final Button cancel = new Button(dialog, SWT.PUSH);
        cancel.setText("&Cancel");
        cancel.setLayoutData(SharedStyle.relativeToBottomRight(null));
        cancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) { 
                dialog.close();
            }
        });

        final Text text = new Text(dialog, SWT.BORDER);
        final FormData data = SharedStyle.relativeTo(null, null, cancel, label);
        data.width = Math.min(10 * maxLength, 100);
        data.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
        text.setLayoutData(data);
        text.setTextLimit(maxLength);
        
        final Button ok = new Button(dialog, SWT.PUSH);
        ok.setText("&OK");
        ok.setLayoutData(SharedStyle.relativeToBottomRight(cancel));
        ok.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                updater.setValue(text.getText());
                dialog.close();
            }
        });
        dialog.setDefaultButton(ok);
        dialog.setTabList(new Control[] { text, ok, cancel });
        dialog.pack();
        dialog.open();
    }

}
