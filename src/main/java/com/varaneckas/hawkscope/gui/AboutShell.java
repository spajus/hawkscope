package com.varaneckas.hawkscope.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.tray.TrayManager;
import com.varaneckas.hawkscope.util.IOUtils;
import com.varaneckas.hawkscope.util.IconFactory;

public class AboutShell {
    
    private Shell shell;
    private FormData layout;
    private Font bold;
    private Color red;
    private Color green;
    private Color blue;
    private Cursor hand;
    private Canvas logo;
    private Label labelAppName;
    private Label labelAppSlogan;
    private Label labelVersion;
    private Label labelReleased;
    private Label labelHomePage;
    private Label labelAppVersion;
    private Label labelAppReleased;
    private Label labelAppHomePage;
    private Label labelEnvironment;
    private Text textEnvironment;
    private Button buttonCopyToClipboard;
    private Button buttonClose;
    
    public void open() {
        if (shell != null && !shell.isDisposed()) {
            shell.setVisible(true);
            shell.forceFocus();
            return;
        }       
        createShell();
        createResources();
        createLogo();
        createLabelAppName();
        createLabelAppSlogan();
        createLabelVersion();
        createLabelReleased();
        createLabelHomePage();
        createLabelAppVersion();
        createLabelAppReleased();
        createLabelAppHomePage();
        createLabelEnvironment();
        createButtonClose();
        createButtonCopyToClipboard();
        createTextEnvironment();
        shell.pack();
        shell.open();
    }
    
    private void createResources() {
        final FontData data = new FontData();
        data.setHeight(10);
        data.setStyle(SWT.BOLD);
        bold = new Font(shell.getDisplay(), data);
        red = new Color(shell.getDisplay(), 255, 0, 0);
        green = new Color(shell.getDisplay(), 0, 128, 0);
        blue = new Color(shell.getDisplay(), 0, 0, 255);
        hand = new Cursor(shell.getDisplay(), SWT.CURSOR_HAND);
    }
    
    private void createShell() {
        shell = new Shell(TrayManager.getInstance().getShell(), SWT.SHELL_TRIM);
        final FormLayout layout = new FormLayout();
        layout.spacing = 6;
        layout.marginHeight = 12;
        layout.marginWidth = 12;
        shell.setLocation(shell.getParent().toDisplay(100, 100));
        shell.setImage(IconFactory.getInstance()
                .getUncachedIcon("hawkscope16.png"));
        shell.setText("About");
        shell.setLayout(layout);
        shell.layout();
    }
    
    private FormData relativeTo(final Control top, final Control left) {
        layout = new FormData();
        layout.top = new FormAttachment(top);
        layout.left = new FormAttachment(left);
        return layout;
    }
    
    private FormData relativeToBottomRight(final Control right) {
        layout = new FormData();
        layout.bottom = new FormAttachment(100, 0);
        if (right == null) {
            layout.right = new FormAttachment(100, 0);
        } else {
            layout.right = new FormAttachment(right);
        }
        return layout;
    }    
    
