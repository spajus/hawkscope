package com.varaneckas.hawkscope.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.Updater;

public class InputDialog {
    
    public static void getString(final String prompt, 
            final int maxLength, 
            final Shell parent, 
            final Updater updater) {
        final Shell dialog = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        dialog.setImage(IconFactory.getInstance().getUncachedIcon(
                "hawkscope16.png"));
        dialog.setText("Input Dialog");

        FormLayout formLayout = new FormLayout();
        formLayout.marginWidth = 10;
        formLayout.marginHeight = 10;
        formLayout.spacing = 10;
        dialog.setLayout(formLayout);

        Label label = new Label(dialog, SWT.NONE);
        label.setText(prompt);
        FormData data = new FormData();
        label.setLayoutData(data);

        Button cancel = new Button(dialog, SWT.PUSH);
        cancel.setText("Cancel");
        data = new FormData();
        data.width = 60;
        data.right = new FormAttachment(100, 0);
        data.bottom = new FormAttachment(100, 0);
        cancel.setLayoutData(data);
        cancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) { 
                dialog.close();
            }
        });

        final Text text = new Text(dialog, SWT.BORDER);
        data = new FormData();
        data.width = 200;
        data.left = new FormAttachment(label, 0, SWT.DEFAULT);
        data.right = new FormAttachment(100, 0);
        data.top = new FormAttachment(label, 0, SWT.CENTER);
        data.bottom = new FormAttachment(cancel, 0, SWT.DEFAULT);
        text.setLayoutData(data);
        text.setTextLimit(maxLength);
        
        Button ok = new Button(dialog, SWT.PUSH);
        ok.setText("OK");
        data = new FormData();
        data.width = 60;
        data.right = new FormAttachment(cancel, 0, SWT.DEFAULT);
        data.bottom = new FormAttachment(100, 0);
        ok.setLayoutData(data);
        ok.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                updater.setValue(text.getText());
                dialog.close();
            }
        });
        dialog.setDefaultButton(ok);
        dialog.setTabList(new Control[] { text, ok, cancel });
        dialog.pack();
        dialog.open();
    }

}
