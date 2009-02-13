package com.varaneckas.hawkscope.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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

/**
 * About Window
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class AboutWindow {
    
	/**
	 * The Shell 
	 */
    private Shell shell;
    
    /**
     * Main layout
     */
    private FormData layout;
    
    /**
     * Bold font
     */
    private Font bold;
    
    /**
     * Red color
     */
    private Color red;
    
    /**
     * Green color
     */
    private Color green;
    
    /**
     * Blue color
     */
    private Color blue;
    
    /**
     * Hand cursor
     */
    private Cursor hand;
    
    /**
     * Canvas for drawing the logo
     */
    private Canvas logo;
    
    /**
     * Label with application name
     */
    private Label labelAppName;
    
    /**
     * Label with application slogan
     */
    private Label labelAppSlogan;
    
    /**
     * Label that says "Version"
     */
    private Label labelVersion;
    
    /**
     * Label that says "Released"
     */
    private Label labelReleased;
    
    /**
     * Label that says "Homepage"
     */
    private Label labelHomePage;
    
    /**
     * Label with application version
     */
    private Label labelAppVersion;
    
    /**
     * Label with application release date
     */
    private Label labelAppReleased;
    
    /**
     * Label with application home page URL
     */
    private Label labelAppHomePage;
    
    /**
     * Label that says "Environment"
     */
    private Label labelEnvironment;
    
    /**
     * Text area for displaying environmental information
     */
    private Text textEnvironment;
    
    /**
     * Button for copying environmental report to clipboard
     */
    private Button buttonCopyToClipboard;
    
    /**
     * Button for closing the window
     */
    private Button buttonClose;
    
    /**
     * Opens the window or makes it visible if it's hidden but not disposed
     */
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
    
    /**
     * Creates shared resources
     */
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
    
    /**
     * Releases shared resources
     */
    private void releaseResources() {
    	bold.dispose();
    	red.dispose();
    	green.dispose();
    	blue.dispose();
    	hand.dispose();
    }
    
    /**
     * Creates the main {@link Shell}
     */
    private void createShell() {
        shell = new Shell(TrayManager.getInstance().getShell(), SWT.SHELL_TRIM);
        final FormLayout layout = new FormLayout();
        layout.spacing = 6;
        layout.marginHeight = 12;
        layout.marginWidth = 12;
        shell.setMinimumSize(400, 300);
        shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(final DisposeEvent ev) {
				releaseResources();
			}
        });
        shell.setLocation(shell.getParent().toDisplay(100, 100));
        shell.setImage(IconFactory.getInstance()
                .getUncachedIcon("hawkscope16.png"));
        shell.setText("About");
        shell.setLayout(layout);
        shell.layout();
    }
    
    /**
     * Gets layout data ({@link FormData}) for a Widget that should be 
     * positioned relative to top-left corner.
     * 
     * @param top Control above the target Widget
     * @param left Control in the left of target Widget
     * @return layout data
     */
    private FormData relativeTo(final Control top, final Control left) {
        layout = new FormData();
        layout.top = new FormAttachment(top);
        layout.left = new FormAttachment(left);
        return layout;
    }
    
    /**
     * Gets layout data ({@link FormData}) for a Widget that should be 
     * positioned relative to botom-right corner.
     * 
     * @param right Control in the right of target Widget
     * @return layout data
     */
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
    
    /**
     * Draws the logo on logo canvas
     * 
     * @see #logo
     */
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
    
    /**
     * Creates label with application name
     */
    private void createLabelAppName() {
        labelAppName = new Label(shell, SWT.NONE);
        labelAppName.setText(Version.APP_NAME);
        labelAppName.setLayoutData(relativeTo(null, logo));
        labelAppName.setFont(bold);
    }
    
    /**
     * Creates label with application slogan
     */
    private void createLabelAppSlogan() {
        labelAppSlogan = new Label(shell, SWT.NONE);
        labelAppSlogan.setLayoutData(relativeTo(labelAppName, logo));
        labelAppSlogan.setText(Version.APP_SLOGAN);
    }
    
    /**
     * Creates label that says "Version"
     */
    private void createLabelVersion() {
        labelVersion = new Label(shell, SWT.NONE);
        labelVersion.setText("Version:");
        labelVersion.setFont(bold);
        labelVersion.setLayoutData(relativeTo(labelAppSlogan, logo));
    }
    
    /**
     * Creates label with application version
     */
    private void createLabelAppVersion() {
        labelAppVersion = new Label(shell, SWT.NONE);
        labelAppVersion.setText(Version.VERSION_NUMBER);
        labelAppVersion.setLayoutData(relativeTo(labelAppSlogan, labelHomePage));
        updateLabelAppVersion();
    }
    
    /**
     * Updates {@link #labelAppVersion} with current verson information
     */
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
    
    /**
     * Creates label that says "Released"
     */
    private void createLabelReleased() {
        labelReleased = new Label(shell, SWT.NONE); 
        labelReleased.setText("Released:");
        labelReleased.setFont(bold);
        labelReleased.setLayoutData(relativeTo(labelVersion, logo));
    }
    
    /**
     * Creates label with application release date
     */
    private void createLabelAppReleased() {
        labelAppReleased = new Label(shell, SWT.NONE);
        labelAppReleased.setText(Version.VERSION_DATE);
        labelAppReleased.setLayoutData(relativeTo(labelVersion, labelHomePage));
    }
    
    /**
     * Creates label that says "Homepage"
     */
    private void createLabelHomePage() {
        labelHomePage = new Label(shell, SWT.NONE); 
        labelHomePage.setText("Homepage:");
        labelHomePage.setFont(bold);
        labelHomePage.setLayoutData(relativeTo(labelReleased, logo));
    }    
    
    /**
     * Creates label with application homepage URL
     */
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
                shell.dispose();
            } 
        });
    }
    
    /**
     * Creates label that says "Environment"
     */
    private void createLabelEnvironment() {
        labelEnvironment = new Label(shell, SWT.NONE);
        labelEnvironment.setText("Environment");
        labelEnvironment.setFont(bold);
        labelEnvironment.setLayoutData(relativeTo(logo, null));
    }

    /**
     * Creates button for closing the window
     */
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
    
    /**
     * Creates button for copying environmental data report to clipboard
     */
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
    
    /**
     * Creates text area for displaying application environment info
     */
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
