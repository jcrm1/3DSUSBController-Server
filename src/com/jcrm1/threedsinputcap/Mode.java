package com.jcrm1.threedsinputcap;

public enum Mode {
	MOUSE_MODE("mouse"),
	JOYSTICK_MODE("joystick");
	public final String optionText;
	private Mode(String optionText) {
		this.optionText = optionText;
	}
}
