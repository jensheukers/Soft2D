package com.soft2d.editor;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.soft2d.editor.buttons.SaveButton;
import com.soft2d.engine.Engine;
import com.soft2d.engine.Event;
import com.soft2d.engine.ext.Vector2;
import com.soft2d.engine.graphics.Camera;
import com.soft2d.engine.graphics.SoftImage;
import com.soft2d.engine.ui.SoftButton;

public class Editor extends Event{
	private Camera camera;
	private Engine engine;
	
	public List<SoftButton>buttons;

	public Editor(Engine engine) {
		this.engine = engine;
		
		//Set lists
		buttons = new ArrayList<SoftButton>();
		
		//Create new Camera
		camera = new Camera();
		camera.position = new Vector2(0,0);
		
		//Add axises to Input
		engine.getInput().addAxis("Horizontal", KeyEvent.VK_D, KeyEvent.VK_A);
		engine.getInput().addAxis("Vertical", KeyEvent.VK_S, KeyEvent.VK_W);
		
		//Apply to engine
		engine.setCurrentActiveCamera(camera);
		engine.addEvent(this);
		
		
		SaveButton button = new SaveButton(this);
		engine.addEvent(button);
		engine.addEvent(button.getSoftCollider());
		engine.addSoftObject(button);

		
	}
	
	public void frameEvent(Engine engine) {
	
		//Update camera position every frame
		camera.position.add(new Vector2(engine.getInput().getAxis("Horizontal"),engine.getInput().getAxis("Vertical")));
	}

	public Engine getEngine() {
		return engine;
	}
}
