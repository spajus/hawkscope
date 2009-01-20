package com.varaneckas.hawkscope.menu;

/**
 * Executable menu item interface
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public interface ExecutableMenuItem extends MenuItem {

    /**
     * Sets the command
     * 
     * @param command
     */
    abstract public void setCommand(final Command command);
    
    /**
     * Gets the command
     * 
     * @return
     */
    abstract public Command getCommand();

}
