package com.soft2d.engine.graphics;

import com.soft2d.engine.ext.Vector2;

public class Light {
	
	public Vector2 position;
	private float intensity;
	private float radius;
	private int zIndex;
	
	public Light(Vector2 position) {
		this.position = position;
		this.radius = 1f;
		this.intensity = 1f;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public int getZIndex() {
		return zIndex;
	}

	public void setZIndex(int zIndex) {
		this.zIndex = zIndex;
	}
}
