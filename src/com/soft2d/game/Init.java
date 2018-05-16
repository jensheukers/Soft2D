package com.soft2d.game;

import com.soft2d.engine.Engine;
import com.soft2d.engine.ext.Vector2;

public class Init {
	public static void main(String[] args) {
		Engine engine = new Engine();
		engine.start(new Vector2(1280,720),2,true);
	}
}
