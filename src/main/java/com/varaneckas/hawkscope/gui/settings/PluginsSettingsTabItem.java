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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.gui.SharedStyle;
import com.varaneckas.hawkscope.plugin.Plugin;
import com.varaneckas.hawkscope.plugin.PluginManager;
import com.varaneckas.hawkscope.plugin.PluginTableEditor;
import com.varaneckas.hawkscope.util.PathUtils;

/**
 * Plugin settings {@link TabItem}
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class PluginsSettingsTabItem extends AbstractSettingsTabItem {

    /**
     * Label "Plugin Location"
     */
    private Label pluginLocation;
    
    /**
     * Text for plugin location
     */
    private Text textPluginLocation;
    
    /**
     * Button that opens plugin location;
     */
    private Button openPluginLocation;
    
	/**
	 * Label "Available Plugins"
	 */
	private Label availablePlugins;
	
	/**
	 * Table with {@link Plugin}s
	 */
	private Table tablePlugins;
	
	/**
	 * Plugin Name -> Plugin Object map
	 */
	private final Map<String, Plugin> pluginMap = new HashMap<String, Plugin>();
	
	/**
	 * Initializing constructor 
	 * 
	 * @param folder Settings {@link TabFolder}
	 */
	public PluginsSettingsTabItem(final TabFolder folder) {
		super(folder, "&Plugins");
		
		//Plugin Location
		pluginLocation = addSectionLabel("Plugin Location");
		pluginLocation.setLayoutData(SharedStyle.relativeTo(null, null));
		
		createButtonOpenPluginLocation();
		
		createTextPluginLocation();
		
		//Available Plugins
		availablePlugins = addSectionLabel("Available Plugins");
		availablePlugins.setLayoutData(SharedStyle.relativeTo(
		        textPluginLocation, null));
		
		//Table: Available Plugins
		createTablePlugins();
	}

    private void createButtonOpenPluginLocation() {
        openPluginLocation = addButton("Open");
		final FormData layout = SharedStyle.relativeTo(pluginLocation, 
		        null, null, null);
		layout.bottom = null;
		layout.left = null;
		openPluginLocation.setLayoutData(layout);
		openPluginLocation.addSelectionListener(new SelectionAdapter() {
		   @Override
		    public void widgetSelected(final SelectionEvent ev) {
		       final File dir = new File(PathUtils.unsanitizePath(
		               textPluginLocation.getText()));
		       if (!dir.isDirectory()) {
		           dir.mkdir();
		       }
		       Program.launch(dir.getAbsolutePath());
		    } 
		});
    }

    private void createTextPluginLocation() {
        //Plugin location [            ]
		textPluginLocation = addText(PathUtils.sanitizePath(
		        cfg.getPluginLocation().getAbsolutePath()), 0);
		final FormData layout = ident(SharedStyle.relativeTo(pluginLocation, 
		        openPluginLocation, null, null));
		layout.bottom = null;
		textPluginLocation.setLayoutData(layout);
		textPluginLocation.setToolTipText("Double click to browse");
		textPluginLocation.addListener(SWT.MouseDoubleClick, new Listener() {
            public void handleEvent(final Event event) {
                textPluginLocation.setSelection(0);
                final DirectoryDialog dd = new DirectoryDialog(
                        folder.getShell(), SWT.APPLICATION_MODAL);
                dd.setText("Hawkscope Plugin Location");
                dd.setMessage("Choose a folder where Hawkscope plugins are " +
                		"located");
                dd.setFilterPath(textPluginLocation.getText());
                final String loc = dd.open();
                if (loc != null) {
                    textPluginLocation.setText(PathUtils.sanitizePath(loc));
                }
            }
		});
    }

	/**
	 * Creates the {@link Table}
	 */
	private void createTablePlugins() {
		tablePlugins = new Table(container, SWT.MULTI | SWT.BORDER
                | SWT.SINGLE | SWT.VERTICAL | SWT.HORIZONTAL);
		final FormData layout = ident(SharedStyle.relativeTo(availablePlugins, 
				null, null, null));
		layout.width = 260;
		tablePlugins.setLayoutData(layout);
		tablePlugins.setLinesVisible(true);
		tablePlugins.setHeaderVisible(true);
		
		final TableColumn colEnabled = new TableColumn(tablePlugins, SWT.NONE);
		colEnabled.setText("Enabled");
		
		final TableColumn colPluginName = new TableColumn(tablePlugins, SWT.NONE);
		colPluginName.setText("Plugin");
		
		
		final TableColumn colPluginVersion = new TableColumn(tablePlugins, SWT.NONE);
		colPluginVersion.setText("Version");
		
		final TableColumn colPluginDesc = new TableColumn(tablePlugins, SWT.NONE);
		colPluginDesc.setText("Description");
		colPluginDesc.setWidth(400);
		
		for (final Plugin p : PluginManager.getInstance().getAllPlugins()) {
			log.debug("Addin plugin: " + p.getName());
			pluginMap.put(p.getName(), p);
			final TableItem item = new TableItem(tablePlugins, SWT.NONE);
			item.setText(0, p.isEnabled() ? "Yes" : "No");
			item.setText(1, p.getName());
			item.setText(2, p.getVersion());
			item.setText(3, p.getDescription());
		}
		
		new PluginTableEditor(tablePlugins);
		
		colEnabled.pack();
		colPluginName.pack();
		colPluginVersion.pack();
		colPluginDesc.pack();
	}
	
	@Override
	protected void saveConfiguration() {
		for (final TableItem item : tablePlugins.getItems()) {
			final Plugin p = pluginMap.get(item.getText(1));
			boolean enabled = item.getText(0).equals("Yes");
			p.setEnabled(enabled);
			cfg.getProperties().put("plugin." + p.getClass().getName() 
					+ ".enabled", enabled ? "1" : "0");
		}
	}

}
