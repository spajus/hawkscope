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

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.gui.SharedStyle;


public class BlackListSettingsTabItem extends AbstractSettingsTabItem {
	
    private Label predefined;
    
    private Button displayHidden;
    
    private Button displayFloppy;
    
    private Label blacklisted;
    
    private List listBlack;
    
    private Button buttonAddBlack;
    
    private Button buttonDelBlack;
    
	public BlackListSettingsTabItem(final TabFolder folder) {
		super(folder, "&Blacklist");
		
		//Predefined
		predefined = addSectionLabel("Predefined");
		predefined.setLayoutData(SharedStyle.relativeTo(null, null));
		
		//[] Display hidden files
		displayHidden = addCheckbox("Display hidden files");
		displayHidden.setLayoutData(ident(SharedStyle.relativeTo(predefined, null)));
		displayHidden.setSelection(cfg.isHiddenFilesDisplayed());
		
		//[] Display floppy drives
		displayFloppy = addCheckbox("Display floppy drives (discouraged)");
		displayFloppy.setLayoutData(ident(SharedStyle.relativeTo(displayHidden, null)));
        displayFloppy.setSelection(cfg.isFloppyDrivesDisplayed());
        
        //Blacklisted Locations
        blacklisted = addSectionLabel("Blacklisted Locations");
        blacklisted.setLayoutData(SharedStyle.relativeTo(displayFloppy, null));
		
        createButtonAddBlack();
        createButtonDelBlack();
        
		createListBlack();
	}
	
	private void createButtonAddBlack() {
	    buttonAddBlack = addButton("&+");
	    final FormData layout = SharedStyle.relativeTo(blacklisted,
	            null, null, null);
	    layout.width = 30;
	    layout.bottom = null;
	    layout.left = null;
	    buttonAddBlack.setLayoutData(layout);
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
	
	private void createButtonDelBlack() {
        buttonDelBlack = addButton("&-");
        final FormData layout = SharedStyle.relativeTo(buttonAddBlack, 
                null, null, null);
        layout.width = 30;
        layout.bottom = null;
        layout.left = null;
        buttonDelBlack.setLayoutData(layout); 
        buttonDelBlack.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
               for (String s : listBlack.getSelection()) {
                   listBlack.remove(s);
               }
               listBlack.setToolTipText("");
            } 
        });
	}
	
	private void createListBlack() {
	    listBlack = new List(container, 
                SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	    FormData layout = ident(SharedStyle.relativeTo(blacklisted, buttonAddBlack, null, null));
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
