package com.varaneckas.hawkscope.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.menu.MainMenu;
import com.varaneckas.hawkscope.menu.state.MenuClosedState;
import com.varaneckas.hawkscope.tray.TrayManagerFactory;

/**
 * {@link MainMenu} - SWT implementation
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SWTMainMenu extends MainMenu {

    /**
     * Singleton instance
     */
    private static SWTMainMenu instance = null;

    /**
     * SWT Menu object
     */
    private final Menu menu;

    /**
     * Initializing singleton constructor
     */
    private SWTMainMenu() {
        menu = new Menu(((SWTTrayManager) TrayManagerFactory.getTrayManager())
                .getShell(), SWT.POP_UP);
        menu.addListener(SWT.Hide, new Listener() {
            @Override
            public void handleEvent(Event event) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        menu.getDisplay().syncExec(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(10l);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (!(state instanceof MenuClosedState)) {
                                    setState(MenuClosedState.getInstance());
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }

    /**
     * Singleton instance getter
     * 
     * @return instance of SWTMainMenu
     */
    public static SWTMainMenu getInstance() {
        if (instance == null) {
            instance = new SWTMainMenu();
        }
        return instance;
    }

    @Override
    public void clearMenu() {
        for (MenuItem item : menu.getItems()) {
            if (!item.isDisposed()) {
                item.dispose();
            }
        }
    }

    @Override
    public void forceHide() {
        setState(MenuClosedState.getInstance());
        menu.setVisible(false);
    }

    @Override
    public void showMenu(final int x, final int y) {
        menu.setLocation(x, y);
        menu.setVisible(true);
    }

    @Override
    public void addMenuItem(final com.varaneckas.hawkscope.menu.MenuItem item) {
        if (item instanceof SWTMenuItem) {
            ((SWTMenuItem) item).createMenuItem(menu);
        }
    }

    @Override
    public void addSeparator() {
        new MenuItem(menu, SWT.SEPARATOR);
    }

}