    private void createLogo() {
        logo = new Canvas(shell, SWT.NONE);
        logo.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                e.gc.drawImage(IconFactory.getInstance()
                        .getUncachedIcon("hawkscope128.png"), 0, 0);
            }
        });
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(MouseEvent event) {
                Program.launch(Version.HOMEPAGE);
            } 
        });
        logo.setCursor(hand);
        logo.setToolTipText("Click to visit Homepage");
        layout = relativeTo(null, null);
        layout.width = 128;
        layout.height = 128;
        logo.setLayoutData(layout);
    }
    
    private void createLabelAppName() {
        labelAppName = new Label(shell, SWT.NONE);
        labelAppName.setText(Version.APP_NAME);
        labelAppName.setLayoutData(relativeTo(null, logo));
        labelAppName.setFont(bold);
    }
    
    private void createLabelAppSlogan() {
        labelAppSlogan = new Label(shell, SWT.NONE);
        labelAppSlogan.setLayoutData(relativeTo(labelAppName, logo));
        labelAppSlogan.setText(Version.APP_SLOGAN);
    }
    
    private void createLabelVersion() {
        labelVersion = new Label(shell, SWT.NONE);
        labelVersion.setText("Version:");
        labelVersion.setFont(bold);
        labelVersion.setLayoutData(relativeTo(labelAppSlogan, logo));
    }
    
    private void createLabelAppVersion() {
        labelAppVersion = new Label(shell, SWT.NONE);
        labelAppVersion.setText(Version.VERSION_NUMBER);
        labelAppVersion.setLayoutData(relativeTo(labelAppSlogan, labelHomePage));
        updateLabelAppVersion();
    }
    
    private void updateLabelAppVersion() {
        if (Version.isUpdateAvailable() == null) {
            if (ConfigurationFactory.getConfigurationFactory()
                    .getConfiguration().checkForUpdates()) {
                labelAppVersion.setToolTipText("Could not get version information.");
            }
        } else {
            if (Version.isUpdateAvailable()) {
                labelAppVersion.setForeground(red);
                labelAppVersion.setText(Version.VERSION_NUMBER 
                        + " (Update Available!)");
                labelAppVersion.setToolTipText("Click to go to update " +
                        "download page");
                labelAppVersion.setCursor(hand);
                labelAppVersion.addMouseListener(new MouseAdapter() {
                   @Override
                    public void mouseUp(final MouseEvent event) {
                       Program.launch(Version.DOWNLOAD_URL);
                       shell.dispose();
                    } 
                });
            } else {
                labelAppVersion.setText(Version.VERSION_NUMBER);
                labelAppVersion.setToolTipText("Latest available version!");
                labelAppVersion.setForeground(green);
            }
        }
    }
    
    private void createLabelReleased() {
        labelReleased = new Label(shell, SWT.NONE); 
        labelReleased.setText("Released:");
        labelReleased.setFont(bold);
        labelReleased.setLayoutData(relativeTo(labelVersion, logo));
    }
    
    private void createLabelAppReleased() {
        labelAppReleased = new Label(shell, SWT.NONE);
        labelAppReleased.setText(Version.VERSION_DATE);
        labelAppReleased.setLayoutData(relativeTo(labelVersion, labelHomePage));
    }
    
    private void createLabelHomePage() {
        labelHomePage = new Label(shell, SWT.NONE); 
        labelHomePage.setText("Homepage:");
        labelHomePage.setFont(bold);
        labelHomePage.setLayoutData(relativeTo(labelReleased, logo));
    }    
    
    private void createLabelAppHomePage() {
        labelAppHomePage = new Label(shell, SWT.NONE);
        labelAppHomePage.setText(Version.HOMEPAGE);
        labelAppHomePage.setLayoutData(relativeTo(labelReleased, labelHomePage));
        labelAppHomePage.setCursor(hand);
        labelAppHomePage.setForeground(blue);
        labelAppHomePage.setToolTipText("Click to open in browser");
        labelAppHomePage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(final MouseEvent event) {
                Program.launch(Version.HOMEPAGE);
            } 
        });
    }
    
    private void createLabelEnvironment() {
        labelEnvironment = new Label(shell, SWT.NONE);
        labelEnvironment.setText("Environment");
        labelEnvironment.setFont(bold);
        labelEnvironment.setLayoutData(relativeTo(logo, null));
    }

    private void createButtonClose() {
        buttonClose = new Button(shell, SWT.PUSH);
        buttonClose.setText("&Close");
        buttonClose.setLayoutData(relativeToBottomRight(null));
        buttonClose.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                shell.dispose();
            } 
         });
    }
    
    private void createButtonCopyToClipboard() {
        buttonCopyToClipboard = new Button(shell, SWT.PUSH);
        buttonCopyToClipboard.setText("C&opy to Clipboard");
        buttonCopyToClipboard.setLayoutData(relativeToBottomRight(buttonClose));
        buttonCopyToClipboard.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                IOUtils.copyToClipboard(Version.getEnvironmentReport());
            } 
         });
    }
    
    private void createTextEnvironment() {
        textEnvironment = new Text(shell, SWT.MULTI | SWT.WRAP 
                | SWT.V_SCROLL | SWT.BORDER);
        textEnvironment.setText(Version.getEnvironmentReport());
        textEnvironment.setEditable(false);
        layout = relativeTo(labelEnvironment, null);
        layout.right = new FormAttachment(100, 0);
        layout.bottom = new FormAttachment(buttonClose);
        layout.width = 500;
        layout.height = 150;
        textEnvironment.setLayoutData(layout);
    }    
    
}
