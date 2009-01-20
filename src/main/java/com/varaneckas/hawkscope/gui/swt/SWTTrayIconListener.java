package com.varaneckas.hawkscope.gui.swt;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Point;
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
            log.info(event);
            final StateEvent se = new StateEvent();
            final Point loc = ((SWTTrayManager) TrayManagerFactory
                    .getTrayManager()).getDisplay().getCursorLocation();
            se.setX(loc.x);
            se.setY(loc.y);
            MenuFactory.getMenuFactory().getMainMenu().getState().act(se);
        } catch (final Exception e) {
            log.error(e, e);
        }
    }

}
