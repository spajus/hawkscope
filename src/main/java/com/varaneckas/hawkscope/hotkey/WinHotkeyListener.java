package com.varaneckas.hawkscope.hotkey;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import com.melloware.jintellitype.HotkeyListener;
import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;

public class WinHotkeyListener implements HotkeyListener {
    public void onHotKey(int hk) {
        MenuFactory.getMainMenu().getSwtMenuObject()
                .getDisplay().syncExec(new Runnable() {
            public void run() {
                final StateEvent se = new StateEvent();
                final Point loc = Display.getDefault().getCursorLocation();
                se.setX(loc.x);
                se.setY(loc.y);
                MenuFactory.getMainMenu().getState().act(se);
            }
        });
    }
}
