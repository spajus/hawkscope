package com.varaneckas.hawkscope.tray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TrayItem;

import com.varaneckas.hawkscope.util.IconFactory;
import com.varaneckas.hawkscope.util.UncaughtExceptionHandler;

/**
 * {@link TrayManager} - SWT Implementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class TrayManager {
	
	private static final TrayManager instance = new TrayManager();
	
	private TrayManager() {}
	
	public static TrayManager getInstance() {
		return instance;
	}

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(TrayManager.class);
    
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
    
    public void load() {
        trayIcon = new TrayItem(d.getSystemTray(), SWT.NONE);
        trayIcon.setImage(IconFactory.getInstance().getTrayIcon());
        TrayIconListener listener = new TrayIconListener();
        trayIcon.addListener(SWT.Selection, listener);
        trayIcon.addListener(SWT.MenuDetect, listener);
        log.debug(trayIcon.getListeners(SWT.NONE).length);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                log.debug("Removing Tray Icon");
                IconFactory.getInstance().cleanup();
            }
        }, "icon-disposer-hook"));
        while (!sh.isDisposed()) {
            try {
                if (!d.readAndDispatch ()) {
                    d.sleep();
                }
            } catch (final Exception e) {
                new UncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
            }
        }
        d.dispose();
    }
}
