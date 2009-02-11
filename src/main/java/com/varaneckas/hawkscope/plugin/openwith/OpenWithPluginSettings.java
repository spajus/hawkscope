package com.varaneckas.hawkscope.plugin.openwith;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
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
import com.varaneckas.hawkscope.util.OSUtils;
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
        containerOpenWith.setLayout(containerOpenWithLayout);
        openWith.setControl(containerOpenWith);
        openWith.setImage(IconFactory.getInstance().getIcon("open"));
        openWith.setText("&Open With");

        // Label: Preferred Applications
        {
            Label labelPreferredApps = new Label(containerOpenWith, SWT.NONE);
            FormData labelPrefAppsLData = new FormData();
            labelPrefAppsLData.width = 358;
            labelPrefAppsLData.height = 17;
            labelPrefAppsLData.left = new FormAttachment(0, 1000, 12);
            labelPrefAppsLData.top = new FormAttachment(0, 1000, 12);
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
            tablePreferedLData.height = 126;
            tablePreferedLData.left = new FormAttachment(0, 1000, 24);
            tablePreferedLData.top = new FormAttachment(0, 1000, 36);
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
        // Button [+] (QA list)
        {
            Button buttonAdd = new Button(containerOpenWith, SWT.PUSH
                    | SWT.CENTER);
            FormData buttonAddLData = new FormData();
            buttonAddLData.width = 39;
            buttonAddLData.height = 29;
            buttonAddLData.left = new FormAttachment(0, 1000, 331);
            buttonAddLData.top = new FormAttachment(0, 1000, 36);
            buttonAdd.setLayoutData(buttonAddLData);
            buttonAdd.setText("+");
            OSUtils.adjustButton(buttonAdd);
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
        // Button [-] (QA list)
        {
            Button buttonDel = new Button(containerOpenWith, SWT.PUSH
                    | SWT.CENTER);
            FormData buttonDelLData = new FormData();
            buttonDelLData.width = 39;
            buttonDelLData.height = 29;
            buttonDelLData.left = new FormAttachment(0, 1000, 331);
            buttonDelLData.top = new FormAttachment(0, 1000, 71);
            buttonDel.setLayoutData(buttonDelLData);
            buttonDel.setText("-");
            OSUtils.adjustButton(buttonDel);
            buttonDel.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent event) {
                    for (TableItem s : tablePrefered.getSelection()) {
                        s.dispose();
                    }
                }
            });
        }
        createTableEditor(tablePrefered);
    }

    private static void createTableEditor(final Table table) {
        final TableEditor editor = new TableEditor(table);
        editor.horizontalAlignment = SWT.LEFT;
        editor.grabHorizontal = true;
        table.addListener(SWT.MouseDoubleClick, new Listener() {
            public void handleEvent(Event event) {
                Rectangle clientArea = table.getClientArea();
                Point pt = new Point(event.x, event.y);
                int index = table.getTopIndex();
                while (index < table.getItemCount()) {
                    boolean visible = false;
                    final TableItem item = table.getItem(index);
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        Rectangle rect = item.getBounds(i);
                        if (rect.contains(pt)) {
                            final int column = i;
                            // executable
                            if (column == 1) {
                                ExecutableInputDialog.getString(
                                        "Please enter executable", item
                                                .getText(column), table
                                                .getShell(), new Updater() {
                                            public void setValue(String value) {
                                                item.setText(column, value);
                                                table.getColumn(1).pack();
                                            }
                                        });
                                return;
                            }
                            final Text text = new Text(table, SWT.NONE);
                            Listener textListener = new Listener() {
                                public void handleEvent(final Event e) {
                                    switch (e.type) {
                                    case SWT.FocusOut:
                                        item.setText(column, text.getText());
                                        text.dispose();
                                        break;
                                    case SWT.Traverse:
                                        switch (e.detail) {
                                        case SWT.TRAVERSE_RETURN:
                                            item
                                                    .setText(column, text
                                                            .getText());
                                            // FALL THROUGH
                                        case SWT.TRAVERSE_ESCAPE:
                                            text.dispose();
                                            e.doit = false;
                                        }
                                        break;
                                    }
                                    table.getColumn(0).pack();
                                }
                            };
                            text.addListener(SWT.FocusOut, textListener);
                            text.addListener(SWT.Traverse, textListener);
                            editor.setEditor(text, item, i);
                            text.setText(item.getText(i));
                            text.selectAll();
                            text.setFocus();
                            return;
                        }
                        if (!visible && rect.intersects(clientArea)) {
                            visible = true;
                        }
                    }
                    if (!visible)
                        return;
                    index++;
                }
            }
        });

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
