package com.soft2d.editor.buttons;

import com.soft2d.engine.ui.SoftButton;

public class LoadButton extends SoftButton {
	
	public void onClick() {
		System.out.println("Clicked button");
	}
	
	public void onHover() {
		System.out.println("Hovering");
	}
	
	public boolean isClicked() {
		return isClicked;
	}
}
