package com.varaneckas.hawkscope.plugin.openwith;

import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import com.varaneckas.hawkscope.gui.SettingsWindow;
import com.varaneckas.hawkscope.gui.WindowFactory;
import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.OSUtils;

public class OpenWithPluginSettings {

    public static void enhance(final TabFolder folder) {
        TabItem openWith = new TabItem(folder, SWT.NONE);
        Composite containerOpenWith = new Composite(folder, SWT.NONE);
        FormLayout containerOpenWithLayout = new FormLayout();
        containerOpenWith.setLayout(containerOpenWithLayout);
        openWith.setControl(containerOpenWith);
        openWith.setImage(IconFactory.getInstance().getIcon("open"));
        openWith.setText("&Open With");
        final Table tablePrefered;
        
        //Label: Preferred Applications
        {
            Label labelPreferredApps = new Label(containerOpenWith, SWT.NONE);
            FormData labelPrefAppsLData = new FormData();
            labelPrefAppsLData.width = 358;
            labelPrefAppsLData.height = 17;
            labelPrefAppsLData.left =  new FormAttachment(0, 1000, 12);
            labelPrefAppsLData.top =  new FormAttachment(0, 1000, 12);
            labelPreferredApps.setLayoutData(labelPrefAppsLData);
            labelPreferredApps.setText("Prefered Applications");
            labelPreferredApps.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
        }
        //Table: Prefered Apps
        {
            tablePrefered = new Table(containerOpenWith, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
            FormData tablePreferedLData = new FormData();
            tablePreferedLData.width = 273;
            tablePreferedLData.height = 126;
            tablePreferedLData.left =  new FormAttachment(0, 1000, 24);
            tablePreferedLData.top = new FormAttachment(0, 1000, 36);
            tablePrefered.setLinesVisible(true);
            tablePrefered.setHeaderVisible(true);
            tablePrefered.setLayoutData(tablePreferedLData);
            TableColumn colType = new TableColumn(tablePrefered, SWT.NONE);
            colType.setText("Type");
            colType.setWidth(5);
            TableColumn colApp = new TableColumn(tablePrefered, SWT.NONE);
            colApp.setText("Application");
            for (int i = 0; i < 128; i++) {
                TableItem item = new TableItem(tablePrefered, SWT.NONE);
                item.setText(0, "." + i);
                item.setText(1, "No App " + i);
            }
            colType.pack();
            colApp.pack();
        }
        //Button [+] (QA list)
        {
            Button buttonAdd = new Button(containerOpenWith, SWT.PUSH | SWT.CENTER);
            FormData buttonAddLData = new FormData();
            buttonAddLData.width = 39;
            buttonAddLData.height = 29;
            buttonAddLData.left =  new FormAttachment(0, 1000, 331);
            buttonAddLData.top =  new FormAttachment(0, 1000, 36);
            buttonAdd.setLayoutData(buttonAddLData);
            buttonAdd.setText("+");
            OSUtils.adjustButton(buttonAdd);
            buttonAdd.addListener(SWT.Selection, new Listener() {
                public void handleEvent(Event arg0) {
                    int index = 0;
                    if (tablePrefered.getSelectionCount() > 0) {
                        index = tablePrefered.getSelectionIndices()[0];
                    }
                    new TableItem(tablePrefered, SWT.NONE, index);
                    tablePrefered.clearAll();
                    tablePrefered.select(index);
                    tablePrefered.getVerticalBar().setSelection(0);
                }
            });
        }
        //Button [-] (QA list)
        {
            Button buttonDel = new Button(containerOpenWith, SWT.PUSH | SWT.CENTER);
            FormData buttonDelLData = new FormData();
            buttonDelLData.width = 39;
            buttonDelLData.height = 29;
            buttonDelLData.left =  new FormAttachment(0, 1000, 331);
            buttonDelLData.top =  new FormAttachment(0, 1000, 71);
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
        final TableEditor editor = new TableEditor (table);
        editor.horizontalAlignment = SWT.LEFT;
        editor.grabHorizontal = true;
        table.addListener (SWT.MouseDoubleClick, new Listener () {
            public void handleEvent (Event event) {
                Rectangle clientArea = table.getClientArea ();
                Point pt = new Point (event.x, event.y);
                int index = table.getTopIndex ();
                while (index < table.getItemCount ()) {
                    boolean visible = false;
                    final TableItem item = table.getItem (index);
                    for (int i=0; i<table.getColumnCount (); i++) {
                        Rectangle rect = item.getBounds (i);
                        if (rect.contains (pt)) {
                            final int column = i;
                            //executable
                            if (column == 1) {
                                FileDialog fd = new FileDialog(table.getShell());
                                fd.setFileName(item.getText(column));
                                String executable = fd.open();
                                if (executable != null) {
                                    item.setText(column, executable);
                                }
                                return;
                            }
                            final Text text = new Text (table, SWT.NONE);
                            Listener textListener = new Listener () {
                                public void handleEvent (final Event e) {
                                    switch (e.type) {
                                        case SWT.FocusOut:
                                            item.setText (column, text.getText ());
                                            text.dispose ();
                                            break;
                                        case SWT.Traverse:
                                            switch (e.detail) {
                                                case SWT.TRAVERSE_RETURN:
                                                    item.setText (column, text.getText ());
                                                    //FALL THROUGH
                                                case SWT.TRAVERSE_ESCAPE:
                                                    text.dispose ();
                                                    e.doit = false;
                                            }
                                            break;
                                    }
                                }
                            };
                            text.addListener (SWT.FocusOut, textListener);
                            text.addListener (SWT.Traverse, textListener);
                            editor.setEditor (text, item, i);
                            text.setText (item.getText (i));
                            text.selectAll ();
                            text.setFocus ();
                            return;
                        }
                        if (!visible && rect.intersects (clientArea)) {
                            visible = true;
                        }
                    }
                    if (!visible) return;
                    index++;
                }
            }
        });
        
    }

    public static void main(String[] args) {
        SettingsWindow sw = WindowFactory.getSettingsWindow();
        sw.showObject();
    }
    
}
