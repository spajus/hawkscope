package com.varaneckas.hawkscope.gui.swt;


import java.awt.Dimension;
import java.awt.SystemTray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;
import com.varaneckas.hawkscope.tray.TrayManagerFactory;

/**
 * Tray Icon listener for SWT GUI implementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTTrayIconListener implements Listener {

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(SWTTrayIconListener.class);
    
    @Override
    public void handleEvent(final Event event) {
        try {
            final StateEvent se = findPopupMenuLocation();
            MenuFactory.getMenuFactory().getMainMenu().getState().act(se);
        } catch (final Exception e) {
            log.error(e, e);
        }
    }

    /**
     * Finds popup menu location and makes a {@link StateEvent}
     * 
     * @return
     */
    private StateEvent findPopupMenuLocation() {
        final Display d = ((SWTTrayManager) TrayManagerFactory
                .getTrayManager()).getDisplay();
        final StateEvent se = new StateEvent();
        final Point loc = d.getCursorLocation();
        final Dimension traySize = SystemTray.getSystemTray().getTrayIconSize();
        int y = loc.y;
        //assume click is in the middle of the icon
        int x = loc.x - traySize.width / 2;
        if (loc.y < traySize.height + 2) {
            //tray is on top side of the screen
            y = traySize.height;
        } else {
            //tray is on bottom side of the screen
            y = d.getBounds().height - traySize.height;
        }
        se.setX(x);
        se.setY(y);
        return se;
    }

}
