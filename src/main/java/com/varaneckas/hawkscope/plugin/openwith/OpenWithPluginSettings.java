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
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.PathUtils;
import com.varaneckas.hawkscope.util.Updater;

/**
 * Open With Plugin settings window supplements
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class OpenWithPluginSettings {

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
     * Settings tab item 
     */
    private CTabItem tabItem;
    
    /**
     * Label: "Preferred Applications"
     */
    private Label labelPreferredApps;
    
    /**
     * Container with {@link FormLayout} that this {@link CTabItem} uses
     */
    private Composite container;
    
    /**
     * Button that adds application to preferred app table
     */
    private Button buttonAdd;
    
    /**
     * Button that removes application from preferred app table
     */
    private Button buttonDel;


    /**
     * Loads the {@link Configuration} with values from this tab item. 
     * Refreshes the {@link OpenWithPlugin}.
     * 
     * @param cfg Configuration object
     */
    public void apply(final Configuration cfg) {
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

    /**
     * Enhances the {@link SettingsWindow}'s {@link CTabFolder} with OpenWith 
     * plugin's tab. 
     * 
     * @param cfg Configuration object
     * @param folder Settings {@link CTabFolder}
     */
    public void enhance(final Configuration cfg, final CTabFolder folder) {
        //presenting the cast in order of appearance:
        createTabItem(folder);
        createLabelSpecialApps();           
        createLabelFolderNav();
        createTextFolderNav(cfg);
        createLabelUnknownFiles();
        createTextUnknownFiles(cfg);
        createLabelPreferredApps();
        createTablePreferredApps(cfg);
        createButtonAddApp();
        createButtonDelApp();
    }

    /**
     * Creates button that deletes application from preferred app table
     * 
     * @see #buttonDel
     */
    private void createButtonDelApp() {
        buttonDel = new Button(container, SWT.PUSH
                | SWT.CENTER);
        final FormData layout = new FormData();
        layout.width = 39;
        layout.height = 29;
        layout.left = new FormAttachment(tablePreferred, 12);
        layout.top = new FormAttachment(buttonAdd, 12);
        buttonDel.setLayoutData(layout);
        buttonDel.setText("-");
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
     * Creates button that adds application to preferred app table
     * 
     * @see #buttonAdd
     */    
    private void createButtonAddApp() {
        buttonAdd = new Button(container, SWT.PUSH
                | SWT.CENTER);
        final FormData layout = new FormData();
        layout.width = 39;
        layout.height = 29;
        layout.left = new FormAttachment(tablePreferred, 12);
        layout.top = new FormAttachment(labelPreferredApps, 6);
        buttonAdd.setLayoutData(layout);
        buttonAdd.setText("+");
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
     * @param cfg Configuration object
     */
    private void createTablePreferredApps(final Configuration cfg) {
        tablePreferred = new Table(container, SWT.MULTI | SWT.BORDER
                | SWT.FULL_SELECTION | SWT.VERTICAL);
        final FormData laoyout = new FormData();
        laoyout.width = 273;
        laoyout.height = 56;
        laoyout.left = new FormAttachment(0, 12);
        laoyout.top = new FormAttachment(labelPreferredApps, 6);
        tablePreferred.setLinesVisible(true);
        tablePreferred.setHeaderVisible(true);
        tablePreferred.setLayoutData(laoyout);
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
     * Creates label that says "Preferred Applications"
     * 
     * @see #labelPreferredApps
     */
    private void createLabelPreferredApps() {
        labelPreferredApps = new Label(container, SWT.NONE);
        final FormData layout = new FormData();
        layout.width = 358;
        layout.height = 17;
        layout.left = new FormAttachment(0, 0);
        layout.top = new FormAttachment(textFileDef, 3);
        labelPreferredApps.setLayoutData(layout);
        labelPreferredApps.setText("Prefered Applications");
        labelPreferredApps.setFont(SWTResourceManager.getFont("Sans", 10,
                1, false, false));
    }

    /**
     * Creates text input for unknown file applications
     * 
     * @see #textFileDef
     * @param cfg Configuration object
     */
    private void createTextUnknownFiles(final Configuration cfg) {
        textFileDef = new Text(container, SWT.BORDER);
        final FormData layout = new FormData();
        layout.width = 200;
        layout.height = 17;
        layout.left = new FormAttachment(labelFolderNav, 6);
        layout.top = new FormAttachment(textFolderNav, 3);
        textFileDef.setLayoutData(layout);
        final String fileDef = cfg.getProperties()
                .get(OpenWithPlugin.PROP_UNKNOWN_FILE_APP);
        if (fileDef != null) {
            textFileDef.setText(fileDef);
        }
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
     * Creates label that says "Unknown files:"
     * 
     * @see #labelFileDef
     */
    private void createLabelUnknownFiles() {
        labelFileDef = new Label(container, SWT.NONE);
        final FormData layout = new FormData();
        layout.height = 17;
        layout.left = new FormAttachment(0, 12);
        layout.top = new FormAttachment(textFolderNav, 8);
        labelFileDef.setLayoutData(layout);
        labelFileDef.setText("Unknown files:");
    }

    /**
     * Creates text input for folder navigator application
     * 
     * @see #textFolderNav
     * @param cfg Configuration object
     */
    private void createTextFolderNav(final Configuration cfg) {
        textFolderNav = new Text(container, SWT.BORDER);
        final FormData layout = new FormData();
        layout.width = 200;
        layout.height = 17;
        layout.left = new FormAttachment(labelFolderNav, 6);
        layout.top = new FormAttachment(labelSpecialApps, 3);
        textFolderNav.setLayoutData(layout);
        final String folderNav = cfg.getProperties().get(
                OpenWithPlugin.PROP_FOLDER_NAVIGATOR);
        if (folderNav != null) {
            textFolderNav.setText(folderNav);
        }
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
     * Creates label that says "For directories:"
     * 
     * @see #labelFolderNav
     */
    private void createLabelFolderNav() {
        labelFolderNav = new Label(container, SWT.NONE);
        final FormData layout = new FormData();
        layout.height = 17;
        layout.left = new FormAttachment(0, 12);
        layout.top = new FormAttachment(labelSpecialApps, 8);
        labelFolderNav.setLayoutData(layout);
        labelFolderNav.setText("For directories:");
    }

    /**
     * Creates label that says "Special Applications"
     * 
     * @see #labelSpecialApps
     */
    private void createLabelSpecialApps() {
        labelSpecialApps = new Label(container, SWT.NONE);
        final FormData laout = new FormData();
        laout.width = 358;
        laout.height = 17;
        laout.left = new FormAttachment(0, 0);
        laout.top = new FormAttachment(0, 0);
        labelSpecialApps.setLayoutData(laout);
        labelSpecialApps.setText("Special Applications");
        labelSpecialApps.setFont(SWTResourceManager.getFont("Sans", 10,
                1, false, false));
    }

    /**
     * Creates {@link OpenWithPlugin} tab item in {@link SettingsWindow}'s 
     * {@link CTabFolder}.
     * 
     * @param folder Settings CTabFolder
     */
    private void createTabItem(final CTabFolder folder) {
        tabItem = new CTabItem(folder, SWT.NONE);
        container = new Composite(folder, SWT.NONE);
        final FormLayout layout = new FormLayout();
        layout.marginWidth = 12;
        layout.marginHeight = 12;
        container.setLayout(layout);
        tabItem.setControl(container);
        tabItem.setImage(IconFactory.getInstance().getIcon("open"));
        tabItem.setText("&Open With");
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
                if (ext == null || ext.length() == 0)
                    return;
                if (!ext.startsWith("."))
                    ext = "." + ext;
                tableItem.setText(0, ext);
                ExecutableInputDialog.open(
                        "Please enter executable for handling " + ext, null, 
                        tablePreferred.getShell(), new Updater() {
                    public void setValue(final String app) {
                        tableItem.setText(1, app);
                        tablePreferred.getColumn(0).pack();
                        tablePreferred.getColumn(1).pack();
                    }
                });
            }
        });
    }

}
