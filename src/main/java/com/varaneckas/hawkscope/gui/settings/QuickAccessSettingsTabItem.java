package com.varaneckas.hawkscope.gui.settings;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;

import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.gui.SharedStyle;

public class QuickAccessSettingsTabItem extends AbstractSettingsTabItem {
    
    private Label labelQuickAccessLoc;
    private List listQuickAccess;
    private Button buttonAddQa;
    private Button buttonDelQa;
    private Button buttonUpQa;
    private Button buttonDnQa;
    
	public QuickAccessSettingsTabItem(final TabFolder folder) {
		super(folder, "&Quick Access");
		
		//Quick Access Locations
		labelQuickAccessLoc = addSectionLabel("Quick Access Locations");
		labelQuickAccessLoc.setLayoutData(SharedStyle.relativeTo(null, null));
		
		createButtonAddQa();
		createButtonDelQa();

		createListQuickAccess();

		createButtonDnQa();
		createButtonUpQa();

	}

    private void createButtonAddQa() {
        buttonAddQa = addButton("&+");
        FormData layout = SharedStyle.relativeTo(labelQuickAccessLoc, 
                null, null, null);
        layout.bottom = null;
        layout.left = null;
        layout.width = 30;
        buttonAddQa.setLayoutData(layout);
        buttonAddQa.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
               final DirectoryDialog fd = new DirectoryDialog(folder.getShell(), 
                       SWT.OPEN);
               folder.getShell().setEnabled(false);
               fd.setMessage("Find a folder to add");
               fd.setText("Add to Quick Access List");
               final String item = fd.open();
               if (item != null && !Arrays.asList(listQuickAccess.getItems())
                       .contains(item)) {
                   listQuickAccess.add(item);
                   listQuickAccess.setToolTipText(item);
                   listQuickAccess.setSelection(
                           listQuickAccess.getItemCount() - 1);
               }
               folder.getShell().setEnabled(true);
            } 
        });
    }
    
    private void createButtonDelQa() {
        buttonDelQa = addButton("&-");
        FormData layout = SharedStyle.relativeTo(buttonAddQa, null, null, null);
        layout.bottom = null;
        layout.left = null;
        layout.width = 30;
        buttonDelQa.setLayoutData(layout);
        buttonDelQa.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
               for (final String s : listQuickAccess.getSelection()) {
                   listQuickAccess.remove(s);
               }
               listQuickAccess.setToolTipText("");
            } 
        });
    }
    
    private void createButtonUpQa() {
        buttonUpQa = addButton("&Up");
        FormData layout = SharedStyle.relativeTo(null, null, buttonDnQa, null);
        layout.top = null;
        layout.left = null;
        layout.width = 30;
        buttonUpQa.setLayoutData(layout);
        buttonUpQa.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
               if (listQuickAccess.getSelectionCount() == 0) return;
               if (listQuickAccess.getSelectionCount() > 1) {
                   showMoveWarnDialog();
                   return;
               }
               int i = listQuickAccess.getSelectionIndex();
               //first item
               if (i == 0) return;
               final String temp = listQuickAccess.getItem(i-1);
               listQuickAccess.setItem(i-1, listQuickAccess.getSelection()[0]);
               listQuickAccess.setItem(i, temp);
               listQuickAccess.setSelection(i-1);
            }
        });
    }
    
    private void createButtonDnQa() {
        buttonDnQa = addButton("&Dn");
        FormData layout = SharedStyle.relativeTo(null, null, null, null);
        layout.top = null;
        layout.left = null;
        layout.width = 30;
        buttonDnQa.setLayoutData(layout);
        buttonDnQa.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
               if (listQuickAccess.getSelectionCount() == 0) return;
               if (listQuickAccess.getSelectionCount() > 1) {
                   showMoveWarnDialog();
                   return;
               }
               int i = listQuickAccess.getSelectionIndex();
               //last item
               if (i == listQuickAccess.getItemCount()-1) return;
               final String temp = listQuickAccess.getItem(i+1);
               listQuickAccess.setItem(i+1, listQuickAccess.getSelection()[0]);
               listQuickAccess.setItem(i, temp);
               listQuickAccess.setSelection(i+1);
            }
        });
    }
    
    private void showMoveWarnDialog() {
        final MessageBox mb = new MessageBox(folder.getShell(), SWT.ICON_WARNING);
        mb.setMessage("Please select only one item to move!");
        mb.setText("Hey!");
        folder.getShell().setEnabled(false);
        mb.open();
        folder.getShell().setEnabled(true);
    }     

    private void createListQuickAccess() {
        listQuickAccess = new List(container, 
                SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        final FormData layout = ident(SharedStyle.relativeTo(
                labelQuickAccessLoc, buttonAddQa, null, null));
        layout.width = 250;
        layout.height = 120;
        listQuickAccess.setLayoutData(layout);
        listQuickAccess.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
               if (listQuickAccess.getSelectionCount() == 1) {
                   listQuickAccess
                       .setToolTipText(listQuickAccess.getSelection()[0]);
               }
            } 
        });
        final Iterator<File> qaFiles = cfg.getQuickAccessList().iterator();
        if (qaFiles.hasNext()) {
            for (final String qaItem : cfg.getRawQuickAccessList()) {
                try {
                    String qaFile = qaFiles.next().getAbsolutePath()
                            .replaceAll("\\\\", "/");
                    if (!qaFile.equals(qaItem)) {
                        listQuickAccess.add(qaFile + " <" + qaItem + ">");
                    } else {
                        listQuickAccess.add(qaItem);
                    }
                } catch (final Exception e) {
                    log.warn("Processing invalid access entry: " + qaItem 
                            + ": " + e.getMessage(), e);
                }
            }
        }
    }

    @Override
    protected void saveConfiguration() {
        final StringBuilder quickAccess = new StringBuilder();
        for (final String item : listQuickAccess.getItems()) {
            quickAccess.append(item.replaceFirst(".+<", "")
                    .replaceFirst(">", "").replaceAll("\\\\", "/"));
            quickAccess.append(';');
        }
        cfg.getProperties().put(Configuration.QUICK_ACCESS_LIST, 
                quickAccess.toString());        
    }

}
