package com.varaneckas.hawkscope.gui.swt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.gui.AboutWindow;
import com.varaneckas.hawkscope.util.IOUtils;
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * {@link AboutWindow} - SWT implementation 
 * 
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 * 
 * However, as this is Free Software, it's all right. :)
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTAboutShell extends org.eclipse.swt.widgets.Dialog 
        implements AboutWindow {
    
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(SWTAboutShell.class);

    private Shell dialogShell;
    private Canvas logoCanvas;
    private Label appNameLabel;
    private Label appSloganLabel;
    private Label appVersion;
    private Label appHomepageValue;
    private Button copyReportButton;
    private Button closeButton;
    private Label environmentLabel;
    private Text environmentTextArea;
    private Label appHomepageLabel;
    private Label appReleasedValue;
    private Label appReleasedLabel;
    private Label appVersionValue;

    /**
     * Initializing constructor
     * 
     * @param parent
     * @param style
     */
    public SWTAboutShell(final Shell parent, final int style) {
        super(parent, style);
    }

    /**
     * Opens the window
     */
    public void open() {
        if (dialogShell != null && !dialogShell.isDisposed()) {
            dialogShell.setVisible(true);
            return;
        }
        try {
            final Shell parent = getParent();
            dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
            dialogShell.setImage((Image) SWTIconFactory.getIconFactory()
                    .getUncachedIcon("hawkscope16.png"));
            dialogShell.setText("About");
            {
                //Register as a resource user - SWTResourceManager will
                //handle the obtaining and disposing of resources
                SWTResourceManager.registerResourceUser(dialogShell);
            }
            
            dialogShell.setLayout(new FormLayout());
            dialogShell.layout();
            dialogShell.pack();            
            dialogShell.setSize(516, 322);
            {
                copyReportButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                FormData copyReportButtonLData = new FormData();
                copyReportButtonLData.width = 125;
                copyReportButtonLData.height = 29;
                copyReportButtonLData.left =  new FormAttachment(0, 1000, 314);
                copyReportButtonLData.top =  new FormAttachment(0, 1000, 252);
                copyReportButton.setLayoutData(copyReportButtonLData);
                copyReportButton.addMouseListener(new MouseAdapter() {
                   @Override
                   public void mouseUp(MouseEvent event) {
                       IOUtils.copyToClipboard(Version.getEnvironmentReport());
                   } 
                });
                copyReportButton.setText("Copy to Clipboard");
            }
            {
                closeButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
                FormData closeButtonLData = new FormData();
                closeButtonLData.width = 47;
                closeButtonLData.height = 29;
                closeButtonLData.left =  new FormAttachment(0, 1000, 451);
                closeButtonLData.top =  new FormAttachment(0, 1000, 252);
                closeButton.setLayoutData(closeButtonLData);
                closeButton.addMouseListener(new MouseAdapter() {
                   @Override
                    public void mouseUp(MouseEvent event) {
                       dialogShell.setVisible(false);
                    } 
                });
                closeButton.setText("Close");
            }
            {
                environmentLabel = new Label(dialogShell, SWT.NONE);
                FormData environmentLabelLData = new FormData();
                environmentLabelLData.width = 486;
                environmentLabelLData.height = 17;
                environmentLabelLData.left =  new FormAttachment(0, 1000, 12);
                environmentLabelLData.top =  new FormAttachment(0, 1000, 127);
                environmentLabel.setLayoutData(environmentLabelLData);
                environmentLabel.setText("Environment");
                environmentLabel.setFont(SWTResourceManager.getFont("Sans", 10, 1));
            }
            {
                environmentTextArea = new Text(dialogShell, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
                FormData environmentTextAreaLData = new FormData();
                environmentTextAreaLData.width = 468;
                environmentTextAreaLData.height = 90;
                environmentTextAreaLData.left =  new FormAttachment(0, 1000, 12);
                environmentTextAreaLData.top =  new FormAttachment(0, 1000, 150);
                environmentTextArea.setLayoutData(environmentTextAreaLData);
                environmentTextArea.setText(Version.getSystemProperties());
                environmentTextArea.setEditable(false);
            }
            {
                appHomepageValue = new Label(dialogShell, SWT.NONE);
                appHomepageValue.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseUp(MouseEvent event) {
                        Program.launch(Version.HOMEPAGE);
                    } 
                });
                appHomepageValue.setCursor(new Cursor(dialogShell.getDisplay(), 
                        SWT.CURSOR_HAND));
                appHomepageValue.setForeground(
                        new Color(dialogShell.getDisplay(), 0, 0, 255));
                FormData appHomepageValueLData = new FormData();
                appHomepageValueLData.width = 242;
                appHomepageValueLData.height = 17;
                appHomepageValueLData.left =  new FormAttachment(0, 1000, 256);
                appHomepageValueLData.top =  new FormAttachment(0, 1000, 104);
                appHomepageValue.setLayoutData(appHomepageValueLData);
                appHomepageValue.setToolTipText("Click to open in browser");
                appHomepageValue.setText(Version.HOMEPAGE);
            }
            {
                appHomepageLabel = new Label(dialogShell, SWT.NONE);
                FormData appHomepageLabelLData = new FormData();
                appHomepageLabelLData.width = 94;
                appHomepageLabelLData.height = 17;
                appHomepageLabelLData.left =  new FormAttachment(0, 1000, 156);
                appHomepageLabelLData.top =  new FormAttachment(0, 1000, 104);
                appHomepageLabel.setLayoutData(appHomepageLabelLData);
                appHomepageLabel.setText("Homepage:");
                appHomepageLabel.setFont(SWTResourceManager.getFont("Sans", 10, 1));
            }
            {
                appReleasedValue = new Label(dialogShell, SWT.NONE);
                FormData appReleasedValueLData = new FormData();
                appReleasedValueLData.width = 242;
                appReleasedValueLData.height = 17;
                appReleasedValueLData.left =  new FormAttachment(0, 1000, 256);
                appReleasedValueLData.top =  new FormAttachment(0, 1000, 81);
                appReleasedValue.setLayoutData(appReleasedValueLData);
                appReleasedValue.setText(Version.VERSION_DATE);
            }
            {
                appReleasedLabel = new Label(dialogShell, SWT.NONE);
                FormData appReleasedLabelLData = new FormData();
                appReleasedLabelLData.width = 77;
                appReleasedLabelLData.height = 17;
                appReleasedLabelLData.left =  new FormAttachment(0, 1000, 156);
                appReleasedLabelLData.top =  new FormAttachment(0, 1000, 81);
                appReleasedLabel.setLayoutData(appReleasedLabelLData);
                appReleasedLabel.setText("Released:");
                appReleasedLabel.setFont(SWTResourceManager.getFont("Sans", 10, 1));
            }
            {
                appVersionValue = new Label(dialogShell, SWT.NONE);
                FormData appVersionValueLData = new FormData();
                appVersionValueLData.width = 242;
                appVersionValueLData.height = 17;
                appVersionValueLData.left =  new FormAttachment(0, 1000, 256);
                appVersionValueLData.top =  new FormAttachment(0, 1000, 58);
                appVersionValue.setLayoutData(appVersionValueLData);
                if (Version.isUpdateAvailable()) {
                    appVersionValue.setForeground(new Color(dialogShell
                            .getDisplay(), 255, 0, 0));
                    appVersionValue.setText(Version.VERSION_NUMBER 
                            + " (Update Available!)");
                    appVersionValue.setToolTipText("Click to go to update " +
                    		"download page");
                    appVersionValue.setCursor(new Cursor(dialogShell
                            .getDisplay(), SWT.CURSOR_HAND));
                    appVersionValue.addMouseListener(new MouseAdapter() {
                       @Override
                        public void mouseUp(MouseEvent event) {
                           Program.launch(Version.DOWNLOAD_URL);
                           hideObject();
                        } 
                    });
                } else {
                    appVersionValue.setText(Version.VERSION_NUMBER);
                    appVersionValue.setToolTipText("Latest available version!");
                    appVersionValue.setForeground(new Color(dialogShell
                            .getDisplay(), 0, 128, 0));
                }
            }
            {
                appVersion = new Label(dialogShell, SWT.NONE);
                FormData appVersionLData = new FormData();
                appVersionLData.width = 77;
                appVersionLData.height = 17;
                appVersionLData.left =  new FormAttachment(0, 1000, 156);
                appVersionLData.top =  new FormAttachment(0, 1000, 58);
                appVersion.setLayoutData(appVersionLData);
                appVersion.setText("Version:");
                appVersion.setFont(SWTResourceManager.getFont("Sans", 10, 1));
            }
            {
                appSloganLabel = new Label(dialogShell, SWT.WRAP);
                FormData appSloganLabelLData = new FormData();
                appSloganLabelLData.width = 342;
                appSloganLabelLData.height = 17;
                appSloganLabelLData.left =  new FormAttachment(0, 1000, 156);
                appSloganLabelLData.top =  new FormAttachment(0, 1000, 35);
                appSloganLabel.setLayoutData(appSloganLabelLData);
                appSloganLabel.setText(Version.APP_SLOGAN);
            }
            {
                appNameLabel = new Label(dialogShell, SWT.NONE);
                FormData appNameLabelLData = new FormData();
                appNameLabelLData.width = 342;
                appNameLabelLData.height = 17;
                appNameLabelLData.left =  new FormAttachment(0, 1000, 156);
                appNameLabelLData.top =  new FormAttachment(0, 1000, 12);
                appNameLabel.setLayoutData(appNameLabelLData);
                appNameLabel.setText("Hawkscope");
                appNameLabel.setFont(SWTResourceManager.getFont("Sans", 10, 1));
            }
            {
                final FormData logoCanvasLData = new FormData();
                logoCanvasLData.width = 114;
                logoCanvasLData.height = 109;
                logoCanvasLData.left =  new FormAttachment(0, 1000, 12);
                logoCanvasLData.top =  new FormAttachment(0, 1000, 12);
                logoCanvas = new Canvas(dialogShell, SWT.RESIZE);
                logoCanvas.addPaintListener(new PaintListener() {
                    @Override
                    public void paintControl(PaintEvent e) {
                        e.gc.drawImage((Image) IconFactory.getIconFactory()
                                .getUncachedIcon("hawkscope128.png"), 0, 0, 128, 
                                128, 0, 0, 114, 109);
                    }
                });
                logoCanvas.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseUp(MouseEvent event) {
                        Program.launch(Version.HOMEPAGE);
                    } 
                });
                logoCanvas.setCursor(new Cursor(dialogShell.getDisplay(), 
                        SWT.CURSOR_HAND));
                logoCanvas.setToolTipText("Click to visit Homepage");
                logoCanvas.setLayoutData(logoCanvasLData);
            }
            dialogShell.setLocation(getParent().toDisplay(100, 100));
            dialogShell.open();
            Display display = dialogShell.getDisplay();
            while (!dialogShell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
        } catch (final Exception e) {
            log.warn("Failure in SWT About Window implementation", e);
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
    
}
