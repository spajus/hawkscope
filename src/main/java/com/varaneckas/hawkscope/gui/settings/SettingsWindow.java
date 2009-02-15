package com.varaneckas.hawkscope.gui.settings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TabFolder;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.AbstractWindow;
import com.varaneckas.hawkscope.gui.SharedStyle;
import com.varaneckas.hawkscope.plugin.PluginManager;

public class SettingsWindow extends AbstractWindow {
	
	private TabFolder tabFolderSettings;
	
	private final Configuration cfg = ConfigurationFactory
			.getConfigurationFactory().getConfiguration();
	
	@Override
	public void open() {
		createShell("Settings");
		createButtonClose();
		createTabFolderSettings();
		createButtonCancel();
		packAndSetMinSize();
		shell.open();
	}
	
	private void createTabFolderSettings() {
		tabFolderSettings = new TabFolder(shell, SWT.NONE);
		tabFolderSettings.setLayoutData(SharedStyle.relativeTo(null, null, 
				buttonClose, null));
		tabFolderSettings.setLayout(SharedStyle.LAYOUT);
		new GeneralSettingsTabItem(tabFolderSettings);
		new QuickAccessSettingsTabItem(tabFolderSettings);
		new BlackListSettingsTabItem(tabFolderSettings);
		new NetworkSettingsTabItem(tabFolderSettings);
		PluginManager.getInstance().enhanceSettings(cfg, tabFolderSettings);
	}
	
	/**
	 * Creates button for closing the window
	 */
	protected void createButtonCancel() {
	    final Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    cancel.setLayoutData(SharedStyle.relativeToBottomRight(buttonClose));
	    cancel.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(final SelectionEvent event) {
	            shell.dispose();
	        } 
	     });
	}
	
	public static void main(String[] args) {
		SettingsWindow w = new SettingsWindow();
		w.open();
		while (w.shell.getDisplay().readAndDispatch()) {
			w.shell.getDisplay().sleep();
		}
	}

}
