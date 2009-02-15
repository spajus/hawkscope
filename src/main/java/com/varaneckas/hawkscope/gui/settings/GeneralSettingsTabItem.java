package com.varaneckas.hawkscope.gui.settings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.gui.SharedStyle;


public class GeneralSettingsTabItem extends AbstractSettingsTabItem {
	
	private Label updates;
	
	private Button checkForUpdates;
	
	private Label menu;
	
	private Label reloadDelaySec;
	
	private Text reloadDelayInput;
	
	private Button useOsIcons;
	
	public GeneralSettingsTabItem(final TabFolder folder) {
		super(folder, "&General");
		createUpdatesSection();
		createMenuSection();
	}
	
	/**
	 * Creates the "Updates" section
	 */
	private void createUpdatesSection() {
		//Updates
	    updates = addSectionLabel("Updates");
		updates.setLayoutData(SharedStyle.relativeTo(null, null));	
		
		//Check for updates
		checkForUpdates = 
			addCheckbox("Check for &updates on startup");
		checkForUpdates.setToolTipText("Should Hawkscope check for " +
				"updates on startup? Please mind the HTTP Proxy settings " +
				"in Network tab!");
		checkForUpdates.setLayoutData(ident(SharedStyle
				.relativeTo(updates, null)));
		checkForUpdates.setSelection(cfg.checkForUpdates());
	}
	
	private void createMenuSection() {
	    menu = addSectionLabel("Menu");
		menu.setLayoutData(SharedStyle.relativeTo(checkForUpdates, null));
		
		reloadDelaySec = addLabel("Reload delay (seconds):");
		reloadDelaySec.setLayoutData(ident(SharedStyle.relativeTo(menu, null)));
		
		createReloadDelayInput();

		useOsIcons = addCheckbox("Use operating system icons");
		useOsIcons.setLayoutData(ident(SharedStyle
				.relativeTo(reloadDelayInput, null)));
		useOsIcons.setSelection(cfg.useOsIcons());
	}

	private void createReloadDelayInput() {
		final String reloadDelay = "" + (cfg.getMenuReloadDelay() / 1000.0);
		reloadDelayInput = addText(reloadDelay, 4);
		reloadDelayInput.setLayoutData(ident(SharedStyle.relativeTo(menu, reloadDelaySec)));
		reloadDelayInput.addListener(SWT.FocusOut, new Listener() {
            public void handleEvent(final Event event) {
                try {
                    double d = Double.valueOf(reloadDelayInput.getText());
                    if (d <= 0) {
                    	reloadDelayInput.setText("0.1");
                    }
                    if (d > 9999) {
                    	reloadDelayInput.setText("9999");
                    }
                } catch (final Exception e) {
                	reloadDelayInput.setText(reloadDelay);
                }
            }
        });
	}
	
}
