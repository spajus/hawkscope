package com.varaneckas.hawkscope.gui.swt;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;
import com.varaneckas.hawkscope.tray.TrayManagerFactory;

public class SWTTrayIconListener implements Listener {

    private static final Log log = LogFactory.getLog(SWTTrayIconListener.class);
    
    @Override
    public void handleEvent(Event event) {
        try {
            log.info(event);
            StateEvent se = new StateEvent();
            Point loc = ((SWTTrayManager) TrayManagerFactory.getTrayManager()).getDisplay().getCursorLocation();
            se.setX(loc.x);
            se.setY(loc.y);
            MenuFactory.getMenuFactory().getMainMenu().getState().act(se);
        } catch (Exception e) {
            log.error(e, e);
        }
    }

}
