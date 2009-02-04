package com.varaneckas.hawkscope.gui.swt;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.varaneckas.hawkscope.menu.state.StateEvent;
import com.varaneckas.hawkscope.util.OSUtils;

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
    
    public void handleEvent(final Event event) {
        try {
            final StateEvent se = findPopupMenuLocation();
            SWTMenuFactory.getMainMenu().getState().act(se);
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
        final Display d = Display.getDefault();
        final StateEvent se = new StateEvent();
        final Point loc = d.getCursorLocation();
        int x, y;
        x = loc.x;
        y = loc.y;
        if (!System.getProperty("os.name").toLowerCase().startsWith("win")) {
            final int traySize = OSUtils.getTraySize();
            //assume click is in the middle of the icon
            x = loc.x - traySize / 2;
            if (loc.y < traySize * 4) {
                //tray is on top side of the screen
                y = traySize;
            } else {
                //tray is on bottom side of the screen
                y = d.getBounds().height - traySize;
            }
        } else {
            loc.y -= 2;
        }
        se.setX(x);
        se.setY(y);
        return se;
    }

}
