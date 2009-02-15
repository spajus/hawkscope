package com.varaneckas.hawkscope.gui.settings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public abstract class AbstractSettingsTabItem {

	protected final TabFolder folder;

	protected final TabItem item;
	
	public AbstractSettingsTabItem(final TabFolder folder, final String title) {
		this.folder = folder;
		item = new TabItem(folder, SWT.NONE);
		item.setText(title);
	}
	
}
