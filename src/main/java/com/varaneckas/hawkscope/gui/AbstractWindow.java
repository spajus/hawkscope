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
import org.eclipse.swt.widgets.Shell;

import com.varaneckas.hawkscope.tray.TrayManager;
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * Abstract window for creating Hawkscope GUI
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public abstract class AbstractWindow {

	/**
	 * The Shell 
	 * 
	 * @see #createShell()
	 */
	protected Shell shell;
	/**
	 * Button for closing the window
	 * 
	 * @see #createButtonClose()
	 */
	protected Button buttonClose;

	/**
	 * Creates the main {@link Shell}
	 * 
	 * @param title Window title
	 */
	protected void createShell(final String title) {
	    shell = new Shell(TrayManager.getInstance().getShell(), 
	            SWT.SHELL_TRIM | SWT.DOUBLE_BUFFERED);
	    shell.setLocation(shell.getParent().toDisplay(100, 100));
	    shell.setImage(IconFactory.getInstance()
	            .getUncachedIcon("hawkscope16.png"));
	    shell.setText(title);
	    shell.setLayout(SharedStyle.LAYOUT);
	    shell.layout();
	}

	/**
	 * Creates button for closing the window
	 */
	protected void createButtonClose() {
	    buttonClose = new Button(shell, SWT.PUSH);
	    buttonClose.setText("&Close");
	    buttonClose.setLayoutData(SharedStyle.relativeToBottomRight(null));
	    buttonClose.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(final SelectionEvent event) {
	            shell.dispose();
	        } 
	     });
	}
	
	/**
	 * Packs shell and sets the minimum size
	 */
	protected void packAndSetMinSize() {
		shell.pack();
		shell.setMinimumSize(shell.getSize());
		WindowFactory.centerShell(shell);
	}
	
	/**
	 * Open the window
	 */
	abstract public void open();

}