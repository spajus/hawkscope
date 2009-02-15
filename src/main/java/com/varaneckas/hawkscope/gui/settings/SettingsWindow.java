package com.varaneckas.hawkscope.gui.settings;

import org.eclipse.swt.SWT;
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
		packAndSetMinSize();
		shell.open();
	}
	
	public void createTabFolderSettings() {
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
	
	
	public static void main(String[] args) {
		SettingsWindow w = new SettingsWindow();
		w.open();
		while (w.shell.getDisplay().readAndDispatch()) {
			w.shell.getDisplay().sleep();
		}
	}

}
