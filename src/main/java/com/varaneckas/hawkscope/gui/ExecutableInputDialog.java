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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.varaneckas.hawkscope.util.Updater;

/**
 * Dialog for choosing an Executable application
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class ExecutableInputDialog extends InputDialog {
    
	/**
	 * The choose button
	 */
	protected Button choose;
	
    /**
     * Opens the dialog
     * 
     * @param prompt Text to be displayed
     * @param defaultApp Default executable
     * @param parent Parent {@link Shell}
     * @param updater Updater for setting the value
     */
    public void open(final String prompt, final String defaultApp,
             final Shell parent, final Updater updater) {
        
    	createDialogShell(parent);
    	dialog.setText("Command Input Dialog");
    	
    	createLabel(prompt);

    	createButtonCancel();
    	
    	createTextInput(0, 200);
    	if (defaultApp != null) {
    		text.setText(defaultApp);
    	}
        
    	createButtonOk(updater);
    	
        createButtonChoose(defaultApp, parent);
        
        runDialog();
        dialog.setTabList(new Control[] { text, choose, ok, cancel});
    }

    /**
     * Creates a choose file button
     * 
     * @param defaultApp
     * @param parent
     */
	protected void createButtonChoose(final String defaultApp,
			final Shell parent) {
		choose = new Button(dialog, SWT.PUSH);
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
	}

}
