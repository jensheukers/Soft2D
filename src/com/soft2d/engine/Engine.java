package com.soft2d.engine;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.soft2d.editor.Editor;
import com.soft2d.engine.ext.*;
import com.soft2d.engine.graphics.*;
import com.soft2d.game.Main;

public class Engine implements Runnable {
	
	private Thread thread;
	private boolean isRunning;
	private double updateCap = 1.0/60.0;
	
	private int fps;
	private int runTime;
	
	private Window window;
	private Renderer renderer;
	private Input input;
	private SoftSceneManager softSceneManager;
	
	
	private List<SoftObject>softObjects;	
	private List<Event>events;
	private List<Light>lights;
	private List<String>texts;
	
	public Engine() {
	}
	
	public void start(Vector2 windowDimensions,int scale,boolean editor) {
		softObjects = new ArrayList<SoftObject>();
		events = new ArrayList<Event>();
		lights = new ArrayList();
		
		window = new Window(this,windowDimensions,scale);
		renderer = new Renderer(this);
		input = new Input(this);
		softSceneManager = new SoftSceneManager(this);
		
		if(!editor) {
			Main main = new Main(this);
		}
		else
		{
			System.out.println("Editor starting...");
			Editor main = new Editor(this);
		}
				
		thread = new Thread(this);
		thread.run();
	}
	
	public void run() {
		isRunning = true;
		
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int frames = 0;
		fps = 0;
		runTime = 0;
				
		while(isRunning) {
			render = false;
			
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			frameTime += passedTime;
			unprocessedTime += passedTime;
			
			while(unprocessedTime >= updateCap) {
				unprocessedTime -= updateCap;
				render = true;
				
				if(frameTime >= 1.0) {
					runTime += 1;
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
			}
			
			if(render) {
				renderer.clear();
				frames++;
				this.update();
			}
			else
			{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void update() {	
		this.render();
		window.update();
		this.updateEvents();
		
		//Input should always be the last Update Call
		input.update();
	}
	
	public void addSoftObject(SoftObject softObject) {
		softObjects.add(softObject);
	}
	
	public void removeSoftObject(SoftObject softObject) {
		for(int i = softObjects.size() - 1; i > -1; i--) {
			if(softObjects.get(i) == softObject) {
				softObjects.remove(i);
			}
		}
	}
	
	public void addEvent(Event e) {
		events.add(e);
	}
		
	public void removeEvent(Event e) {
		for(int i = events.size() - 1; i > -1; i--) {
			if(events.get(i) == e) {
				events.remove(i);
			}
		}
	}
		
	public void addText(String text) {
		texts.add(text);
	}
	
	public void addLight(Light light) {
		lights.add(light);
	}
	
	public void removeLight(Light light) {
		for(int i = lights.size() - 1; i > -1; i--) {
			if(lights.get(i) == light) {
				lights.remove(i);
			}
		}
	}
	
	public void setCurrentActiveCamera(Camera cam) {
		this.getRenderer().setCurrentActiveCamera(cam);
		cam.setDimensions(this.getWindow().getSize());
		this.addEvent(cam);
	}
	
	public void render() {
		int currentZMax = 0;
		for(int i = 0; i < softObjects.size(); i++) {
			if(currentZMax < softObjects.get(i).getZ()) {
				currentZMax = softObjects.get(i).getZ();
			}
		}	
		for(int ii = 0; ii <= currentZMax; ii++) {
				for(int iii = 0; iii < softObjects.size(); iii++) {		
				if(softObjects.get(iii).getSoftImage() != null && softObjects.get(iii).getZ() == ii) {
					renderer.drawImage(softObjects.get(iii).getSoftImage(), softObjects.get(iii).position);
				}
			}
		}
	}
	
	public void updateEvents() {
		for(int i = 0; i < events.size(); i++) {
			events.get(i).frameEvent(this);
		}
	}
		
	//GETTERS & SETTERS
	public void setMaxFps(int amount) {
		this.updateCap = 1.0/amount;
	}
	
	public int getFps() {
		return fps;
	}
	
	public int getRunTime() {
		return runTime;
	}
	
	public Window getWindow() {
		return window;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Input getInput() {
		return input;
	}

	public List<SoftObject> getSoftObjects() {
		return softObjects;
	}

	public void setSoftObjects(List<SoftObject> softObjects) {
		this.softObjects = softObjects;
	}

	public List<Light> getLights() {
		return lights;
	}

	public SoftSceneManager getSoftSceneManager() {
		return softSceneManager;
	}
	
	public Camera getActiveCamera() {
		return this.getRenderer().getCurrentActiveCamera();
	}
}
