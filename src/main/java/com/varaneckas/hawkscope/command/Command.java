package com.varaneckas.hawkscope.command;

/**
 * Executable Menu Command interface
 * 
 * @author Tomas Varaneckas
 * @version $Id$
 */
public interface Command {
    
    /**
     * Executes the command
     */
    public abstract void execute();
}
