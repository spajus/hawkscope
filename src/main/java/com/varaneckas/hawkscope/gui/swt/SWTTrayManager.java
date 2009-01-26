package com.varaneckas.hawkscope.gui.swt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TrayItem;

import com.varaneckas.hawkscope.tray.TrayManager;
import com.varaneckas.hawkscope.util.IconFactory;

/**
 * {@link TrayManager} - SWT Implementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTTrayManager implements TrayManager {

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(SWTTrayManager.class);
    
    /**
     * Tray Icon object
     */
    private TrayItem trayIcon;
    
    /**
     * Display in use
     */
    private static final Display d = Display.getDefault();
    
    /**
     * Shell in use
     */
    private static final Shell sh = new Shell(d);
    
    @Override
    public TrayItem getTrayIcon() {
        return trayIcon;
    }

    /**
     * Gets the shell
     * 
     * @return
     */
    public Shell getShell() {
        return sh;
    }
    
    @Override
    public void load() {
        trayIcon = new TrayItem(d.getSystemTray(), SWT.NONE);
        trayIcon.setImage((Image) IconFactory.getIconFactory().getTrayIcon());
        SWTTrayIconListener listener = new SWTTrayIconListener();
        trayIcon.addListener (SWT.Selection, listener);
        trayIcon.addListener (SWT.MenuDetect, listener);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("Removing Tray Icon");
                IconFactory.getIconFactory().cleanup();
            }
        }, "icon-disposer-hook"));
        while (!sh.isDisposed()) {
            try {
                if (!d.readAndDispatch ()) {
                    d.sleep();
                }
            } catch (final Exception e) {
                new SWTUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
            }
        }
        d.dispose();
    }
}
