/*
 * Copyright (c) 2008-2009 Tomas Varaneckas
 * http://www.varaneckas.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.varaneckas.hawkscope.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.varaneckas.hawkscope.Version;
import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.util.IOUtils;
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * About Window
 * +------------------------------------------------------------------------+
 * | {@link #logo}       {@link #labelAppName}                              |
 * |                     {@link #labelAppSlogan}                            |
 * |                     {@link #labelVersion}    {@link #labelAppVersion}  |
 * |                     {@link #labelReleased}   {@link #labelAppReleased} |
 * |                     {@link #labelHomePage}   {@link #labelAppHomePage} |     
 * | {@link #labelEnvironment}                                              |
 * | {@link #textEnvironment}                                               |
 * |                    {@link #buttonCopyToClipboard} {@link #buttonClose} |
 * +------------------------------------------------------------------------+
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class AboutWindow extends AbstractWindow {
	
	/**
     * Canvas for drawing the logo
     * 
     * @see #createLogo()
     */
    private Canvas logo;
    
    /**
     * Label with application name
     * 
     * @see #createLabelAppName()
     */
    private Label labelAppName;
    
    /**
     * Label with application slogan
     * 
     * @see #createLabelAppSlogan()
     */
    private Label labelAppSlogan;
    
    /**
     * Label that says "Version"
     * 
     * @see #createLabelVersion()
     */
    private Label labelVersion;
    
    /**
     * Label that says "Released"
     * 
     * @see #createLabelReleased()
     */
    private Label labelReleased;
    
    /**
     * Label that says "Homepage"
     * 
     * @see #createLabelHomePage()
     */
    private Label labelHomePage;
    
    /**
     * Label with application version
     * 
     * @see #createLabelAppVersion()
     */
    private Label labelAppVersion;
    
    /**
     * Label with application release date
     * 
     * @see #createLabelAppReleased()
     */
    private Label labelAppReleased;
    
    /**
     * Label with application home page URL
     * 
     * @see #createLabelAppHomePage()
     */
    private Label labelAppHomePage;
    
    /**
     * Label that says "Environment"
     * 
     * @see #createLabelEnvironment()
     */
    private Label labelEnvironment;
    
    /**
     * Text area for displaying environmental information
     * 
     * @see #createTextEnvironment()
     */
    private Text textEnvironment;
    
    /**
     * Button for copying environmental report to clipboard
     * 
     * @see #createButtonCopyToClipboard()
     */
    private Button buttonCopyToClipboard;
    
    /**
     * Opens the window or makes it visible if it's hidden but not disposed
     */
    @Override
    public void open() {
        if (shell != null && !shell.isDisposed()) {
            shell.setVisible(true);
            shell.forceFocus();
            return;
        }       
        createShell("About");
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
        packAndSetMinSize();
        shell.open();
    }
    
    /**
     * Draws the logo on logo canvas
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
        logo.setCursor(SharedStyle.CURSOR_HAND);
        logo.setToolTipText("Click to visit Homepage");
        final FormData layout = SharedStyle.relativeTo(null, null);
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
        labelAppName.setLayoutData(SharedStyle.relativeTo(null, logo));
        labelAppName.setFont(SharedStyle.FONT_BOLD);
    }
    
    /**
     * Creates label with application slogan
     */
    private void createLabelAppSlogan() {
        labelAppSlogan = new Label(shell, SWT.NONE);
        labelAppSlogan.setLayoutData(SharedStyle.relativeTo(labelAppName, logo));
        labelAppSlogan.setText(Version.APP_SLOGAN);
    }
    
    /**
     * Creates label that says "Version"
     */
    private void createLabelVersion() {
        labelVersion = new Label(shell, SWT.NONE);
        labelVersion.setText("Version:");
        labelVersion.setFont(SharedStyle.FONT_BOLD);
        labelVersion.setLayoutData(SharedStyle.relativeTo(labelAppSlogan, logo));
    }
    
    /**
     * Creates label with application version
     */
    private void createLabelAppVersion() {
        labelAppVersion = new Label(shell, SWT.NONE);
        labelAppVersion.setText(Version.VERSION_NUMBER);
        labelAppVersion.setLayoutData(
        		SharedStyle.relativeTo(labelAppSlogan, labelHomePage));
        updateLabelAppVersion();
    }
    
    /**
     * Updates {@link #labelAppVersion} with current verson information
     */
    private void updateLabelAppVersion() {
        if (Version.isUpdateAvailable() == null) {
            if (ConfigurationFactory.getConfigurationFactory()
                    .getConfiguration().checkForUpdates()) {
                labelAppVersion.setToolTipText(
                		"Could not get version information.");
            }
        } else {
            if (Version.isUpdateAvailable()) {
                labelAppVersion.setForeground(SharedStyle.COLOR_RED);
                labelAppVersion.setText(Version.VERSION_NUMBER 
                        + " (Update Available!)");
                labelAppVersion.setToolTipText("Click to go to update " +
                        "download page");
                labelAppVersion.setCursor(SharedStyle.CURSOR_HAND);
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
                labelAppVersion.setForeground(SharedStyle.COLOR_GREEN);
            }
        }
    }
    
    /**
     * Creates label that says "Released"
     */
    private void createLabelReleased() {
        labelReleased = new Label(shell, SWT.NONE); 
        labelReleased.setText("Released:");
        labelReleased.setFont(SharedStyle.FONT_BOLD);
        labelReleased.setLayoutData(SharedStyle.relativeTo(labelVersion, logo));
    }
    
    /**
     * Creates label with application release date
     */
    private void createLabelAppReleased() {
        labelAppReleased = new Label(shell, SWT.NONE);
        labelAppReleased.setText(Version.VERSION_DATE);
        labelAppReleased.setLayoutData(SharedStyle
        		.relativeTo(labelVersion, labelHomePage));
    }
    
    /**
     * Creates label that says "Homepage"
     */
    private void createLabelHomePage() {
        labelHomePage = new Label(shell, SWT.NONE); 
        labelHomePage.setText("Homepage:");
        labelHomePage.setFont(SharedStyle.FONT_BOLD);
        labelHomePage.setLayoutData(SharedStyle.relativeTo(labelReleased, logo));
    }    
    
    /**
     * Creates label with application homepage URL
     */
    private void createLabelAppHomePage() {
        labelAppHomePage = new Label(shell, SWT.NONE);
        labelAppHomePage.setText(Version.HOMEPAGE);
        labelAppHomePage.setLayoutData(SharedStyle
        		.relativeTo(labelReleased, labelHomePage));
        labelAppHomePage.setCursor(SharedStyle.CURSOR_HAND);
        labelAppHomePage.setForeground(SharedStyle.COLOR_BLUE);
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
        labelEnvironment.setFont(SharedStyle.FONT_BOLD);
        labelEnvironment.setLayoutData(SharedStyle.relativeTo(logo, null));
    }

    /**
     * Creates button for copying environmental data report to clipboard
     */
    private void createButtonCopyToClipboard() {
        buttonCopyToClipboard = new Button(shell, SWT.PUSH);
        buttonCopyToClipboard.setText("Co&py to Clipboard");
        buttonCopyToClipboard.setLayoutData(SharedStyle
        		.relativeToBottomRight(buttonClose));
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
        final FormData layout = SharedStyle.relativeTo(labelEnvironment, null, 
        		buttonClose, null);
        layout.width = 500;
        layout.height = 150;
        textEnvironment.setLayoutData(layout);
    }    
    
}
