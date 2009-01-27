package com.varaneckas.hawkscope.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

public class MacTrayTest {

  private static final String MENU_ITEM_TEXT = "Menu Item";

  private static final String ICON_IMAGE = "icons/hawkscope16.png";

  public static void main(String[] args) {
    MacTrayTest test = new MacTrayTest();
    test.showTray();
  }

  public void showTray() {
    final Display display = new Display();
    final Shell shell = new Shell(display);
    Tray tray = display.getSystemTray();
    if (tray != null) {
      initTray(display, shell, tray);
    }
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();    
  }
  
  private void initTray(final Display display, final Shell shell, Tray tray) {
    Image image = new Image(display, MacTrayTest.class.getClassLoader()
        .getResourceAsStream(ICON_IMAGE));
    TrayItem item = new TrayItem(tray, SWT.NONE);
    item.setImage(image);
    final Menu menu = new Menu(shell, SWT.POP_UP);
    MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
    menuItem.setText(MENU_ITEM_TEXT);

    item.addSelectionListener(getSelectionListener(display, menu));
    item.addMenuDetectListener(getMenuDetectListener(display, menu));
  }

  private MenuDetectListener getMenuDetectListener(final Display display, final Menu menu) {
    return new MenuDetectListener() {
      public void menuDetected(MenuDetectEvent arg0) {
        menu.setLocation(display.getCursorLocation().x - 13, 26);
        menu.setVisible(true);
      }
    };
  }

  private SelectionListener getSelectionListener(final Display display, final Menu menu) {
    return new SelectionListener() {
      public void widgetDefaultSelected(SelectionEvent event) {
        widgetSelected(event);
      }
      public void widgetSelected(SelectionEvent event) {
        menu.setLocation(display.getCursorLocation().x - 13, 26);
        menu.setVisible(true);
      }
    };
  }
}
