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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormData;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;

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
     * Logger
     */
    private static final Log log = LogFactory.getLog(HelpWindow.class);
    
	/**
	 * Help browser
	 */
	private Browser browser;
	
	@Override
	public void open() {
		if (shell != null && !shell.isDisposed()) {
			shell.setVisible(true);
			shell.forceActive();
			shell.forceFocus();
			return;
		}
		createShell("Help");
		createButtonClose();
		
		createHelpBrowser();
		
        shell.pack();
        shell.setMinimumSize(300, 200);
        WindowFactory.centerShell(shell);
		shell.open();
		shell.forceActive();
		shell.forceFocus();
	}

	/**
	 * Creates the help browser
	 */
	private void createHelpBrowser() {
	    final Configuration cfg = ConfigurationFactory
	        .getConfigurationFactory().getConfiguration();
	    if (cfg.isHttpProxyInUse()) {
	        log.debug("Setting proxy");
	        System.setProperty("network.proxy_host", cfg.getHttpProxyHost());
	        System.setProperty("network.proxy_port", "" 
	                + cfg.getHttpProxyPort());
	    }
		browser = new Browser(shell, SWT.BORDER);
		final FormData layout = SharedStyle.relativeTo(null, null, buttonClose, 
				null);
		final Rectangle bounds = shell.getDisplay().getPrimaryMonitor()
			.getBounds();
		layout.width = Math.max(bounds.width * 2/3, 800);
		layout.height = Math.max(bounds.height * 2/3, 600);
		browser.setLayoutData(layout);
		browser.addTitleListener(new TitleListener() {
            public void changed(final TitleEvent ev) {
                browser.setToolTipText("");
            }
		});
		browser.setToolTipText("Loading online help, please wait...");
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
