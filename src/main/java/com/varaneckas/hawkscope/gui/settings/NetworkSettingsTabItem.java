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
package com.varaneckas.hawkscope.gui.settings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.gui.SharedStyle;

/**
 * Network Settings {@link TabItem}
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class NetworkSettingsTabItem extends AbstractSettingsTabItem {
	
    /**
     * Label "HTTP Proxy"
     */
    private Label httpProxy;
    
    /**
     * Checkbox "[] Use HTTP Proxy"
     */
    private Button useHttpProxy;
    
    /**
     * Checkbox "[] Enable Authentication"
     */
    private Button useHttpProxyAuth;
    
    /**
     * Label "HTTP Proxy Host"
     */
    private Label labelHttpProxyHost;

    /**
     * Label "HTTP Proxy Port"
     */
    private Label labelHttpProxyPort;
    
    /**
     * Input for HTTP proxy host
     */
    private Text httpProxyHost;
    
    /**
     * Input for HTTP proxy port
     */
    private Text httpProxyPort;
    
    /**
     * Label "Username"
     */
    private Label labelUsername;
    
    /**
     * Label "Password"
     */
    private Label labelPassword;
    
    /**
     * Input for HTTP proxy auth username
     */
    private Text httpProxyUser;
    
    /**
     * Input for HTTP proxy auth password
     */
    private Text httpProxyPass;
    
    /**
     * Creates the Network Settings {@link TabItem}
     * 
     * @param folder Settings {@link TabFolder}
     */
	public NetworkSettingsTabItem(final TabFolder folder) {
		super(folder, "&Network");
		
		//HTTP Proxy
		httpProxy = addSectionLabel("HTTP Proxy");
		httpProxy.setLayoutData(SharedStyle.relativeTo(null, null));
		
		//[] Use HTTP Proxy
		createCheckUseHttpProxy();
		
		//HTTP Proxy Host:
		labelHttpProxyHost = addLabel("HTTP Proxy Host:");
		labelHttpProxyHost.setLayoutData(ident(SharedStyle.relativeTo(
		        useHttpProxy, null)));
		
		//HTTP Proxy Port:
		labelHttpProxyPort = addLabel("HTTP Proxy Port:");
		labelHttpProxyPort.setLayoutData(ident(SharedStyle.relativeTo(
		        labelHttpProxyHost, null)));
		
		//[] Enable Authentication
		createCheckUseHttpProxyAuth();
		
		//Http Proxy Host [           ]
		createInputHttpProxyHost();
		
		//Http Proxy Port [           ]
		createInputHttpProxyPort();
		
		//Username:
		labelUsername = addLabel("Username:");
		labelUsername.setLayoutData(ident(SharedStyle.relativeTo(
		        useHttpProxyAuth, null)));

		//Password:
		labelPassword = addLabel("Password:");
		labelPassword.setLayoutData(ident(SharedStyle.relativeTo(
		        labelUsername, null)));
		
		//Username: [       ]
		createInputAuthUsername();
		
		//Password: [       ]
		createInputAuthPassword();
	}

	/**
	 * Creates checkbox "[] Enable Authentication"
	 */
    private void createCheckUseHttpProxyAuth() {
        useHttpProxyAuth = addCheckbox("Enable &Authentication");
		useHttpProxyAuth.setLayoutData(ident(SharedStyle.relativeTo(
		        labelHttpProxyPort, null)));
		useHttpProxyAuth.setEnabled(cfg.isHttpProxyInUse());
		useHttpProxyAuth.setSelection(cfg.isHttpProxyAuthInUse());
		useHttpProxyAuth.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                httpProxyUser.setEnabled(useHttpProxy.getSelection() 
                        && useHttpProxyAuth.getSelection());
                httpProxyPass.setEnabled(useHttpProxy.getSelection() 
                        && useHttpProxyAuth.getSelection());
            }
        });          
    }

    /**
     * Creates checkbox "[] Use HTTP Proxy"
     */
    private void createCheckUseHttpProxy() {
        useHttpProxy = addCheckbox("&Use HTTP Proxy");
		useHttpProxy.setLayoutData(ident(SharedStyle.relativeTo(
		        httpProxy, null)));
		useHttpProxy.setSelection(cfg.isHttpProxyInUse());
		useHttpProxy.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                httpProxyHost.setEnabled(useHttpProxy.getSelection());
                httpProxyPort.setEnabled(useHttpProxy.getSelection());
                useHttpProxyAuth.setEnabled(useHttpProxy.getSelection());
                httpProxyUser.setEnabled(useHttpProxy.getSelection() 
                        && useHttpProxyAuth.getSelection());
                httpProxyPass.setEnabled(useHttpProxy.getSelection() 
                        && useHttpProxyAuth.getSelection());
            }
        });
    }

    /**
     * Creates auth password input
     */
    private void createInputAuthPassword() {
        httpProxyPass = addText(cfg.getHttpProxyAuthPassword(), 255);
        final FormData layout = SharedStyle.relativeTo(labelUsername, null, 
                null, useHttpProxyAuth);
        layout.bottom = null;
        layout.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
        httpProxyPass.setLayoutData(layout);
        httpProxyPass.setEnabled(cfg.isHttpProxyInUse() 
                && cfg.isHttpProxyAuthInUse());
        httpProxyPass.setEchoChar('*');
    }

    /**
     * Creates auth username input
     */
    private void createInputAuthUsername() {
        httpProxyUser = addText(cfg.getHttpProxyAuthUsername(), 255);
        final FormData layout = SharedStyle.relativeTo(useHttpProxyAuth, 
                null, null, useHttpProxyAuth);
        layout.bottom = null;
        layout.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
        httpProxyUser.setLayoutData(layout);
        httpProxyUser.setEnabled(cfg.isHttpProxyInUse() 
                && cfg.isHttpProxyAuthInUse());
    }

    /**
     * Creates HTTP proxy port input
     */
    private void createInputHttpProxyPort() {
        httpProxyPort = addText(String.valueOf(cfg.getHttpProxyPort()), 5);
		final FormData layout = SharedStyle.relativeTo(labelHttpProxyHost, 
		        null, null, useHttpProxyAuth);
		layout.bottom = null;
		layout.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
		layout.right = null;
		layout.width = 50;
		httpProxyPort.setLayoutData(layout);
		httpProxyPort.setEnabled(cfg.isHttpProxyInUse());
		httpProxyPort.addListener(SWT.FocusOut, new Listener() {
            public void handleEvent(final Event event) {
                try {
                    final int s = Integer.valueOf(httpProxyPort.getText());
                    if (s <= 0) {
                        httpProxyPort.setText("1");
                    }
                    if (s > Short.MAX_VALUE) {
                        httpProxyPort.setText(String.valueOf(Short.MAX_VALUE));
                    }
                } catch (final Exception e) {
                    httpProxyPort.setText(String.valueOf(cfg.getHttpProxyPort()));
                }
            }
        });
    }

    /**
     * Creates HTTP proxy host input
     */
    private void createInputHttpProxyHost() {
        httpProxyHost = addText(cfg.getHttpProxyHost(), 255);
		final FormData layout = SharedStyle.relativeTo(useHttpProxy, 
		        null, null, useHttpProxyAuth);
		layout.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
		layout.bottom = null;
		httpProxyHost.setLayoutData(layout);
		httpProxyHost.setEnabled(cfg.isHttpProxyInUse());
    }

    @Override
    protected void saveConfiguration() {
        cfg.getProperties().put(Configuration.HTTP_PROXY_USE, 
                useHttpProxy.getSelection() ? "1" : "0");
        cfg.getProperties().put(Configuration.HTTP_PROXY_HOST, 
                httpProxyHost.getText());
        cfg.getProperties().put(Configuration.HTTP_PROXY_PORT, 
                httpProxyPort.getText());
        cfg.getProperties().put(Configuration.HTTP_PROXY_AUTH_USE,
                useHttpProxyAuth.getSelection() ? "1" : "0");
        cfg.getProperties().put(Configuration.HTTP_PROXY_AUTH_USERNAME,
                httpProxyUser.getText());
        cfg.setPasswordProperty(Configuration.HTTP_PROXY_AUTH_PASSWORD,
                httpProxyPass.getText());
    }

}
