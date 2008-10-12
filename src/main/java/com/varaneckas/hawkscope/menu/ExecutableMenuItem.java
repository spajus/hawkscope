package com.varaneckas.hawkscope.menu;

public interface ExecutableMenuItem extends MenuItem {

    abstract public void setCommand(final Command command);
    
    abstract public Command getCommand();

}
