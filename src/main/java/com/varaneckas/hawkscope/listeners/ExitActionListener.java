package com.varaneckas.hawkscope.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

}
