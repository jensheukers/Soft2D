package com.soft2d.engine.ext;

public class Vector2 {
	public float x;
	public float y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static void add(Vector2 toChange, Vector2 other) {
		toChange.x += other.x;
		toChange.y += other.y;
	}
	
	public void add(Vector2 other) {
		this.x += other.x;
		this.y += other.y;
	}
	
	public static int getDistance(Vector2 first,Vector2 other) {
		float dx = first.x - other.x;
		float dy= first.y - other.y;
		return (int) Math.sqrt((dx*dx) + (dy*dy));
	}
	
}
