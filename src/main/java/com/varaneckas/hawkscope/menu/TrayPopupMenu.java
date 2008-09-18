package com.varaneckas.hawkscope.menu;

import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.varaneckas.hawkscope.listeners.ExitActionListener;
import com.varaneckas.hawkscope.listeners.HideActionListener;
import com.varaneckas.hawkscope.listeners.TrayPopupMenuMouseListener;
public class TrayPopupMenu extends JPopupMenu {

    private static final long serialVersionUID = -5946207879630091657L;

    private static final Log log = LogFactory.getLog(TrayPopupMenu.class);
    
    private static final TrayPopupMenu instance = new TrayPopupMenu();
    
    private State state = MenuClosedState.getInstance();
    
    public static TrayPopupMenu getInstance() {
        return instance;
    }
    
    public void forceHide() {
        setState(MenuClosedState.getInstance());
        setVisible(false);
    }
    
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    private TrayPopupMenu() {
        File[] roots = File.listRoots();
        for (File root : roots) {
            log.info("Listing roots");
            final FolderMenu item = new FolderMenu(root);
            item.setText(root.getAbsolutePath());
            item.setIcon(IconFactory.getIcon("drive"));
            item.setToolTipText("" + root.getUsableSpace() / (1024*1024*1024) 
                    + "G free");
            add(item);
        }
        add(new JSeparator(SwingConstants.HORIZONTAL));
        JMenuItem hide = new JMenuItem("Hide");
        hide.addActionListener(new HideActionListener());
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitActionListener());
        addMouseListener(new TrayPopupMenuMouseListener());
        add(hide);
        add(exit);
    }
}
