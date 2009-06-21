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
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.gui.PlainTextTableEditor;
import com.varaneckas.hawkscope.gui.SharedStyle;

/**
 * Quick Access List Settings {@link TabItem}
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class QuickAccessSettingsTabItem extends AbstractSettingsTabItem {
    
    /**
     * Label "Quick Access Locations"
     */
    private Label labelQuickAccessLoc;
    
    /**
     * Quick Access {@link Table}
     */
    private Table tableQuickAccess;
    
    /**
     * Button that adds a Quick Access item
     */
    private Button buttonAddQa;
    
    /**
     * Button that deletes a Quick Access item
     */
    private Button buttonDelQa;
    
    /**
     * Button that moves a Quick Access item up
     */
    private Button buttonUpQa;
    
    /**
     * Button that moves a Quick Access item down
     */
    private Button buttonDnQa;
    
    /**
     * Creates the Quick Access settings {@link TabItem}
     * 
     * @param folder Settings {@link TabFolder}
     */
	public QuickAccessSettingsTabItem(final TabFolder folder) {
		super(folder, "&Quick Access");
		
		//Quick Access Locations
		labelQuickAccessLoc = addSectionLabel("Quick Access Locations");
		labelQuickAccessLoc.setLayoutData(SharedStyle.relativeTo(null, null));
		
		createButtonAddQa();
		createButtonDelQa();
		createTableQuickAccess();
		createButtonDnQa();
		createButtonUpQa();
	}

    /**
	 * Creates {@link Button} that adds a Quick Access item
	 */
    private void createButtonAddQa() {
        buttonAddQa = addButton("&+");
        final FormData layout = SharedStyle.relativeTo(labelQuickAccessLoc, 
                null, null, null);
        layout.bottom = null;
        layout.left = null;
        layout.width = SharedStyle.BUTTON_MIN_WIDTH;
        buttonAddQa.setLayoutData(layout);
        buttonAddQa.setToolTipText("Add a Quick Access item");
        buttonAddQa.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
               final DirectoryDialog fd = new DirectoryDialog(folder.getShell(), 
                       SWT.OPEN);
               folder.getShell().setEnabled(false);
               fd.setMessage("Find a folder to add");
               fd.setText("Add to Quick Access List");
               final String item = fd.open();
               if (item != null) {
                   TableItem newItem = new TableItem(tableQuickAccess, SWT.NONE);
                   newItem.setText(0, new File(item).getName());
                   newItem.setText(1, item);
               }
               folder.getShell().setEnabled(true);
            } 
        });
    }
    
    /**
     * Creates a {@link Button} that removes selected Quick Access item(s)
     */
    private void createButtonDelQa() {
        buttonDelQa = addButton("&-");
        final FormData layout = SharedStyle.relativeTo(buttonAddQa, 
                null, null, null);
        layout.bottom = null;
        layout.left = null;
        layout.width = SharedStyle.BUTTON_MIN_WIDTH;
        buttonDelQa.setLayoutData(layout);
        buttonDelQa.setToolTipText("Remove selected Quick Access item(s)");
        buttonDelQa.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                tableQuickAccess.remove(tableQuickAccess.getSelectionIndices());
            } 
        });
    }
    
    /**
     * Creates a {@link Button} that moves a Quick Access item up
     */
    private void createButtonUpQa() {
        buttonUpQa = addButton("&Up");
        final FormData layout = SharedStyle.relativeTo(null, null, 
                buttonDnQa, null);
        layout.top = null;
        layout.left = null;
        layout.width = SharedStyle.BUTTON_MIN_WIDTH;
        buttonUpQa.setLayoutData(layout);
        buttonUpQa.setToolTipText("Move selected item up");
        buttonUpQa.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
               if (tableQuickAccess.getSelectionCount() == 0) {
                   return;
               }
               if (tableQuickAccess.getSelectionCount() > 1) {
                   showMoveWarnDialog();
                   return;
               }
               final int i = tableQuickAccess.getSelectionIndex();
               //first item
               if (i == 0) {
                   return;
               }
               swap(tableQuickAccess.getItem(i-1), tableQuickAccess.getItem(i));
               tableQuickAccess.setSelection(i - 1);
            }
        });
    }
    
    /**
     * Creates a {@link Button} that moves Quick Access item down
     */
    private void createButtonDnQa() {
        buttonDnQa = addButton("&Dn");
        final FormData layout = SharedStyle.relativeTo(null, null, null, null);
        layout.top = null;
        layout.left = null;
        layout.width = SharedStyle.BUTTON_MIN_WIDTH;
        buttonDnQa.setLayoutData(layout);
        buttonDnQa.setToolTipText("Move selected item down");
        buttonDnQa.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
               if (tableQuickAccess.getSelectionCount() == 0) {
                   return;
               }
               if (tableQuickAccess.getSelectionCount() > 1) {
                   showMoveWarnDialog();
                   return;
               }
               final int i = tableQuickAccess.getSelectionIndex();
               //last item
               if (i == tableQuickAccess.getItemCount() - 1) {
                   return;
               }
               swap(tableQuickAccess.getItem(i), tableQuickAccess.getItem(i + 1));
               tableQuickAccess.setSelection(i + 1);
            }
        });
    }
    
    /**
     * Swaps two items in a table
     * 
     * @param one
     * @param two
     */
    private void swap(final TableItem one, final TableItem two) {
        final String temp0 = one.getText(0);
        final String temp1 = one.getText(1);
        one.setText(0, two.getText(0));
        one.setText(1, two.getText(1));
        two.setText(0, temp0);
        two.setText(1, temp1);
    }
    
    /**
     * Shows warning when user is tring to move more than one selected item
     */
    private void showMoveWarnDialog() {
        final MessageBox mb = new MessageBox(folder.getShell(), SWT.ICON_WARNING);
        mb.setMessage("Please select only one item to move!");
        mb.setText("Hey!");
        folder.getShell().setEnabled(false);
        mb.open();
        folder.getShell().setEnabled(true);
    }     

    /**
     * Creates the Quick Access {@link Table}
     */
    private void createTableQuickAccess() {
        tableQuickAccess = new Table(container, SWT.MULTI | SWT.BORDER
                | SWT.SINGLE | SWT.VERTICAL | SWT.HORIZONTAL);
        final FormData layout = ident(SharedStyle.relativeTo(
                labelQuickAccessLoc, buttonAddQa, null, null));
        layout.width = 250;
        layout.height = 120;
        tableQuickAccess.setLayoutData(layout);
        tableQuickAccess.setLinesVisible(true);
        tableQuickAccess.setHeaderVisible(true);
        final TableColumn colType = new TableColumn(tableQuickAccess, SWT.NONE);
        colType.setText("Name");
        colType.setWidth(5);
        final TableColumn colApp = new TableColumn(tableQuickAccess, SWT.NONE);
        colApp.setText("Path");
        // load quick access list
        final Iterator<File> qaFiles = cfg.getQuickAccessList().iterator();
        final Map<String, String> names = cfg.getQuickAccessNames();
        log.debug("Quick Access List Names: " + names);
        if (qaFiles.hasNext()) {
            for (final String qaItem : cfg.getRawQuickAccessList()) {
                try {
                    final String qaFile = qaFiles.next().getAbsolutePath()
                            .replaceAll("\\\\", "/");
                    final TableItem item = new TableItem(tableQuickAccess,
                            SWT.NONE);
                    item.setText(0, names.containsKey(qaItem) ? 
                            names.get(qaItem) : new File(qaFile).getName());
                    item.setText(1, qaItem);
                } catch (final Exception e) {
                    log.warn("Processing invalid access entry: " + qaItem
                            + ": " + e.getMessage(), e);
                }
            }
        }        
        colType.pack();
        colApp.pack();
        new PlainTextTableEditor(tableQuickAccess);
    }    
    
    @Override
    protected void saveConfiguration() {
        final StringBuilder quickAccess = new StringBuilder();
        final StringBuilder quickAccessNames = new StringBuilder();
        for (final TableItem item : tableQuickAccess.getItems()) {
            quickAccess.append(item.getText(1).replaceAll("\\\\", "/"));
            quickAccess.append(';');
            quickAccessNames.append(item.getText(1).replaceAll("\\\\", "/"))
                .append("::").append(item.getText(0));
            quickAccessNames.append(';');
        }
        cfg.getProperties().put(Configuration.QUICK_ACCESS_LIST, 
                quickAccess.toString());       
        cfg.getProperties().put(Configuration.QUICK_ACCESS_NAMES_LIST, 
                quickAccessNames.toString());
    }

}
