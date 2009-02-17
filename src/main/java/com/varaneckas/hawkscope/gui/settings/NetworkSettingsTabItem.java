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
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.gui.SharedStyle;


public class NetworkSettingsTabItem extends AbstractSettingsTabItem {
	
    private Label httpProxy;
    
    private Button useHttpProxy;
    
    private Button useHttpProxyAuth;
    
    private Label labelHttpProxyHost;

    private Label labelHttpProxyPort;
    
    private Text httpProxyHost;
    
    private Text httpProxyPort;
    
    private Label labelUsername;
    
    private Label labelPassword;
    
    private Text httpProxyUser;
    
    private Text httpProxyPass;
    
    
	public NetworkSettingsTabItem(final TabFolder folder) {
		super(folder, "&Network");
		
		//HTTP Proxy
		httpProxy = addSectionLabel("HTTP Proxy");
		httpProxy.setLayoutData(SharedStyle.relativeTo(null, null));
		
		//[] Use HTTP Proxy
		createCheckUseHttpProxy();
		
		//HTTP Proxy Host:
		labelHttpProxyHost = addLabel("HTTP Proxy Host:");
		labelHttpProxyHost.setLayoutData(ident(SharedStyle.relativeTo(useHttpProxy, null)));
		
		//HTTP Proxy Port:
		labelHttpProxyPort = addLabel("HTTP Proxy Port:");
		labelHttpProxyPort.setLayoutData(ident(SharedStyle.relativeTo(labelHttpProxyHost, null)));
		
		//[] Enable Authentication
		createCheckUseHttpProxyAuth();
		
		//Http Proxy Host [           ]
		createInputHttpProxyHost();
		
		//Http Proxy Port [           ]
		createInputHttpProxyPort();
		
		//Username:
		labelUsername = addLabel("Username:");
		labelUsername.setLayoutData(ident(SharedStyle.relativeTo(useHttpProxyAuth, null)));

		//Password:
		labelPassword = addLabel("Password:");
		labelPassword.setLayoutData(ident(SharedStyle.relativeTo(labelUsername, null)));
		
		//Username: [       ]
		createInputAuthUsername();
		
		//Password: [       ]
		createInputAuthPassword();
	}

    private void createCheckUseHttpProxyAuth() {
        useHttpProxyAuth = addCheckbox("Enable Authentication");
		useHttpProxyAuth.setLayoutData(ident(SharedStyle.relativeTo(labelHttpProxyPort, null)));
		useHttpProxyAuth.setSelection(cfg.isHttpProxyAuthInUse());
		useHttpProxyAuth.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                httpProxyUser.setEnabled(useHttpProxy.getSelection() 
                        && useHttpProxyAuth.getSelection());
                httpProxyPass.setEnabled(useHttpProxy.getSelection() 
                        && useHttpProxyAuth.getSelection());
            }
        });          
    }

    private void createCheckUseHttpProxy() {
        useHttpProxy = addCheckbox("&Use HTTP Proxy");
		useHttpProxy.setLayoutData(ident(SharedStyle.relativeTo(httpProxy, null)));
		useHttpProxy.setSelection(cfg.isHttpProxyInUse());
		useHttpProxy.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
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

    private void createInputAuthPassword() {
        httpProxyPass = addText(cfg.getHttpProxyAuthPassword(), 255);
        FormData layout = SharedStyle.relativeTo(labelUsername, null, null, useHttpProxyAuth);
        layout.bottom = null;
        layout.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
        httpProxyPass.setLayoutData(layout);
        httpProxyPass.setEnabled(cfg.isHttpProxyInUse() 
                && cfg.isHttpProxyAuthInUse());
        httpProxyPass.setEchoChar('*');
    }

    private void createInputAuthUsername() {
        httpProxyUser = addText(cfg.getHttpProxyAuthUsername(), 255);
        FormData layout = SharedStyle.relativeTo(useHttpProxyAuth, 
                null, null, useHttpProxyAuth);
        layout.bottom = null;
        layout.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
        httpProxyUser.setLayoutData(layout);
        httpProxyUser.setEnabled(cfg.isHttpProxyInUse() 
                && cfg.isHttpProxyAuthInUse());
    }

    private void createInputHttpProxyPort() {
        httpProxyPort = addText("" + cfg.getHttpProxyPort(), 5);
		FormData layout = SharedStyle.relativeTo(labelHttpProxyHost, null, null, useHttpProxyAuth);
		layout.bottom = null;
		layout.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
		layout.right = null;
		layout.width = 50;
		httpProxyPort.setLayoutData(layout);
		httpProxyPort.setEnabled(cfg.isHttpProxyInUse());
		httpProxyPort.addListener(SWT.FocusOut, new Listener() {
            public void handleEvent(Event event) {
                try {
                    int s = Integer.valueOf(httpProxyPort.getText());
                    if (s <= 0) {
                        httpProxyPort.setText("1");
                    }
                    if (s > Short.MAX_VALUE) {
                        httpProxyPort.setText("" + Short.MAX_VALUE);
                    }
                } catch (final Exception e) {
                    httpProxyPort.setText("" + cfg.getHttpProxyPort());
                }
            }
        });
    }

    private void createInputHttpProxyHost() {
        httpProxyHost = addText(cfg.getHttpProxyHost(), 255);
		FormData layout = SharedStyle.relativeTo(useHttpProxy, null, null, useHttpProxyAuth);
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
        cfg.getProperties().put(Configuration.HTTP_PROXY_AUTH_PASSWORD,
                httpProxyPass.getText());
    }

}
