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
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormData;

/**
 * Help Window
 * +------------------------------------------------------------------------+
 * | {@link #browser}                                                       |
 * |                                                                        |
 * |                                                   {@link #buttonClose} |
 * +------------------------------------------------------------------------+
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class HelpWindow extends AbstractWindow {

	/**
	 * Help browser
	 */
	private Browser browser;
	
	@Override
	public void open() {
		if (shell != null && !shell.isDisposed()) {
			shell.setVisible(true);
			shell.forceFocus();
		}
		createShell("Help");
		createButtonClose();
		
		createHelpBrowser();
		
		packAndSetMinSize();
		shell.open();
	}

	/**
	 * Creates the help browser
	 */
	private void createHelpBrowser() {
		browser = new Browser(shell, SWT.BORDER);
		final FormData layout = SharedStyle.relativeTo(null, null, buttonClose, 
				null);
		final Rectangle bounds = shell.getDisplay().getPrimaryMonitor()
			.getBounds();
		layout.width = bounds.width * 3/4;
		layout.height = bounds.height * 3/4;
		browser.setLayoutData(layout);
		browser.setUrl("http://code.google.com/p/hawkscope/wiki/Help");
	}
	
	/**
	 * Launcher for testing when in development
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		final HelpWindow w = new HelpWindow();
		w.open();
	    while (!w.shell.isDisposed()) {
	        if (!w.shell.getDisplay().readAndDispatch())
	          w.shell.getDisplay().sleep();
	      }
	      w.shell.getDisplay().dispose();
	}

}
