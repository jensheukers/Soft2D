package com.soft2d.engine.ext;

import com.soft2d.engine.Event;
import com.soft2d.engine.graphics.SoftImage;

public class SoftObject extends Event{
	
	public Vector2 position;
	
	private int zIndex;
	private SoftImage softImage;
	private SoftCollider softCollider;
	
	public SoftObject() {
		zIndex = 0;
	}

	//Getters & Setters
	
	public void setSoftImage(SoftImage image) {
		this.softImage = image;
	}
	
	public void setZ(int value) {
		this.zIndex = value;
	}

	public SoftImage getSoftImage() {
		return softImage;
	}

	public int getZ() {
		return zIndex;
	}
	
	public SoftCollider getSoftCollider() {
		return softCollider;
	}
	
	public void addSoftCollider() {
		this.softCollider = new SoftCollider(this);
	}
}
