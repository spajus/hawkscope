package com.varaneckas.hawkscope.gui.swing;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.menu.MainMenu;
import com.varaneckas.hawkscope.menu.MenuItem;
import com.varaneckas.hawkscope.menu.state.MenuClosedState;

/**
 * Main popup menu of Hawkscope application
 *
 * @author Tomas Varaneckas
 * @version $Id$
 */
public class SwingMainMenu extends MainMenu {
    
    private JPopupMenu menu;

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -5946207879630091657L;

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(SwingMainMenu.class);
    
    
    /* (non-Javadoc)
     * @see com.varaneckas.hawkscope.menu.MainMenu#forceHide()
     */
    public void forceHide() {
        setState(MenuClosedState.getInstance());
        menu.setVisible(false);
    }
    
    /**
     * Constructor
     */
    public SwingMainMenu() {
        menu = new JPopupMenu();
        menu.addMouseListener(new SwingTrayIconListener());
    }
    
    @Override
    public void clearMenu() {
        menu.removeAll();
    }
    
    @Override
    public void showMenu(int x, int y) {
        menu.setLocation(x, y);
        menu.setInvoker(menu);
        menu.setVisible(true);
        SwingUtilities.windowForComponent(menu).setAlwaysOnTop(true);
    }

    @Override
    public void addMenuItem(MenuItem item) {
        if (item != null) {
            menu.add(((SwingMenuItem) item).getMenuItem());
        } else {
            log.warn("Item is null");
        }
    }

    @Override
    public void addSeparator() {
        menu.addSeparator();
    }
}
