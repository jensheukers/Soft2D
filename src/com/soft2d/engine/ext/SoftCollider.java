package com.soft2d.engine.ext;
import com.soft2d.engine.Engine;
import com.soft2d.engine.Event;

public class SoftCollider extends Event{
	
	public SoftObject parent;
	public Vector2 dimensionsX;
	public Vector2 dimensionsY;
	
	public SoftCollider(SoftObject obj) {
		this.parent = obj;
	}
	
	public void frameEvent(Engine engine) {
		if(this.parent == null) {
			System.out.println("Parent = null");
			return;
		}
		
		if(this.parent.getSoftImage() == null) {
			System.out.println("SoftImage = null");
			return;
		}
		
		this.dimensionsX = new Vector2(parent.position.x,parent.getSoftImage().getSize().x);
		this.dimensionsY = new Vector2(parent.position.y, parent.getSoftImage().getSize().y);
	}
	
	public boolean inRange(Vector2 other) { //Check inrange
		if(this.dimensionsX == null || this.dimensionsY == null) {
			return false;
		}
		
		if(other.x > dimensionsX.x && other.x < dimensionsX.y && other.y > dimensionsY.x && other.y < dimensionsY.y) {
			return true;
		}
		
		return false;
	}
}
