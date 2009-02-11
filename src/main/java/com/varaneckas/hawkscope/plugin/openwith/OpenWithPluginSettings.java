package com.varaneckas.hawkscope.plugin.openwith;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import com.varaneckas.hawkscope.cfg.Configuration;
import com.varaneckas.hawkscope.gui.ExecutableInputDialog;
import com.varaneckas.hawkscope.gui.InputDialog;
import com.varaneckas.hawkscope.gui.SettingsWindow;
import com.varaneckas.hawkscope.gui.WindowFactory;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.Updater;

public class OpenWithPluginSettings {

    private static Table tablePrefered;

    public static void apply(final Configuration cfg, final CTabFolder folder) {
        // cleanup
        List<String> toRemove = new ArrayList<String>();
        for (String app : cfg.getProperties().keySet()) {
            if (app.startsWith("plugin.openwith.type.")) {
                toRemove.add(app);
            }
        }
        // avoiding concurrent modification
        for (String app : toRemove) {
            cfg.getProperties().remove(app);
        }
        // then add
        for (TableItem item : tablePrefered.getItems()) {
            if (item.getText(0) != null && item.getText().length() > 0) {
                if (item.getText(1) != null && item.getText(1).length() > 0) {
                    cfg.getProperties().put(
                            "plugin.openwith.type." + item.getText(0),
                            item.getText(1));
                }
            }
        }
        OpenWithPlugin.refresh();
    }

