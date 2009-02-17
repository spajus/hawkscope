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
package com.varaneckas.hawkscope.plugin.openwith;

import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.gui.ExecutableInputDialog;
import com.varaneckas.hawkscope.gui.InputDialog;
import com.varaneckas.hawkscope.gui.SharedStyle;
import com.varaneckas.hawkscope.gui.settings.AbstractSettingsTabItem;
import com.varaneckas.hawkscope.gui.settings.SettingsWindow;
import com.varaneckas.hawkscope.plugin.Plugin;
import com.varaneckas.hawkscope.util.PathUtils;
import com.varaneckas.hawkscope.util.Updater;

/**
 * Open With {@link Plugin}'s {@link SettingsWindow} {@link TabItem}
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class OpenWithSettingsTabItem extends AbstractSettingsTabItem {

    /**
     * Table with .ext -> application that overrides default associations
     */
    private Table tablePreferred;
    
    /**
     * Label: "Special Applications"
     */
    private Label labelSpecialApps;
    
    /**
     * Label: "For directories:"
     */
    private Label labelFolderNav;
    
    /**
     * Text input for folder navigator application
     */
    private Text textFolderNav;
    
    /**
     * Label: "Unknown files:"
     */
    private Label labelFileDef;
    
    /**
     * Text input for unknown file application
     */
    private Text textFileDef;
    
    /**
     * Label: "Preferred Applications"
     */
    private Label labelPreferredApps;
    
    /**
     * Button that adds application to preferred app table
     */
    private Button buttonAdd;
    
    /**
     * Button that removes application from preferred app table
     */
    private Button buttonDel;
    
    /**
     * Creates the {@link TabItem}
     * 
     * @param folder Settings {@link TabFolder}
     */
    public OpenWithSettingsTabItem(final TabFolder folder) {
        super(folder, "Open &With");
        //Special Applications
        labelSpecialApps = addSectionLabel("Special Applications");
        labelSpecialApps.setLayoutData(SharedStyle.relativeTo(null, null));
        
        //For directories:
        labelFolderNav = addLabel("For directories:");
        labelFolderNav.setLayoutData(ident(SharedStyle.relativeTo(labelSpecialApps, null)));
        
        //Uknown files:
        labelFileDef = addLabel("Unknown files:");
        labelFileDef.setLayoutData(ident(SharedStyle.relativeTo(labelFolderNav, null)));
        
        createTextFolderNav();
        createTextFileDef();
        
        //Preferred Application
        labelPreferredApps = addSectionLabel("Preferred Applications");
        labelPreferredApps.setLayoutData(SharedStyle.relativeTo(labelFileDef, null));
       
        createButtonAdd();
        createButtonDel();
        createTablePreferredApps();
    }
    
    /**
     * Creates {@link Button} that deletes items from preferred applications 
     * {@link Table}
     */
    private void createButtonDel() {
        buttonDel = addButton("&-");
        final FormData layout = SharedStyle.relativeTo(buttonAdd, null, null, null);
        layout.width = 30;
        layout.bottom = null;
        layout.left = null;
        buttonDel.setLayoutData(layout);
        buttonDel.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                for (final TableItem s : tablePreferred.getSelection()) {
                    s.dispose();
                }
            }
        });
    }

    /**
     * Creates {@link Button} that adds item to preferred applications 
     * {@link Table}
     */    
    private void createButtonAdd() {
        buttonAdd = addButton("&+");
        final FormData layout = SharedStyle.relativeTo(labelPreferredApps, null, null, null);
        layout.bottom = null;
        layout.left = null;
        layout.width = 30;
        buttonAdd.setLayoutData(layout);
        buttonAdd.addListener(SWT.Selection, new Listener() {
            public void handleEvent(final Event ev) {
                int index = 0;
                if (tablePreferred.getSelectionCount() > 0) {
                    index = tablePreferred.getSelectionIndices()[0];
                }
                tablePreferred.deselectAll();
                addApplication(new TableItem(tablePreferred, SWT.NONE, index));
                tablePreferred.select(index);
                tablePreferred.getVerticalBar().setSelection(0);
            }
        });
        
    }

    /**
     * Creates {@link Table} for storing preferred applications
     * 
     * @see #tablePreferred
     */
    private void createTablePreferredApps() {
        tablePreferred = new Table(container, SWT.MULTI | SWT.BORDER
                | SWT.FULL_SELECTION | SWT.VERTICAL);
        final FormData layout = ident(SharedStyle.relativeTo(
                labelPreferredApps, buttonAdd, null, null));
        layout.width = 250;
        layout.height = 60;
        tablePreferred.setLinesVisible(true);
        tablePreferred.setHeaderVisible(true);
        tablePreferred.setLayoutData(layout);
        final TableColumn colType = new TableColumn(tablePreferred, SWT.NONE);
        colType.setText("Type");
        colType.setWidth(5);
        final TableColumn colApp = new TableColumn(tablePreferred, SWT.NONE);
        colApp.setText("Application");
        // load apps
        for (final String appEntry : cfg.getProperties().keySet()) {
            if (appEntry.startsWith(OpenWithPlugin.PROP_FILE_TYPE_PREFIX)) {
                final TableItem item = new TableItem(tablePreferred, SWT.NONE);
                item.setText(0, appEntry.replaceFirst(
                        OpenWithPlugin.PROP_FILE_TYPE_PREFIX, ""));
                item.setText(1, cfg.getProperties().get(appEntry));
            }
        }
        colType.pack();
        colApp.pack();
        new AppTableEditor(tablePreferred);
    }

    /**
     * Creates input for defining default file application
     */
    private void createTextFileDef() {
        final String fileDef = cfg.getProperties()
                .get(OpenWithPlugin.PROP_UNKNOWN_FILE_APP);
        textFileDef = addText(fileDef == null ? "" : fileDef, 0);
        final FormData layout = SharedStyle.relativeTo(labelFolderNav, 
                null, null, labelSpecialApps);
        layout.bottom = null;
        layout.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
        textFileDef.setLayoutData(layout);
        textFileDef.addListener(SWT.MouseDoubleClick, new Listener() {
            public void handleEvent(final Event ev) {
                ExecutableInputDialog.open(
                        "Choose executable for opening unknown files", 
                        textFileDef.getText(), textFileDef.getShell(), 
                        new Updater() {
                    public void setValue(final String value) {
                        textFileDef.setText(PathUtils.sanitizePath(value));
                    }
                });
            }
        });
        textFileDef.setToolTipText("Double-click to choose an executable");
        
    }

    /**
     * Creates input for defining the folder navigator application
     */
    private void createTextFolderNav() {
        final String folderNav = cfg.getProperties().get(
                OpenWithPlugin.PROP_FOLDER_NAVIGATOR);
        textFolderNav = addText(folderNav == null ? "" : folderNav, 0);
        FormData layout = SharedStyle.relativeTo(labelSpecialApps, 
                null, null, labelSpecialApps);
        layout.bottom = null;
        layout.top.offset += SharedStyle.TEXT_TOP_OFFSET_ADJUST;
        textFolderNav.setLayoutData(layout);
        textFolderNav.addListener(SWT.MouseDoubleClick, new Listener() {
            public void handleEvent(final Event ev) {
                ExecutableInputDialog.open(
                        "Choose executable for opening directories", 
                        textFolderNav.getText(), 
                        textFolderNav.getShell(), new Updater() {
                    public void setValue(final String value) {
                        textFolderNav.setText(PathUtils.sanitizePath(value));
                    }
                });
            }
        });
        textFolderNav.setToolTipText("Double-click to choose an executable");
    }
    
    /**
     * Adds application to {@link TableItem} of {@link #tablePreferred}
     * 
     * @see #tablePreferred
     * @see #createButtonAddApp()
     * @param tableItem TableItem to fill in
     */
    private void addApplication(final TableItem tableItem) {
        InputDialog.open("Please enter file extension", 30,
                tablePreferred.getShell(), new Updater() {
            public void setValue(String ext) {
                if (ext == null || ext.length() == 0) {
                    return;
                }
                if (!ext.startsWith(".")) {
                    ext = "." + ext;
                }
                tableItem.setText(0, ext);
                ExecutableInputDialog.open(
                        "Please enter executable for handling " + ext, null, 
                        tablePreferred.getShell(), new Updater() {
                    public void setValue(final String app) {
                        if (app == null || app.length() == 0) {
                            return;
                        }
                        tableItem.setText(1, app);
                        tablePreferred.getColumn(0).pack();
                        tablePreferred.getColumn(1).pack();
                    }
                });
            }
        });
    }    
    
    @Override
    protected void saveConfiguration() {
     // cleanup
        final List<String> toRemove = new ArrayList<String>();
        for (final String app : cfg.getProperties().keySet()) {
            if (app.startsWith(OpenWithPlugin.PROP_FILE_TYPE_PREFIX)) {
                toRemove.add(app);
            }
        }
        // avoiding concurrent modification
        for (final String app : toRemove) {
            cfg.getProperties().remove(app);
        }
        // then add
        for (final TableItem item : tablePreferred.getItems()) {
            if (item.getText(0) != null && item.getText().length() > 0) {
                if (item.getText(1) != null && item.getText(1).length() > 0) {
                    cfg.getProperties().put(OpenWithPlugin.PROP_FILE_TYPE_PREFIX 
                            + item.getText(0), item.getText(1));
                }
            }
        }
        //unknown file app
        if (textFileDef.getText() != null 
                && textFileDef.getText().length() > 0) {
            cfg.getProperties().put(OpenWithPlugin.PROP_UNKNOWN_FILE_APP, 
                    PathUtils.sanitizePath(textFileDef.getText()));
        }
        //folder navigator
        if (textFolderNav.getText() != null 
                && textFolderNav.getText().length() > 0) {
            cfg.getProperties().put(OpenWithPlugin.PROP_FOLDER_NAVIGATOR, 
                    PathUtils.sanitizePath(textFolderNav.getText()));
        }
        OpenWithPlugin.getInstance().refresh();
    }
}
