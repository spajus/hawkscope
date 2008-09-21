package com.varaneckas.hawkscope.menu;

import java.awt.event.MouseEvent;

public abstract class State {
    
    public abstract void act(MouseEvent event);
    
    public abstract void init();

}