    public static void enhance(final Configuration cfg, final CTabFolder folder) {
        final CTabItem openWith = new CTabItem(folder, SWT.NONE);
        Composite containerOpenWith = new Composite(folder, SWT.NONE);
        FormLayout containerOpenWithLayout = new FormLayout();
        containerOpenWithLayout.marginWidth = 12;
        containerOpenWithLayout.marginHeight = 12;
        containerOpenWith.setLayout(containerOpenWithLayout);
        openWith.setControl(containerOpenWith);
        openWith.setImage(IconFactory.getInstance().getIcon("open"));
        openWith.setText("&Open With");
        
        Label labelSpecialApps;
        //Label Special Apps
        {
            labelSpecialApps = new Label(containerOpenWith, SWT.NONE);
            FormData labelSpecialAppsLData = new FormData();
            labelSpecialAppsLData.width = 358;
            labelSpecialAppsLData.height = 17;
            labelSpecialAppsLData.left = new FormAttachment(0, 0);
            labelSpecialAppsLData.top = new FormAttachment(0, 0);
            labelSpecialApps.setLayoutData(labelSpecialAppsLData);
            labelSpecialApps.setText("Special Applications");
            labelSpecialApps.setFont(SWTResourceManager.getFont("Sans", 10,
                    1, false, false));           
        }
        Label labelFolderNav;
        //Label Folder Nav
        {
            labelFolderNav = new Label(containerOpenWith, SWT.NONE);
            FormData layout = new FormData();
            layout.height = 17;
            layout.left = new FormAttachment(0, 12);
            layout.top = new FormAttachment(labelSpecialApps, 8);
            labelFolderNav.setLayoutData(layout);
            labelFolderNav.setText("For directories:");
        }
        Text textFolderNav;
        //Text: folder nav
        {
            textFolderNav = new Text(containerOpenWith, SWT.BORDER);
            FormData textFolderNavLData = new FormData();
            textFolderNavLData.width = 150;
            textFolderNavLData.height = 17;
            textFolderNavLData.left = new FormAttachment(labelFolderNav, 6);
            textFolderNavLData.top = new FormAttachment(labelSpecialApps, 4);
            textFolderNav.setLayoutData(textFolderNavLData);
        }
        Button buttonChooseNav;
        //Button: Nav
        {
            buttonChooseNav = new Button(containerOpenWith, SWT.PUSH); 
            FormData layout = new FormData();
            layout.height = 29;
            layout.width = 80;
            layout.left = new FormAttachment(textFolderNav, 6);
            layout.top = new FormAttachment(labelSpecialApps, 3);
            buttonChooseNav.setLayoutData(layout);
            buttonChooseNav.setText("C&hoose");
        }
        
        
        Label labelPreferredApps;
        // Label: Preferred Applications
        {
            labelPreferredApps = new Label(containerOpenWith, SWT.NONE);
            FormData labelPrefAppsLData = new FormData();
            labelPrefAppsLData.width = 358;
            labelPrefAppsLData.height = 17;
            labelPrefAppsLData.left = new FormAttachment(0, 0);
            labelPrefAppsLData.top = new FormAttachment(textFolderNav, 4);
            labelPreferredApps.setLayoutData(labelPrefAppsLData);
            labelPreferredApps.setText("Prefered Applications");
            labelPreferredApps.setFont(SWTResourceManager.getFont("Sans", 10,
                    1, false, false));
        }
        // Table: Prefered Apps
        {
            tablePrefered = new Table(containerOpenWith, SWT.MULTI | SWT.BORDER
                    | SWT.FULL_SELECTION);
            FormData tablePreferedLData = new FormData();
            tablePreferedLData.width = 273;
            tablePreferedLData.height = 56;
            tablePreferedLData.left = new FormAttachment(0, 12);
            tablePreferedLData.top = new FormAttachment(labelPreferredApps, 6);
            tablePrefered.setLinesVisible(true);
            tablePrefered.setHeaderVisible(true);
            tablePrefered.setLayoutData(tablePreferedLData);
            TableColumn colType = new TableColumn(tablePrefered, SWT.NONE);
            colType.setText("Type");
            colType.setWidth(5);
            TableColumn colApp = new TableColumn(tablePrefered, SWT.NONE);
            colApp.setText("Application");
            // load apps
            for (String appEntry : cfg.getProperties().keySet()) {
                if (appEntry.startsWith("plugin.openwith.type.")) {
                    TableItem item = new TableItem(tablePrefered, SWT.NONE);
                    item.setText(0, appEntry.replaceFirst(
                            "plugin.openwith.type.", ""));
                    item.setText(1, cfg.getProperties().get(appEntry));
                }
            }

            colType.pack();
            colApp.pack();
        }
        Button buttonAdd;
        // Button [+] (QA list)
        {
            buttonAdd = new Button(containerOpenWith, SWT.PUSH
                    | SWT.CENTER);
            FormData buttonAddLData = new FormData();
            buttonAddLData.width = 39;
            buttonAddLData.height = 29;
            buttonAddLData.left = new FormAttachment(tablePrefered, 12);
            buttonAddLData.top = new FormAttachment(labelPreferredApps, 6);
            buttonAdd.setLayoutData(buttonAddLData);
            buttonAdd.setText("+");
            buttonAdd.addListener(SWT.Selection, new Listener() {
                public void handleEvent(Event arg0) {
                    int index = 0;
                    if (tablePrefered.getSelectionCount() > 0) {
                        index = tablePrefered.getSelectionIndices()[0];
                    }
                    tablePrefered.deselectAll();
                    final TableItem newTi = new TableItem(tablePrefered,
                            SWT.NONE, index);
                    tablePrefered.select(index);
                    tablePrefered.getVerticalBar().setSelection(0);
                    addApplication(folder, newTi);

                }

                
            });
        }
        Button buttonDel;
        // Button [-] (QA list)       
        {
            buttonDel = new Button(containerOpenWith, SWT.PUSH
                    | SWT.CENTER);
            FormData buttonDelLData = new FormData();
            buttonDelLData.width = 39;
            buttonDelLData.height = 29;
            buttonDelLData.left = new FormAttachment(tablePrefered, 12);
            buttonDelLData.top = new FormAttachment(buttonAdd, 12);
            buttonDel.setLayoutData(buttonDelLData);
            buttonDel.setText("-");
            buttonDel.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent event) {
                    for (TableItem s : tablePrefered.getSelection()) {
                        s.dispose();
                    }
                }
            });
        }
        new AppTableEditor(tablePrefered);
    }

    private static void addApplication(final CTabFolder folder,
            final TableItem newTi) {
        InputDialog.getString("Please enter file extension", 30,
                folder.getShell(), new Updater() {
                    public void setValue(String ext) {
                        if (ext == null || ext.length() == 0)
                            return;
                        if (!ext.startsWith("."))
                            ext = "." + ext;
                        newTi.setText(0, ext);
                        ExecutableInputDialog.getString(
                                "Please enter executable for handling "
                                        + ext, null, folder
                                        .getShell(), new Updater() {
                                    public void setValue(String app) {
                                        newTi.setText(1, app);
                                        tablePrefered.getColumn(0)
                                                .pack();
                                        tablePrefered.getColumn(1)
                                                .pack();
                                    }
                                });
                    }
                });
    }

    public static void main(String[] args) {
        SettingsWindow sw = WindowFactory.getSettingsWindow();
        sw.showObject();
    }

}
