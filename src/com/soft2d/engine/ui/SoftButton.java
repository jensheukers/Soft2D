package com.soft2d.engine.ui;

import java.awt.event.MouseEvent;

import com.soft2d.engine.Engine;
import com.soft2d.engine.Event;
import com.soft2d.engine.ext.SoftObject;
import com.soft2d.engine.ext.Vector2;
import com.soft2d.engine.graphics.SoftImage;

public class SoftButton extends SoftObject{
	
	protected boolean isClicked;

	public SoftButton() {
		position = new Vector2(0,0);
		this.addSoftCollider();
	}
	
	public void frameEvent(Engine engine) {
		if(this.getSoftCollider().inRange(engine.getInput().getMousePos())) {
			this.onHover();
		}
		else
		{
			this.onNotHover();
		}
		
		if(this.getSoftCollider().inRange(engine.getInput().getMousePos()) && engine.getInput().isButtonDown(MouseEvent.BUTTON1)) {
			this.onClick();
		}
	}
	
	public void onClick() {
	}
	
	public void onHover() {
	}
	
	public void onNotHover() {
	}
	
	public boolean isClicked() {
		return isClicked;
	}
}
