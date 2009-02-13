package com.varaneckas.hawkscope.gui;

public class SettingsWindow extends AbstractWindow {

	
	
	@Override
	public void open() {
		createShell("Test", 300, 200);
		createButtonClose();
		shell.open();
	}
	
	public static void main(String[] args) {
		new SettingsWindow().open();
	}

}
