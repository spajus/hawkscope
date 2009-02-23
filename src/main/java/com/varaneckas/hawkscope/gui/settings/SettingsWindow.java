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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TabFolder;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.AbstractWindow;
import com.varaneckas.hawkscope.gui.SharedStyle;
import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.plugin.PluginManager;

/**
 * Settings Window
 * +------------------------------------------------------------------------+
 * | {@link #tabFolderSettings}                                             |
 * |                                                                        |
 * |                                {@link #buttonOk} {@link #buttonCancel} |
 * +------------------------------------------------------------------------+
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SettingsWindow extends AbstractWindow {
	
    /**
     * Settings {@link TabFolder}
     * 
     * @see #createTabFolderSettings()
     */
	private TabFolder tabFolderSettings;
	
	/**
	 * Cancel {@link Button}
	 * 
	 * @see #createButtonCancel()
	 */
	private Button buttonCancel;
	
	/**
	 * OK (Save) {@link Button}
	 * 
	 * @see #createButtonOk()
	 */
	private Button buttonOk;
	
	/**
	 * List of settings tabs
	 */
	private final List<AbstractSettingsTabItem> settingsTabs = 
	        new ArrayList<AbstractSettingsTabItem>();
	
	/**
	 * Configuration
	 */
	private final Configuration cfg = ConfigurationFactory
			.getConfigurationFactory().getConfiguration();
	
	@Override
	public void open() {
        if (shell != null && !shell.isDisposed()) {
            shell.setVisible(true);
            shell.forceActive();
            shell.forceFocus();
            return;
        }      		
		createShell("Settings");
		createButtonCancel();
		createButtonOk();
		createTabFolderSettings();
		packAndSetMinSize();
		shell.open();
		shell.forceActive();
		shell.forceFocus();
	}
	
	/**
	 * Creates Settings {@link TabFolder}
	 */
	private void createTabFolderSettings() {
		tabFolderSettings = new TabFolder(shell, SWT.NONE);
		tabFolderSettings.setLayoutData(SharedStyle.relativeTo(null, null, 
				buttonCancel, null));
		tabFolderSettings.setLayout(SharedStyle.LAYOUT);
		settingsTabs.add(new GeneralSettingsTabItem(tabFolderSettings));
		settingsTabs.add(new QuickAccessSettingsTabItem(tabFolderSettings));
		settingsTabs.add(new BlackListSettingsTabItem(tabFolderSettings));
		settingsTabs.add(new NetworkSettingsTabItem(tabFolderSettings));
		settingsTabs.add(new PluginsSettingsTabItem(tabFolderSettings));
		PluginManager.getInstance().enhanceSettings(tabFolderSettings, settingsTabs);
	}
	
	/**
	 * Creates button for closing the window
	 */
	private void createButtonCancel() {
	    buttonCancel = new Button(shell, SWT.PUSH);
	    buttonCancel.setText("&Cancel");
	    buttonCancel.setLayoutData(SharedStyle.relativeToBottomRight(null));
	    buttonCancel.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(final SelectionEvent event) {
	            shell.dispose();
	        } 
	     });
	}
	
	/**
	 * Creates OK button
	 */
	private void createButtonOk() {
	    buttonOk = new Button(shell, SWT.PUSH);
	    buttonOk.setText("&OK");
	    buttonOk.setLayoutData(SharedStyle.relativeToBottomRight(buttonCancel));
	    buttonOk.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                saveConfiguration();
                shell.dispose();
                MenuFactory.getMainMenu().reloadMenu(false);
            }
        });
	}
	
	/**
	 * Saves configuration
	 */
	private void saveConfiguration() {
	    for (final AbstractSettingsTabItem tab : settingsTabs) {
	        tab.saveConfiguration();
	    }
	    ConfigurationFactory.getConfigurationFactory().write(cfg);
	}
	
	/**
	 * Launcher for testing when in development
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		final SettingsWindow w = new SettingsWindow();
		w.open();
	    while (!w.shell.isDisposed()) {
	        if (!w.shell.getDisplay().readAndDispatch())
	          w.shell.getDisplay().sleep();
	      }
	      w.shell.getDisplay().dispose();
	}

}
