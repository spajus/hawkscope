package com.varaneckas.hawkscope.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TrayItem;

import com.varaneckas.hawkscope.tray.TrayManager;
import com.varaneckas.hawkscope.util.IconFactory;

public class SWTTrayManager implements TrayManager {

    private TrayItem trayIcon;
    
    private static final Display d = Display.getDefault();

    private static final Shell sh = new Shell(d);
    
    @Override
    public TrayItem getTrayIcon() {
        return trayIcon;
    }

    public Display getDisplay() {
        return d;
    }
    
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
        while (!d.isDisposed()) {
            if (!d.readAndDispatch ()) {
                d.sleep();
            }
        }
    }
}
