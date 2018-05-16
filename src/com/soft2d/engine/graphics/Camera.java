package com.soft2d.engine.graphics;

import com.soft2d.engine.*;
import com.soft2d.engine.ext.*;

public class Camera extends Event{
	
	public Vector2 position;
	public Vector2 positionCorner;
	public Vector2 dimensions;
	
	public Camera() { 
		positionCorner = new Vector2(0,0);
	}
	
	public void frameEvent(Engine engine) {
		positionCorner.x = position.x - (dimensions.x / 2);
		positionCorner.y = position.y - (dimensions.y / 2);
	}

	public void setDimensions(Vector2 dimensions) {
		this.dimensions = dimensions;
		this.position = new Vector2(dimensions.x / 2,dimensions.y / 2);	
	}
}
