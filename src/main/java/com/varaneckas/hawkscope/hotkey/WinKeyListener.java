package com.varaneckas.hawkscope.hotkey;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import com.varaneckas.hawkscope.menu.MenuFactory;
import com.varaneckas.hawkscope.menu.state.StateEvent;

/**
 * Key listener for Windows
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class WinKeyListener extends GlobalHotkeyListener {

	private static final Log log = LogFactory.getLog(WinKeyListener.class);
	
	public WinKeyListener() {
        Display.getCurrent().asyncExec(new Runnable() {
            public void run() {
                try {
                	JIntellitype.getInstance().registerHotKey(1, 
                			InputEvent.CTRL_MASK, KeyEvent.VK_SPACE);
                	JIntellitype.getInstance().addHotKeyListener(getListener());
                } catch (Exception e) {
                    log.debug("Hotkey conflict!", e);
                    JIntellitype.getInstance().cleanUp();
                }
            }
        });
	}
	
	@Override
	protected void finalize() throws Throwable {
		JIntellitype.getInstance().cleanUp();
	}

	protected HotkeyListener getListener() {
		return new HotkeyListener() {
			public void onHotKey(int hk) {
				log.debug("Hotkey: " + hk);
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
		};
	}
	
}
