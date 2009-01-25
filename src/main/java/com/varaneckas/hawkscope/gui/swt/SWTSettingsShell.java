package com.varaneckas.hawkscope.gui.swt;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.SettingsWindow;
import com.varaneckas.hawkscope.menu.MenuFactory;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class SWTSettingsShell extends org.eclipse.swt.widgets.Dialog implements
    SettingsWindow {

    private static final Log log = LogFactory.getLog(SWTSettingsShell.class);
    
    private Shell dialogShell;
    private TabFolder settingsTabFolder;
    private TabItem tabNetwork;
    private Button checkDisplayFloppy;
    private List listQuickAccess;
    private Label labelQuickAccessList;
    private Composite containerQuickAccess;
    private Label labelBlacklisted;
    private Label labelPredefined;
    private Text textMenuReloadDelay;
    private Label labelReloadDelay;
    private Label labelMenu;
    private Button checkCheckforUpdates;
    private Text textHttpProxyPort;
    private Label labelHttpProxyPort;
    private Text textHttpProxyHost;
    private Label labelProxyHost;
    private Button buttonUseHttpProxy;
    private Label labelProxy;
    private Composite containerNetwork;
    private Button buttonDelQuickAccess;
    private Button buttonAddQuickAccess;
    private Label labelUpdates;
    private Composite contanerGeneral;
    private Button buttonRemoveFromBlacklist;
    private Button buttonAddToBlacklist;
    private List listBlacklist;
    private Button checkDisplayHidden;
    private Composite containerBlacklist;
    private Button checkUseOsIcons;
    private Button buttonQuickItemDown;
    private Button buttonQuickItemUp;
    private Button buttonOK;
    private Button buttonCancel;
    private TabItem tabBlacklist;
    private TabItem tabGeneral;
    private TabItem tabQuickAccess;

    private Configuration cfg = ConfigurationFactory.getConfigurationFactory()
            .getConfiguration();
    
    public SWTSettingsShell(Shell parent, int style) {
        super(parent, style);
    }

    public void open() {
        try {
            final Shell parent = getParent();
            dialogShell = new Shell(parent, SWT.DIALOG_TRIM
                    | SWT.APPLICATION_MODAL);
            {
                //Register as a resource user - SWTResourceManager will
                //handle the obtaining and disposing of resources
                SWTResourceManager.registerResourceUser(dialogShell);
            }
            dialogShell.setText("Settings");
            dialogShell.setImage((Image) SWTIconFactory.getIconFactory()
                    .getUncachedIcon("hawkscope16.png"));
            
            
            dialogShell.setLayout(new FormLayout());
            dialogShell.layout();
            dialogShell.pack();
            dialogShell.setSize(418, 311);
            //Settings tab folder: [General][Quick Access][Blacklist][Network]
            {
                settingsTabFolder = new TabFolder(dialogShell, SWT.NONE);
                FormData settingsTabFolderLData = new FormData();
                settingsTabFolderLData.width = 382;
                settingsTabFolderLData.height = 197;
                settingsTabFolderLData.left =  new FormAttachment(0, 1000, 12);
                settingsTabFolderLData.top =  new FormAttachment(0, 1000, 11);
                settingsTabFolder.setLayoutData(settingsTabFolderLData);
                //[General] tab
                createGeneralTab();
                //[Quick Access] tab
                createQuickAccessTab();
                //[Blacklist] tab
                createBlacklistTab();
                //[Network] tab
                createNetworkTab();
                settingsTabFolder.setSelection(0);
            }
            createButtons();            
            dialogShell.setLocation(getParent().toDisplay(100, 100));
            dialogShell.open();
            Display display = dialogShell.getDisplay();
            while (!dialogShell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
        } catch (final Exception e) {
            log.error("Failed loading SWTSettingsShell", e);
            throw new RuntimeException("Failed loading SWTSettingsSHell", e);
        }
    }

    private void createButtons() {
        //[OK] Button
        buttonOK = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
        FormData buttonOKLData = new FormData();
        buttonOKLData.width = 30;
        buttonOKLData.height = 29;
        buttonOKLData.left =  new FormAttachment(0, 1000, 299);
        buttonOKLData.top =  new FormAttachment(0, 1000, 247);
        buttonOK.setLayoutData(buttonOKLData);
        buttonOK.setText("&OK");
        buttonOK.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                saveConfiguration();
                hideObject();
                MenuFactory.getMenuFactory().getMainMenu().reloadMenu();
            }
        });
        
        //[Cancel] Button
        buttonCancel = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
        FormData buttonCancelLData = new FormData();
        buttonCancelLData.width = 55;
        buttonCancelLData.height = 29;
        buttonCancelLData.left =  new FormAttachment(0, 1000, 341);
        buttonCancelLData.top =  new FormAttachment(0, 1000, 247);
        buttonCancel.setLayoutData(buttonCancelLData);
        buttonCancel.setText("&Cancel");
        buttonCancel.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                hideObject();
            } 
        });
    }

    private void createNetworkTab() {
        tabNetwork = new TabItem(settingsTabFolder, SWT.NONE);
        tabNetwork.setText("&Network");
        containerNetwork = new Composite(settingsTabFolder, SWT.NONE);
        FormLayout containerNetworkLayout = new FormLayout();
        containerNetwork.setLayout(containerNetworkLayout);
        tabNetwork.setControl(containerNetwork);
        //Label: HTTP Proxy
        {
            labelProxy = new Label(containerNetwork, SWT.NONE);
            FormData labelProxyLData = new FormData();
            labelProxyLData.width = 358;
            labelProxyLData.height = 17;
            labelProxyLData.left =  new FormAttachment(0, 1000, 12);
            labelProxyLData.top =  new FormAttachment(0, 1000, 12);
            labelProxy.setLayoutData(labelProxyLData);
            labelProxy.setText("HTTP Proxy");
            labelProxy.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
        }
        //Checkbox: Use HTTP Proxy
        {
            buttonUseHttpProxy = new Button(containerNetwork, SWT.CHECK | SWT.LEFT);
            FormData buttonUseHttpProxyLData = new FormData();
            buttonUseHttpProxyLData.width = 124;
            buttonUseHttpProxyLData.height = 22;
            buttonUseHttpProxyLData.left =  new FormAttachment(0, 1000, 24);
            buttonUseHttpProxyLData.top =  new FormAttachment(0, 1000, 35);
            buttonUseHttpProxy.setLayoutData(buttonUseHttpProxyLData);
            buttonUseHttpProxy.setText("&Use HTTP Proxy");
            buttonUseHttpProxy.setSelection(cfg.isHttpProxyInUse());
            buttonUseHttpProxy.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent event) {
                    textHttpProxyHost.setEnabled(buttonUseHttpProxy.getSelection());
                    textHttpProxyPort.setEnabled(buttonUseHttpProxy.getSelection());
                }
            });
        }  
        //Label: HTTP Proxy Host
        {
            labelProxyHost = new Label(containerNetwork, SWT.NONE);
            FormData labelProxyHostLData = new FormData();
            labelProxyHostLData.width = 127;
            labelProxyHostLData.height = 17;
            labelProxyHostLData.left =  new FormAttachment(0, 1000, 24);
            labelProxyHostLData.top =  new FormAttachment(0, 1000, 63);
            labelProxyHost.setLayoutData(labelProxyHostLData);
            labelProxyHost.setText("HTTP Proxy Host:");
        }             
        //Text: HTTP Proxy Host input
        {
            FormData textHttpProxyHostLData = new FormData();
            textHttpProxyHostLData.width = 197;
            textHttpProxyHostLData.height = 17;
            textHttpProxyHostLData.left =  new FormAttachment(0, 1000, 161);
            textHttpProxyHostLData.top =  new FormAttachment(0, 1000, 59);
            textHttpProxyHost = new Text(containerNetwork, SWT.BORDER);
            textHttpProxyHost.setLayoutData(textHttpProxyHostLData);
            textHttpProxyHost.setText(cfg.getHttpProxyHost());
            textHttpProxyHost.setEnabled(cfg.isHttpProxyInUse());
        }
        //Label: HTTP Proxy Port
        {
            labelHttpProxyPort = new Label(containerNetwork, SWT.NONE);
            FormData labelHttpProxyPortLData = new FormData();
            labelHttpProxyPortLData.width = 127;
            labelHttpProxyPortLData.height = 17;
            labelHttpProxyPortLData.left =  new FormAttachment(0, 1000, 24);
            labelHttpProxyPortLData.top =  new FormAttachment(0, 1000, 92);
            labelHttpProxyPort.setLayoutData(labelHttpProxyPortLData);
            labelHttpProxyPort.setText("HTTP Proxy Port:");
        }
        //Text: HTTP Proxy Port input
        {
            textHttpProxyPort = new Text(containerNetwork, SWT.BORDER);
            FormData textHttpProxyPortLData = new FormData();
            textHttpProxyPortLData.width = 65;
            textHttpProxyPortLData.height = 17;
            textHttpProxyPortLData.left =  new FormAttachment(0, 1000, 161);
            textHttpProxyPortLData.top =  new FormAttachment(0, 1000, 89);
            textHttpProxyPort.setLayoutData(textHttpProxyPortLData);
            textHttpProxyPort.setText("" + cfg.getHttpProxyPort());
            textHttpProxyPort.setEnabled(cfg.isHttpProxyInUse());
            textHttpProxyPort.addListener(SWT.FocusOut, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    try {
                        int s = Integer.valueOf(textHttpProxyPort.getText());
                        if (s <= 0) {
                            textHttpProxyPort.setText("1");
                        }
                        if (s > Short.MAX_VALUE) {
                            textHttpProxyPort.setText("" + Short.MAX_VALUE);
                        }
                    } catch (final Exception e) {
                        textHttpProxyPort.setText("" + cfg.getHttpProxyPort());
                    }
                }
            });
        }
    }

    private void createBlacklistTab() {
        tabBlacklist = new TabItem(settingsTabFolder, SWT.NONE);
        tabBlacklist.setText("&Blacklist");
        containerBlacklist = new Composite(settingsTabFolder, SWT.NONE);
        FormLayout containerBlacklistLayout = new FormLayout();
        containerBlacklist.setLayout(containerBlacklistLayout);
        tabBlacklist.setControl(containerBlacklist);
        //Label: predefined
        {
            labelPredefined = new Label(containerBlacklist, SWT.NONE);
            FormData labelPredefinedLData = new FormData();
            labelPredefinedLData.width = 358;
            labelPredefinedLData.height = 17;
            labelPredefinedLData.left =  new FormAttachment(0, 1000, 12);
            labelPredefinedLData.top =  new FormAttachment(0, 1000, 12);
            labelPredefined.setLayoutData(labelPredefinedLData);
            labelPredefined.setText("Predefined");
            labelPredefined.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
        }
        //Checkbox: Display Hidden
        {
            checkDisplayHidden = new Button(containerBlacklist, SWT.CHECK | SWT.LEFT);
            FormData checkDisplayHiddenLData = new FormData();
            checkDisplayHiddenLData.width = 341;
            checkDisplayHiddenLData.height = 22;
            checkDisplayHiddenLData.left =  new FormAttachment(0, 1000, 29);
            checkDisplayHiddenLData.top =  new FormAttachment(0, 1000, 35);
            checkDisplayHidden.setLayoutData(checkDisplayHiddenLData);
            checkDisplayHidden.setText("Display &hidden files");
            checkDisplayHidden.setSelection(cfg.isHiddenFilesDisplayed());
            checkDisplayHidden.setToolTipText("Should hidden files get listed? You may want that if you're a geek.");
        }
        //Checkbox: Display Floppy
        {
            checkDisplayFloppy = new Button(containerBlacklist, SWT.CHECK | SWT.LEFT);
            FormData checkDisplayFloppyLData = new FormData();
            checkDisplayFloppyLData.width = 341;
            checkDisplayFloppyLData.height = 22;
            checkDisplayFloppyLData.left =  new FormAttachment(0, 1000, 29);
            checkDisplayFloppyLData.top =  new FormAttachment(0, 1000, 63);
            checkDisplayFloppy.setLayoutData(checkDisplayFloppyLData);
            checkDisplayFloppy.setText("Display &floppy drives (discouraged)");
            checkDisplayFloppy.setSelection(cfg.isFloppyDrivesDisplayed());
            checkDisplayFloppy.setToolTipText("Should floppy drives get listed in Hawkscope menu? Warning, floppy drives make annoying sounds when scanned...");
        }         
        //Label: Blacklisted Locations
        {
            labelBlacklisted = new Label(containerBlacklist, SWT.NONE);
            FormData labelBlacklistedLData = new FormData();
            labelBlacklistedLData.width = 358;
            labelBlacklistedLData.height = 17;
            labelBlacklistedLData.left =  new FormAttachment(0, 1000, 12);
            labelBlacklistedLData.top =  new FormAttachment(0, 1000, 91);
            labelBlacklisted.setLayoutData(labelBlacklistedLData);
            labelBlacklisted.setText("Blacklisted Locations");
            labelBlacklisted.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
        }     
        //List: Blacklist
        {
            FormData listBlacklistLData = new FormData();
            listBlacklistLData.width = 271;
            listBlacklistLData.height = 66;
            listBlacklistLData.left =  new FormAttachment(0, 1000, 24);
            listBlacklistLData.top =  new FormAttachment(0, 1000, 114);
            listBlacklist = new List(containerBlacklist, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
            listBlacklist.setLayoutData(listBlacklistLData);
            listBlacklist.addSelectionListener(new SelectionAdapter() {
               @Override
                public void widgetSelected(SelectionEvent event) {
                   if (listBlacklist.getSelectionCount() == 1) {
                       listBlacklist.setToolTipText(listBlacklist.getSelection()[0]);
                   }
                } 
            });
            for (final String blItem : cfg.getRawBlackList()) {
                listBlacklist.add(blItem.replaceAll("\\\\", "/"));
            }
        }  
        //Button: [+] (Blacklist)
        {
            buttonAddToBlacklist = new Button(containerBlacklist, SWT.PUSH | SWT.CENTER);
            FormData buttonAddToBlacklistLData = new FormData();
            buttonAddToBlacklistLData.width = 40;
            buttonAddToBlacklistLData.height = 29;
            buttonAddToBlacklistLData.left =  new FormAttachment(0, 1000, 330);
            buttonAddToBlacklistLData.top =  new FormAttachment(0, 1000, 115);
            buttonAddToBlacklist.setLayoutData(buttonAddToBlacklistLData);
            buttonAddToBlacklist.setText("+");
            buttonAddToBlacklist.addSelectionListener(new SelectionAdapter() {
               @Override
                public void widgetSelected(SelectionEvent event) {
                   DirectoryDialog fd = new DirectoryDialog(dialogShell, SWT.OPEN);
                   dialogShell.setEnabled(false);
                   fd.setMessage("Find a folder to add");
                   fd.setText("Add to Blacklist");
                   String item = fd.open();
                   if (item != null && !Arrays.asList(listBlacklist.getItems())
                           .contains(item)) {
                       listBlacklist.add(item);
                       listBlacklist.setToolTipText(item);
                       listBlacklist.setSelection(listBlacklist.getItemCount()-1);
                   }
                   dialogShell.setEnabled(true);
                } 
            });
        }        
        //Button: [-] (Blacklist)
        {
            buttonRemoveFromBlacklist = new Button(containerBlacklist, SWT.PUSH | SWT.CENTER);
            FormData buttonRemoveFromBlacklistLData = new FormData();
            buttonRemoveFromBlacklistLData.width = 40;
            buttonRemoveFromBlacklistLData.height = 29;
            buttonRemoveFromBlacklistLData.left =  new FormAttachment(0, 1000, 330);
            buttonRemoveFromBlacklistLData.top =  new FormAttachment(0, 1000, 150);
            buttonRemoveFromBlacklist.setLayoutData(buttonRemoveFromBlacklistLData);
            buttonRemoveFromBlacklist.setText("-");
            buttonRemoveFromBlacklist.addSelectionListener(new SelectionAdapter() {
               @Override
                public void widgetSelected(SelectionEvent event) {
                   for (String s : listBlacklist.getSelection()) {
                       listBlacklist.remove(s);
                   }
                   listBlacklist.setToolTipText("");
                } 
            });
        }
    }

    private void createQuickAccessTab() {
        tabQuickAccess = new TabItem(settingsTabFolder, SWT.NONE);
        tabQuickAccess.setText("&Quick Access");
        containerQuickAccess = new Composite(settingsTabFolder, SWT.NONE);
        FormLayout containerQuickAccessLayout = new FormLayout();
        containerQuickAccess.setLayout(containerQuickAccessLayout);
        tabQuickAccess.setControl(containerQuickAccess);
        //Label: Quick Access Locations
        {
            labelQuickAccessList = new Label(containerQuickAccess, SWT.NONE);
            FormData labelQuickAccessListLData = new FormData();
            labelQuickAccessListLData.width = 358;
            labelQuickAccessListLData.height = 17;
            labelQuickAccessListLData.left =  new FormAttachment(0, 1000, 12);
            labelQuickAccessListLData.top =  new FormAttachment(0, 1000, 12);
            labelQuickAccessList.setLayoutData(labelQuickAccessListLData);
            labelQuickAccessList.setText("Quick Access Locations");
            labelQuickAccessList.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
        }                       
        //List: Quick access list
        {
            FormData listQuickAccessLData = new FormData();
            listQuickAccessLData.width = 273;
            listQuickAccessLData.height = 144;
            listQuickAccessLData.left =  new FormAttachment(0, 1000, 24);
            listQuickAccessLData.top =  new FormAttachment(0, 1000, 36);
            listQuickAccess = new List(containerQuickAccess, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
            listQuickAccess.setLayoutData(listQuickAccessLData);
            listQuickAccess.addSelectionListener(new SelectionAdapter() {
               @Override
                public void widgetSelected(SelectionEvent event) {
                   if (listQuickAccess.getSelectionCount() == 1) {
                       listQuickAccess.setToolTipText(listQuickAccess.getSelection()[0]);
                   }
                } 
            });
            Iterator<File> qaFiles = cfg.getQuickAccessList().iterator();
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
        //Button [+] (QA list)
        {
            buttonAddQuickAccess = new Button(containerQuickAccess, SWT.PUSH | SWT.CENTER);
            FormData buttonAddQuickAccessLData = new FormData();
            buttonAddQuickAccessLData.width = 39;
            buttonAddQuickAccessLData.height = 29;
            buttonAddQuickAccessLData.left =  new FormAttachment(0, 1000, 331);
            buttonAddQuickAccessLData.top =  new FormAttachment(0, 1000, 36);
            buttonAddQuickAccess.setLayoutData(buttonAddQuickAccessLData);
            buttonAddQuickAccess.setText("+");
            buttonAddQuickAccess.addSelectionListener(new SelectionAdapter() {
               @Override
                public void widgetSelected(SelectionEvent event) {
                   DirectoryDialog fd = new DirectoryDialog(dialogShell, SWT.OPEN);
                   dialogShell.setEnabled(false);
                   fd.setMessage("Find a folder to add");
                   fd.setText("Add to Quick Access List");
                   String item = fd.open();
                   if (item != null && !Arrays.asList(listQuickAccess.getItems())
                           .contains(item)) {
                       listQuickAccess.add(item);
                       listQuickAccess.setToolTipText(item);
                       listQuickAccess.setSelection(listQuickAccess.getItemCount()-1);
                   }
                   dialogShell.setEnabled(true);
                } 
            });
        }
        //Button [-] (QA list)
        {
            buttonDelQuickAccess = new Button(containerQuickAccess, SWT.PUSH | SWT.CENTER);
            FormData buttonDelQuickAccessLData = new FormData();
            buttonDelQuickAccessLData.width = 39;
            buttonDelQuickAccessLData.height = 29;
            buttonDelQuickAccessLData.left =  new FormAttachment(0, 1000, 331);
            buttonDelQuickAccessLData.top =  new FormAttachment(0, 1000, 71);
            buttonDelQuickAccess.setLayoutData(buttonDelQuickAccessLData);
            buttonDelQuickAccess.setText("-");
            buttonDelQuickAccess.addSelectionListener(new SelectionAdapter() {
               @Override
                public void widgetSelected(SelectionEvent event) {
                   for (String s : listQuickAccess.getSelection()) {
                       listQuickAccess.remove(s);
                   }
                   listQuickAccess.setToolTipText("");
                } 
            });
        }
        //Button [up] (QA list)
        {
            buttonQuickItemUp = new Button(containerQuickAccess, SWT.PUSH | SWT.CENTER);
            FormData buttonQuickItemUpLData = new FormData();
            buttonQuickItemUpLData.width = 39;
            buttonQuickItemUpLData.height = 29;
            buttonQuickItemUpLData.left =  new FormAttachment(0, 1000, 331);
            buttonQuickItemUpLData.top =  new FormAttachment(0, 1000, 118);
            buttonQuickItemUp.setLayoutData(buttonQuickItemUpLData);
            buttonQuickItemUp.setText("Up");
            buttonQuickItemUp.addSelectionListener(new SelectionAdapter() {
               @Override
                public void widgetSelected(SelectionEvent event) {
                   if (listQuickAccess.getSelectionCount() == 0) return;
                   if (listQuickAccess.getSelectionCount() > 1) {
                       showMoveWarnDialog();
                   }
                   int i = listQuickAccess.getSelectionIndex();
                   //first item
                   if (i == 0) return;
                   String temp = listQuickAccess.getItem(i-1);
                   listQuickAccess.setItem(i-1, listQuickAccess.getSelection()[0]);
                   listQuickAccess.setItem(i, temp);
                   listQuickAccess.setSelection(i-1);
                }

            });
        }      
        //Button [dn] (QA list)
        {
            buttonQuickItemDown = new Button(containerQuickAccess, SWT.PUSH | SWT.CENTER);
            FormData buttonQuickItemDownLData = new FormData();
            buttonQuickItemDownLData.width = 39;
            buttonQuickItemDownLData.height = 29;
            buttonQuickItemDownLData.left =  new FormAttachment(0, 1000, 331);
            buttonQuickItemDownLData.top =  new FormAttachment(0, 1000, 153);
            buttonQuickItemDown.setLayoutData(buttonQuickItemDownLData);
            buttonQuickItemDown.setText("Dn");
            buttonQuickItemDown.addSelectionListener(new SelectionAdapter() {
               @Override
                public void widgetSelected(SelectionEvent event) {
                   if (listQuickAccess.getSelectionCount() == 0) return;
                   if (listQuickAccess.getSelectionCount() > 1) {
                       showMoveWarnDialog();
                   }
                   int i = listQuickAccess.getSelectionIndex();
                   //last item
                   if (i == listQuickAccess.getItemCount()-1) return;
                   String temp = listQuickAccess.getItem(i+1);
                   listQuickAccess.setItem(i+1, listQuickAccess.getSelection()[0]);
                   listQuickAccess.setItem(i, temp);
                   listQuickAccess.setSelection(i+1);
                }
            });
        }
    }

    private void createGeneralTab() {
        tabGeneral = new TabItem(settingsTabFolder, SWT.NONE);
        tabGeneral.setText("&General");
        contanerGeneral = new Composite(settingsTabFolder, SWT.NONE);
        FormLayout contanerGeneralLayout = new FormLayout();
        contanerGeneral.setLayout(contanerGeneralLayout);
        tabGeneral.setControl(contanerGeneral);
        //Label: Updates
        {
            labelUpdates = new Label(contanerGeneral, SWT.NONE);
            FormData labelUpdatesLData = new FormData();
            labelUpdatesLData.width = 358;
            labelUpdatesLData.height = 17;
            labelUpdatesLData.left =  new FormAttachment(0, 1000, 12);
            labelUpdatesLData.top =  new FormAttachment(0, 1000, 12);
            labelUpdates.setLayoutData(labelUpdatesLData);
            labelUpdates.setText("Updates");
            labelUpdates.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
        }
        //Checkbox: Check for updates
        {
            checkCheckforUpdates = new Button(contanerGeneral, SWT.CHECK | SWT.LEFT);
            FormData checkCheckforUpdatesLData = new FormData();
            checkCheckforUpdatesLData.width = 346;
            checkCheckforUpdatesLData.height = 22;
            checkCheckforUpdatesLData.left =  new FormAttachment(0, 1000, 24);
            checkCheckforUpdatesLData.top =  new FormAttachment(0, 1000, 35);
            checkCheckforUpdates.setLayoutData(checkCheckforUpdatesLData);
            checkCheckforUpdates.setText("Check for &updates on startup");
            checkCheckforUpdates.setToolTipText("Should Hawkscope check for updates on startup? " +
            		"Please mind the HTTP Proxy settings in Network tab!");
            checkCheckforUpdates.setSelection(cfg.checkForUpdates());
        }
        //Label: Menu
        {
            labelMenu = new Label(contanerGeneral, SWT.NONE);
            FormData labelMenuLData = new FormData();
            labelMenuLData.width = 358;
            labelMenuLData.height = 17;
            labelMenuLData.left =  new FormAttachment(0, 1000, 12);
            labelMenuLData.top =  new FormAttachment(0, 1000, 63);
            labelMenu.setLayoutData(labelMenuLData);
            labelMenu.setText("Menu");
            labelMenu.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
        }
        //Label: Reload delay (seconds)
        {
            labelReloadDelay = new Label(contanerGeneral, SWT.NONE);
            FormData labelReloadDelayLData = new FormData();
            labelReloadDelayLData.width = 164;
            labelReloadDelayLData.height = 22;
            labelReloadDelayLData.left =  new FormAttachment(0, 1000, 24);
            labelReloadDelayLData.top =  new FormAttachment(0, 1000, 86);
            labelReloadDelay.setLayoutData(labelReloadDelayLData);
            labelReloadDelay.setText("&Reload delay (seconds):");
            labelReloadDelay.setToolTipText("After how many seconds " +
                    "of being idle Hawkscope should reload it's main menu " +
                    "(to find new devices, refresh Quick Access List, " +
                    "re-apply Blacklist etc.)? The reload is fast unless you " +
                    "have got many slow network drives.");
        } 
        //Text: menu reload delay input
        {
            textMenuReloadDelay = new Text(contanerGeneral, SWT.BORDER);
            FormData textMenuReloadDelayLData = new FormData();
            textMenuReloadDelayLData.width = 46;
            textMenuReloadDelayLData.height = 16;
            textMenuReloadDelayLData.left =  new FormAttachment(0, 1000, 191);
            textMenuReloadDelayLData.top =  new FormAttachment(0, 1000, 82);
            textMenuReloadDelay.setLayoutData(textMenuReloadDelayLData);
            textMenuReloadDelay.setText("" + (cfg.getMenuReloadDelay() / 1000.0));
            textMenuReloadDelay.addListener(SWT.FocusOut, new Listener() {
                @Override
                public void handleEvent(Event event) {
                    try {
                        double d = Double.valueOf(textMenuReloadDelay.getText());
                        if (d <= 0) {
                            textMenuReloadDelay.setText("0.1");
                        }
                        if (d > 9999) {
                            textMenuReloadDelay.setText("9999");
                        }
                    } catch (final Exception e) {
                        textMenuReloadDelay.setText("" 
                                + (cfg.getMenuReloadDelay() / 1000.0));
                    }
                }
            });
        }
        //Check: Use OS icons
        {
            checkUseOsIcons = new Button(contanerGeneral, SWT.CHECK | SWT.LEFT);
            FormData checkUseOsIconsLData = new FormData();
            checkUseOsIconsLData.width = 346;
            checkUseOsIconsLData.height = 22;
            checkUseOsIconsLData.left =  new FormAttachment(0, 1000, 24);
            checkUseOsIconsLData.top =  new FormAttachment(0, 1000, 114);
            checkUseOsIcons.setLayoutData(checkUseOsIconsLData);
            checkUseOsIcons.setText("Use operating system &icons");
            checkUseOsIcons.setToolTipText("Let Hawkscope look for Operating " +
            		"System icons? They may get resized with interpolation.");
            checkUseOsIcons.setSelection(cfg.useOsIcons());
        }
    }

    @Override
    public void hideObject() {
        dialogShell.dispose();
    }

    @Override
    public void showObject() {
        open();
    }
    
    private void showMoveWarnDialog() {
        MessageBox mb = new MessageBox(dialogShell, SWT.ICON_WARNING);
           mb.setMessage("Please select only one item to move!");
           mb.setText("Hey!");
           dialogShell.setEnabled(false);
           mb.open();
           dialogShell.setEnabled(true);
    } 
    
    private void saveConfiguration() {
        //general
        cfg.getProperties().put(Configuration.CHECK_FOR_UPDATES, 
                checkCheckforUpdates.getSelection() ? "1" : "0");
        cfg.getProperties().put(Configuration.MENU_RELOAD_DELAY, 
                "" + Math.round(Double.valueOf(textMenuReloadDelay.getText()) * 1000));
        cfg.getProperties().put(Configuration.USE_OS_ICONS, 
                checkUseOsIcons.getSelection() ? "1" : "0");
        //quick access
        StringBuilder quickAccess = new StringBuilder();
        for (String item : listQuickAccess.getItems()) {
            quickAccess.append(item.replaceFirst(".+<", "")
                    .replaceFirst(">", "").replaceAll("\\\\", "/"));
        }
        cfg.getProperties().put(Configuration.QUICK_ACCESS_LIST, 
                quickAccess.toString());
        //blacklist
        cfg.getProperties().put(Configuration.HIDDEN_FILES_DISPLAYED, 
                checkDisplayHidden.getSelection() ? "1" : "0");
        cfg.getProperties().put(Configuration.FLOPPY_DRIVES_DISPLAYED, 
                checkDisplayFloppy.getSelection() ? "1" : "0");
        StringBuilder blackList = new StringBuilder();
        for (String item : listBlacklist.getItems()) {
            blackList.append(item.replaceAll("\\\\", "/"));
            blackList.append(';');
        }
        cfg.getProperties().put(Configuration.FILESYSTEM_BLACKLIST, 
                blackList.toString());
        //network
        cfg.getProperties().put(Configuration.HTTP_PROXY_USE, 
                buttonUseHttpProxy.getSelection() ? "1" : "0");
        cfg.getProperties().put(Configuration.HTTP_PROXY_HOST, 
                textHttpProxyHost.getText());
        cfg.getProperties().put(Configuration.HTTP_PROXY_PORT, 
                textHttpProxyPort.getText());
        ConfigurationFactory.getConfigurationFactory().write(cfg);
    }

}
