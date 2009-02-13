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
	 */
	protected void createShell(final String title, int minWidth, int minHeight) {
	    shell = new Shell(TrayManager.getInstance().getShell(), SWT.SHELL_TRIM);
	    shell.setMinimumSize(minWidth, minHeight);
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
	 * Open the window
	 */
	abstract public void open();

}