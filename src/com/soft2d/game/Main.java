package com.soft2d.game;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.soft2d.engine.*;
import com.soft2d.engine.ext.*;
import com.soft2d.engine.graphics.*;

public class Main extends Event {

	Engine engine;

	SoftObject o;
	public Main(Engine engine) {
		Camera cam = new Camera();
		cam.position = new Vector2(50,50);
		engine.setCurrentActiveCamera(cam);
		o = new SoftObject();
		o.setSoftImage(new SoftImage("/testbig.png"));
		o.position = new Vector2(50,50);
		engine.addSoftObject(o);
		o.getSoftImage().drawLight = true;
		
		Light l = new Light(new Vector2(150,150));
		engine.addLight(l);
		
	}
	
	public void frameEvent() {

	}
}
