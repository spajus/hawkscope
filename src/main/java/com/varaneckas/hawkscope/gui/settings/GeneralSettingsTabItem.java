package com.varaneckas.hawkscope.gui.settings;

import org.eclipse.swt.SWT;
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
 * General Settings {@link TabItem}
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
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
		checkForUpdates.setToolTipText("Should Hawkscope check for updates " +
			"on startup? Please mind the HTTP Proxy settings in Network tab!");
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
		useOsIcons.setToolTipText("Let Hawkscope look for Operating " +
        "System icons?");
	}

	private void createReloadDelayInput() {
		final String reloadDelay = "" + (cfg.getMenuReloadDelay() / 1000.0);
		reloadDelayInput = addText(reloadDelay, 4);
		final FormData style = ident(SharedStyle.relativeTo(menu, reloadDelaySec));
		style.width = 40;
		//up a little, to center with reloadDelaySec
		style.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
		reloadDelayInput.setLayoutData(style);
		reloadDelayInput.setToolTipText("After how many seconds " +
                "of being idle Hawkscope should reload it's main menu " +
                "(to find new devices, refresh Quick Access List, " +
                "re-apply Blacklist etc.)? The reload is fast unless you " +
                "have got many slow network drives.");
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

    @Override
    protected void saveConfiguration() {
        cfg.getProperties().put(Configuration.CHECK_FOR_UPDATES, 
                checkForUpdates.getSelection() ? "1" : "0");
        cfg.getProperties().put(Configuration.MENU_RELOAD_DELAY, "" + 
                Math.round(Double.valueOf(reloadDelayInput.getText()) * 1000));
        cfg.getProperties().put(Configuration.USE_OS_ICONS, 
                useOsIcons.getSelection() ? "1" : "0");
    }
	
}
