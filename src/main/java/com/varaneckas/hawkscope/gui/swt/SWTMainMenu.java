package com.varaneckas.hawkscope.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.varaneckas.hawkscope.menu.MainMenu;
import com.varaneckas.hawkscope.menu.state.MenuClosedState;
import com.varaneckas.hawkscope.tray.TrayManagerFactory;

public class SWTMainMenu extends MainMenu {

    private static SWTMainMenu instance = null;

    private final Menu menu;

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

    public static SWTMainMenu getInstance() {
        if (instance == null) {
            instance = new SWTMainMenu();
        }
        return instance;
    }

    @Override
    public synchronized void clearMenu() {
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
    public void showMenu(int x, int y) {
        menu.setLocation(x, y);
        menu.setVisible(true);
    }

    @Override
    public void addMenuItem(com.varaneckas.hawkscope.menu.MenuItem item) {
        if (item instanceof SWTMenuItem) {
            ((SWTMenuItem) item).createMenuItem(menu);
        }
    }

    @Override
    public void addSeparator() {
        new MenuItem(menu, SWT.SEPARATOR);
    }

}
