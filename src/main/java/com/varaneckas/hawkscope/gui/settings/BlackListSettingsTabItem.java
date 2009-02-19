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

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.gui.SharedStyle;

/**
 * Blacklist Settings {@link TabItem}
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class BlackListSettingsTabItem extends AbstractSettingsTabItem {
	
    /**
     * Label "Predefined"
     */
    private Label predefined;
    
    /**
     * Checkbox "[] Display hidden files"
     */
    private Button displayHidden;
    
    /**
     * Checkbox "[] Display floppy drives (discouraged)"
     */
    private Button displayFloppy;
    
    /**
     * Label "Blacklisted Locations"
     */
    private Label blacklisted;
    
    /**
     * The Blacklist itself
     */
    private List listBlack;
    
    /**
     * Button that adds to Blacklist
     */
    private Button buttonAddBlack;
    
    /**
     * Button that removes from Blacklist
     */
    private Button buttonDelBlack;
    
    /**
     * Creates the Blacklist Setting {@link TabItem}
     * 
     * @param folder Settings {@link TabFolder}
     */
	public BlackListSettingsTabItem(final TabFolder folder) {
		super(folder, "&Blacklist");
		
		//Predefined
		predefined = addSectionLabel("Predefined");
		predefined.setLayoutData(SharedStyle.relativeTo(null, null));
		
		//[] Display hidden files
		displayHidden = addCheckbox("Display &hidden files");
		displayHidden.setLayoutData(ident(SharedStyle.relativeTo(predefined, 
		        null)));
		displayHidden.setSelection(cfg.isHiddenFilesDisplayed());
		
		//[] Display floppy drives
		displayFloppy = addCheckbox("Display &floppy drives (discouraged)");
		displayFloppy.setLayoutData(ident(SharedStyle.relativeTo(displayHidden, 
		        null)));
        displayFloppy.setSelection(cfg.isFloppyDrivesDisplayed());
        
        //Blacklisted Locations
        blacklisted = addSectionLabel("Blacklisted Locations");
        blacklisted.setLayoutData(SharedStyle.relativeTo(displayFloppy, null));
		
        createButtonAddBlack();
        createButtonDelBlack();
        
		createListBlack();
	}
	
	/**
	 * Creates a {@link Button} that adds item to Blacklist
	 */
	private void createButtonAddBlack() {
	    buttonAddBlack = addButton("&+");
	    final FormData layout = SharedStyle.relativeTo(blacklisted,
	            null, null, null);
	    layout.width = SharedStyle.BUTTON_MIN_WIDTH;
	    layout.bottom = null;
	    layout.left = null;
	    buttonAddBlack.setLayoutData(layout);
	    buttonAddBlack.setToolTipText("Add item to Blacklist");
	    buttonAddBlack.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
               final DirectoryDialog fd = new DirectoryDialog(folder.getShell(), 
                       SWT.OPEN);
               folder.getShell().setEnabled(false);
               fd.setMessage("Find a folder to add");
               fd.setText("Add to Blacklist");
               final String item = fd.open();
               if (item != null && !Arrays.asList(listBlack.getItems())
                       .contains(item)) {
                   listBlack.add(item);
                   listBlack.setToolTipText(item);
                   listBlack.setSelection(listBlack.getItemCount()-1);
               }
               folder.getShell().setEnabled(true);
            } 
        });
	}
	
	/**
	 * Creates a {@link Button} that removes selected Blacklist item(s)
	 */
	private void createButtonDelBlack() {
        buttonDelBlack = addButton("&-");
        final FormData layout = SharedStyle.relativeTo(buttonAddBlack, 
                null, null, null);
        layout.width = SharedStyle.BUTTON_MIN_WIDTH;
        layout.bottom = null;
        layout.left = null;
        buttonDelBlack.setLayoutData(layout); 
        buttonDelBlack.setToolTipText("Remove selected Blacklist item(s)");
        buttonDelBlack.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
               for (String s : listBlack.getSelection()) {
                   listBlack.remove(s);
               }
               listBlack.setToolTipText("");
            } 
        });
	}
	
	/**
	 * Creates the Blacklist
	 */
	private void createListBlack() {
	    listBlack = new List(container, 
                SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	    final FormData layout = ident(SharedStyle.relativeTo(blacklisted, 
	            buttonAddBlack, null, null));
	    layout.width = 250;
	    layout.height = 60;
	    listBlack.setLayoutData(layout);
	    listBlack.addSelectionListener(new SelectionAdapter() {
            @Override
             public void widgetSelected(SelectionEvent event) {
                if (listBlack.getSelectionCount() == 1) {
                    listBlack.setToolTipText(listBlack.getSelection()[0]);
                }
             } 
         });
         for (final String blItem : cfg.getRawBlackList()) {
             listBlack.add(blItem.replaceAll("\\\\", "/"));
         }
	}

    @Override
    protected void saveConfiguration() {
        cfg.getProperties().put(Configuration.HIDDEN_FILES_DISPLAYED, 
                displayHidden.getSelection() ? "1" : "0");
        cfg.getProperties().put(Configuration.FLOPPY_DRIVES_DISPLAYED, 
                displayFloppy.getSelection() ? "1" : "0");
        StringBuilder blackList = new StringBuilder();
        for (final String item : listBlack.getItems()) {
            blackList.append(item.replaceAll("\\\\", "/"));
            blackList.append(';');
        }
        cfg.getProperties().put(Configuration.FILESYSTEM_BLACKLIST, 
                blackList.toString());
    }

}
