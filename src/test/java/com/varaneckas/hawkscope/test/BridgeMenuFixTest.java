package com.varaneckas.hawkscope.test;


//http://dev.eclipse.org/newslists/news.eclipse.platform.swt/msg33992.html
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class BridgeMenuFixTest {


    static Display display = new Display();
    static Shell shell = new Shell(display);
    static Composite c1 = new Composite(shell, SWT.BORDER);
    static Composite c2 = new Composite(shell, SWT.BORDER|SWT.EMBEDDED);


    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        shell.setLayout(new FillLayout());
        
        c1.addListener(SWT.MenuDetect, new Listener() {
            public void handleEvent(Event e) {
                System.out.println("SWT: c1-mouse " + e.x + " " + e.y);
                showMenu();
            }
        });
        
        c2.addListener(SWT.MenuDetect, new Listener() {
            public void handleEvent(Event e) {
                // this won't show
                System.out.println("SWT: c2-mouse");
                showMenu();
            }
        });
        Frame frame2 = SWT_AWT.new_Frame(c2);
                
        Panel panel = new Panel(new BorderLayout());
        frame2.add(panel);
        
        JPanel jPanel = new JPanel(new BorderLayout());
        panel.add(jPanel);
        jPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("AWT: f2-mouse");
                Runnable r = new Runnable() {
                    public void run() {
                        showMenu();
                    }
                };
                display.asyncExec(r);
            }
        });


        
//      Thread t = new Thread() {
//          public void run() {
//              while (true) {
//                  try {
//                      Thread.sleep(5000);
//                      showMenu();
//                  } catch (InterruptedException e) {
//                      e.printStackTrace();
//                  }
//              }
//          }
//      };
//      t.setDaemon(true);
//      t.start();
        
        shell.setBounds(10, 10, 200, 200);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        
        display.dispose();
    }


    private static void showMenu() {
        final MenuCreator mc = new MenuCreator() {
            public Menu create(Control parent) {
                Menu menu = new Menu(parent);
                MenuItem item = new MenuItem(menu, SWT.PUSH);
                item.addSelectionListener(new SelectionListener() {
                    public void widgetDefaultSelected(SelectionEvent arg0) {}
                    public void widgetSelected(SelectionEvent arg0) {
                        System.out.println("select");
                    }
                });


                item.setText("push it");
                return menu;
            }
        };


        Runnable r = new Runnable() {
            public void run() {
                GTKAWTBridgePopupFix.showMenu(mc);
            }
        };
        display.asyncExec(r);
    }
    
}

