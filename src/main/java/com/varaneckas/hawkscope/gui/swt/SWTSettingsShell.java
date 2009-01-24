package com.varaneckas.hawkscope.gui.swt;
import com.cloudgarden.resource.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

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
public class SWTSettingsShell extends org.eclipse.swt.widgets.Dialog {

    private Shell dialogShell;
    private TabFolder settingsTabFolder;
    private TabItem tabNetwork;
    private Button checkDisplayFloppy;
    private Label labelBlacklisted;
    private Label labelPredefined;
    private Text textMenuReloadDelay;
    private Label labelReloadDelay;
    private Label labelMenu;
    private Button checkCheckforUpdates;
    private Label labelUpdates;
    private Composite contanerGeneral;
    private Button buttonRemoveFromBlacklist;
    private Button buttonAddToBlacklist;
    private List listBlacklist;
    private Button checkDisplayHidden;
    private Composite containerBlacklist;
    private Button buttonOK;
    private Button buttonCancel;
    private TabItem tabBlacklist;
    private TabItem tabGeneral;
    private TabItem tabQuickAccess;

    /**
     * Auto-generated main method to display this org.eclipse.swt.widgets.Dialog
     * inside a new Shell.
     */
    public static void main(String[] args) {
        try {
            Display display = Display.getDefault();
            Shell shell = new Shell(display);
            SWTSettingsShell inst = new SWTSettingsShell(shell, SWT.NULL);
            inst.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SWTSettingsShell(Shell parent, int style) {
        super(parent, style);
    }

    public void open() {
        try {
            Shell parent = getParent();
            dialogShell = new Shell(parent, SWT.DIALOG_TRIM
                    | SWT.APPLICATION_MODAL);

            {
                //Register as a resource user - SWTResourceManager will
                //handle the obtaining and disposing of resources
                SWTResourceManager.registerResourceUser(dialogShell);
            }
            
            
            dialogShell.setLayout(new FormLayout());
            dialogShell.layout();
            dialogShell.pack();
            dialogShell.setSize(394, 344);
            {
                buttonOK = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                FormData buttonOKLData = new FormData();
                buttonOKLData.width = 30;
                buttonOKLData.height = 29;
                buttonOKLData.left =  new FormAttachment(0, 1000, 293);
                buttonOKLData.top =  new FormAttachment(0, 1000, 281);
                buttonOK.setLayoutData(buttonOKLData);
                buttonOK.setText("OK");
            }
            {
                buttonCancel = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                FormData buttonCancelLData = new FormData();
                buttonCancelLData.width = 55;
                buttonCancelLData.height = 29;
                buttonCancelLData.left =  new FormAttachment(0, 1000, 329);
                buttonCancelLData.top =  new FormAttachment(0, 1000, 281);
                buttonCancel.setLayoutData(buttonCancelLData);
                buttonCancel.setText("Cancel");
            }
            {
                settingsTabFolder = new TabFolder(dialogShell, SWT.NONE);
                FormData settingsTabFolderLData = new FormData();
                settingsTabFolderLData.width = 382;
                settingsTabFolderLData.height = 243;
                settingsTabFolderLData.left =  new FormAttachment(0, 1000, 0);
                settingsTabFolderLData.top =  new FormAttachment(0, 1000, 5);
                settingsTabFolder.setLayoutData(settingsTabFolderLData);
                {
                    tabGeneral = new TabItem(settingsTabFolder, SWT.NONE);
                    tabGeneral.setText("General");
                    {
                        contanerGeneral = new Composite(settingsTabFolder, SWT.NONE);
                        FormLayout contanerGeneralLayout = new FormLayout();
                        contanerGeneral.setLayout(contanerGeneralLayout);
                        tabGeneral.setControl(contanerGeneral);
                        {
                            textMenuReloadDelay = new Text(contanerGeneral, SWT.BORDER);
                            FormData textMenuReloadDelayLData = new FormData();
                            textMenuReloadDelayLData.width = 46;
                            textMenuReloadDelayLData.height = 16;
                            textMenuReloadDelayLData.left =  new FormAttachment(0, 1000, 191);
                            textMenuReloadDelayLData.top =  new FormAttachment(0, 1000, 82);
                            textMenuReloadDelay.setLayoutData(textMenuReloadDelayLData);
                            textMenuReloadDelay.setText("3000");
                        }
                        {
                            labelReloadDelay = new Label(contanerGeneral, SWT.NONE);
                            FormData labelReloadDelayLData = new FormData();
                            labelReloadDelayLData.width = 188;
                            labelReloadDelayLData.height = 22;
                            labelReloadDelayLData.left =  new FormAttachment(0, 1000, 24);
                            labelReloadDelayLData.top =  new FormAttachment(0, 1000, 86);
                            labelReloadDelay.setLayoutData(labelReloadDelayLData);
                            labelReloadDelay.setText("Reload delay (seconds):");
                        }
                        {
                            labelMenu = new Label(contanerGeneral, SWT.NONE);
                            FormData labelMenuLData = new FormData();
                            labelMenuLData.width = 383;
                            labelMenuLData.height = 17;
                            labelMenuLData.left =  new FormAttachment(0, 1000, 12);
                            labelMenuLData.top =  new FormAttachment(0, 1000, 63);
                            labelMenu.setLayoutData(labelMenuLData);
                            labelMenu.setText("Menu");
                            labelMenu.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
                        }
                        {
                            checkCheckforUpdates = new Button(contanerGeneral, SWT.CHECK | SWT.LEFT);
                            FormData checkCheckforUpdatesLData = new FormData();
                            checkCheckforUpdatesLData.width = 371;
                            checkCheckforUpdatesLData.height = 22;
                            checkCheckforUpdatesLData.left =  new FormAttachment(0, 1000, 24);
                            checkCheckforUpdatesLData.top =  new FormAttachment(0, 1000, 35);
                            checkCheckforUpdates.setLayoutData(checkCheckforUpdatesLData);
                            checkCheckforUpdates.setText("Check For Updates");
                        }
                        {
                            labelUpdates = new Label(contanerGeneral, SWT.NONE);
                            FormData labelUpdatesLData = new FormData();
                            labelUpdatesLData.width = 383;
                            labelUpdatesLData.height = 17;
                            labelUpdatesLData.left =  new FormAttachment(0, 1000, 12);
                            labelUpdatesLData.top =  new FormAttachment(0, 1000, 12);
                            labelUpdates.setLayoutData(labelUpdatesLData);
                            labelUpdates.setText("Updates");
                            labelUpdates.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
                        }
                    }
                }
                {
                    tabQuickAccess = new TabItem(settingsTabFolder, SWT.NONE);
                    tabQuickAccess.setText("Quick Access");
                }
                {
                    tabBlacklist = new TabItem(settingsTabFolder, SWT.NONE);
                    tabBlacklist.setText("Blacklist");
                    {
                        containerBlacklist = new Composite(settingsTabFolder, SWT.NONE);
                        FormLayout containerBlacklistLayout = new FormLayout();
                        containerBlacklist.setLayout(containerBlacklistLayout);
                        tabBlacklist.setControl(containerBlacklist);
                        {
                            labelBlacklisted = new Label(containerBlacklist, SWT.NONE);
                            FormData labelBlacklistedLData = new FormData();
                            labelBlacklistedLData.width = 383;
                            labelBlacklistedLData.height = 17;
                            labelBlacklistedLData.left =  new FormAttachment(0, 1000, 12);
                            labelBlacklistedLData.top =  new FormAttachment(0, 1000, 91);
                            labelBlacklisted.setLayoutData(labelBlacklistedLData);
                            labelBlacklisted.setText("Blacklisted Locations");
                            labelBlacklisted.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
                        }
                        {
                            labelPredefined = new Label(containerBlacklist, SWT.NONE);
                            FormData labelPredefinedLData = new FormData();
                            labelPredefinedLData.width = 383;
                            labelPredefinedLData.height = 17;
                            labelPredefinedLData.left =  new FormAttachment(0, 1000, 12);
                            labelPredefinedLData.top =  new FormAttachment(0, 1000, 12);
                            labelPredefined.setLayoutData(labelPredefinedLData);
                            labelPredefined.setText("Predefined");
                            labelPredefined.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
                        }
                        {
                            buttonRemoveFromBlacklist = new Button(containerBlacklist, SWT.PUSH | SWT.CENTER);
                            FormData buttonRemoveFromBlacklistLData = new FormData();
                            buttonRemoveFromBlacklistLData.width = 43;
                            buttonRemoveFromBlacklistLData.height = 29;
                            buttonRemoveFromBlacklistLData.left =  new FormAttachment(0, 1000, 330);
                            buttonRemoveFromBlacklistLData.top =  new FormAttachment(0, 1000, 158);
                            buttonRemoveFromBlacklist.setLayoutData(buttonRemoveFromBlacklistLData);
                            buttonRemoveFromBlacklist.setText("-");
                        }
                        {
                            buttonAddToBlacklist = new Button(containerBlacklist, SWT.PUSH | SWT.CENTER);
                            FormData buttonAddToBlacklistLData = new FormData();
                            buttonAddToBlacklistLData.width = 43;
                            buttonAddToBlacklistLData.height = 29;
                            buttonAddToBlacklistLData.left =  new FormAttachment(0, 1000, 330);
                            buttonAddToBlacklistLData.top =  new FormAttachment(0, 1000, 117);
                            buttonAddToBlacklist.setLayoutData(buttonAddToBlacklistLData);
                            buttonAddToBlacklist.setText("+");
                        }
                        {
                            FormData listBlacklistLData = new FormData();
                            listBlacklistLData.width = 271;
                            listBlacklistLData.height = 69;
                            listBlacklistLData.left =  new FormAttachment(0, 1000, 26);
                            listBlacklistLData.top =  new FormAttachment(0, 1000, 116);
                            listBlacklist = new List(containerBlacklist, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
                            listBlacklist.setLayoutData(listBlacklistLData);
                        }
                        {
                            checkDisplayFloppy = new Button(containerBlacklist, SWT.CHECK | SWT.LEFT);
                            FormData checkDisplayFloppyLData = new FormData();
                            checkDisplayFloppyLData.width = 383;
                            checkDisplayFloppyLData.height = 22;
                            checkDisplayFloppyLData.left =  new FormAttachment(0, 1000, 29);
                            checkDisplayFloppyLData.top =  new FormAttachment(0, 1000, 63);
                            checkDisplayFloppy.setLayoutData(checkDisplayFloppyLData);
                            checkDisplayFloppy.setText("Display Floppy Drives (discouraged)");
                        }
                        {
                            checkDisplayHidden = new Button(containerBlacklist, SWT.CHECK | SWT.LEFT);
                            FormData checkDisplayHiddenLData = new FormData();
                            checkDisplayHiddenLData.width = 383;
                            checkDisplayHiddenLData.height = 22;
                            checkDisplayHiddenLData.left =  new FormAttachment(0, 1000, 29);
                            checkDisplayHiddenLData.top =  new FormAttachment(0, 1000, 35);
                            checkDisplayHidden.setLayoutData(checkDisplayHiddenLData);
                            checkDisplayHidden.setText("Display Hidden Files");
                        }
                    }
                }
                {
                    tabNetwork = new TabItem(settingsTabFolder, SWT.NONE);
                    tabNetwork.setText("Network");
                }
                settingsTabFolder.setSelection(0);
            }
            dialogShell.setLocation(getParent().toDisplay(100, 100));
            dialogShell.open();
            Display display = dialogShell.getDisplay();
            while (!dialogShell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
