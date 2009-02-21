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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.Updater;

/**
 * Dialog for choosing an Executable application
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class ExecutableInputDialog {
    
    /**
     * Opens the dialog
     * 
     * @param prompt Text to be displayed
     * @param defaultApp Default executable
     * @param parent Parent {@link Shell}
     * @param updater Updater for setting the value
     */
    public static void open(final String prompt, final String defaultApp,
             final Shell parent, final Updater updater) {
        final Shell dialog = new Shell(parent, SWT.DIALOG_TRIM 
                | SWT.APPLICATION_MODAL);
        dialog.setImage(IconFactory.getInstance().getUncachedIcon(
                "hawkscope16.png"));
        dialog.setText("Command Input Dialog");
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
        if (defaultApp != null) text.setText(defaultApp);
        FormData data = SharedStyle.relativeTo(label, null, cancel, null);
        data.width = 200;
        data.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
        text.setLayoutData(data);
        
        final Button ok = new Button(dialog, SWT.PUSH);
        ok.setText("&OK");
        ok.setLayoutData(SharedStyle.relativeToBottomRight(cancel));
        ok.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                updater.setValue(text.getText());
                dialog.close();
            }
        });
        
        final Button choose = new Button(dialog, SWT.PUSH);
        choose.setText("&Browse");
        choose.setLayoutData(SharedStyle.relativeToBottomRight(ok));
        choose.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                FileDialog fd = new FileDialog(parent);
                if (defaultApp != null) {
                    fd.setFileName(defaultApp);
                }
                fd.setText("Select executable");
                String app = fd.open();
                if (app != null) {
                    text.setText(app);
                }
            }
        });
        
        dialog.setDefaultButton(ok);
        dialog.setTabList(new Control[] { text, choose, ok, cancel });
        dialog.pack();
        WindowFactory.centerShell(dialog);
        dialog.open();
        dialog.forceFocus();
    }

}
