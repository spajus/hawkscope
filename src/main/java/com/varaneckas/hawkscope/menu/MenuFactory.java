package com.varaneckas.hawkscope.menu;

import java.io.File;

import com.varaneckas.hawkscope.cfg.ConfigurationFactory;
import com.varaneckas.hawkscope.gui.swing.SwingMenuFactory;
import com.varaneckas.hawkscope.gui.swt.SWTMenuFactory;

public abstract class MenuFactory {
    
    private static MenuFactory instance = null;
    
    public static MenuFactory getMenuFactory() {
        if (instance == null) {
            if (ConfigurationFactory.getConfigurationFactory().getConfiguration()
                    .getGuiImplementation().equals("SWT")) {
                instance = new SWTMenuFactory();
            } else {
                instance = new SwingMenuFactory();
            }
        }
        return instance;   
    }

    public abstract MainMenu getMainMenu();

    public abstract FileMenuItem newFileMenuItem(final File file);
    
    public abstract ExecutableMenuItem newExecutableMenuItem();
    
    public abstract FolderMenu newFolderMenu(final File file);
    
}
