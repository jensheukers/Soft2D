package com.soft2d.engine.ext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.soft2d.engine.Engine;
import com.soft2d.engine.graphics.*;

public class SoftScene {
	
	private String tag;
	private String path;
	private List<SoftObject>objects;
	private List<Light>lights;
	private Camera camera;
	private boolean loaded;
	private boolean dataLoaded;
	
	
	public SoftScene() {
		loaded = false;
		objects = new ArrayList<SoftObject>();
		lights = new ArrayList<Light>();
	}
	
	
	public void loadData() {
		
		if(path == null) {
			System.out.println("A path has to be set on SoftScene in order to load!");
		}
		
		if(tag == null) {
			System.out.println("A tag has to be set on SoftScene in order to load!");
		}
		
		String pathComplete = path + tag + ".Soft2DScene";
		List<String>lines = new ArrayList<String>();
		try {
			lines = Files.readAllLines(Paths.get(pathComplete));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(lines.size() != 0) {
			for(int i = 0; i < lines.size(); i++) {
				//Split line
				String data[] = lines.get(i).split("&");
				switch(data[0]) {
					case "Camera":
						this.camera = new Camera();
						this.camera.position = new Vector2(Float.parseFloat(data[1]),Float.parseFloat(data[2]));
						break;
					case "SoftObject":
						SoftObject obj = new SoftObject();
						obj.position = new Vector2(Float.parseFloat(data[1]),Float.parseFloat(data[2]));
						obj.setZ(Integer.parseInt(data[3]));
						obj.setSoftImage(new SoftImage(data[4]));
						obj.getSoftImage().alpha = Integer.parseInt(data[5]);
						obj.getSoftImage().drawLight = Boolean.parseBoolean(data[6]);
						
						objects.add(obj);
						break;
					case "Light":
						Light l = new Light(new Vector2(Float.parseFloat(data[1]),Float.parseFloat(data[2])));
						l.setZIndex(Integer.parseInt(data[3]));
						l.setIntensity(Float.parseFloat(data[4]));
						l.setRadius(Float.parseFloat(data[5]));
						
						lights.add(l);
						break;
				}
			}
		}
		dataLoaded = true;
	}
	
	public void unloadData() {
		for(int i = this.objects.size() - 1; i > -1; i--) {
			this.objects.remove(i);
		}
		
		for(int i = this.lights.size() - 1; i > -1; i--) {
			this.lights.remove(i);
		}
		
		this.camera = null;
		dataLoaded = false;
	}
	
	public void saveData() {
		
		if(path == null) {
			System.out.println("A path has to be set on SoftScene in order to save!");
		}
		
		if(tag == null) {
			System.out.println("A tag has to be set on SoftScene in order to save!");
		}
		
		path += tag + ".Soft2DScene";
		List<String>lines = new ArrayList<String>();
		
		if(this.camera != null) {
			String line = "Camera" + "&";
			line += this.camera.position.x + "&" + this.camera.position.y;
			lines.add(line);
		}
		
		for(int i = 0; i < objects.size(); i++) {
			SoftObject obj = objects.get(i);
			String line = "SoftObject" + "&"; 
			line += obj.position.x + "&" + obj.position.y + "&";
			line += obj.getZ() + "&" + obj.getSoftImage().getFilePath() + "&" + obj.getSoftImage().alpha + "&";
			line += obj.getSoftImage().drawLight;
			lines.add(line);
		}
		
		for(int i = 0; i < lights.size(); i++) {
			Light l = lights.get(i);
			String line = "Light" + "&";
			line += l.position.x + "&" + l.position.y + "&";
			line += l.getZIndex() + "&" + l.getIntensity() + "&" + l.getRadius();
			lines.add(line);
		}
		
		Path file = Paths.get(path);
		
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadScene(Engine engine) {
		if(loaded) {
			return;
		}
		
		for(int i = 0; i < objects.size(); i++) {
			engine.addSoftObject(objects.get(i));
		}
		
		for(int i = 0; i < lights.size(); i++) {
			engine.addLight(lights.get(i));
		}
		
		if(this.camera != null) {
			engine.setCurrentActiveCamera(this.camera);
		}
		loaded = true;
	}
	
	public void unLoadScene(Engine engine) {
		for(int i = engine.getSoftObjects().size() - 1; i > -1; i--) {
			engine.getSoftObjects().remove(i);
		}
		
		for(int i = engine.getLights().size() - 1; i > -1; i--) {
			engine.getLights().remove(i);
		}
		loaded = false;
	}
	
	public void constructScene(Engine engine) {
		if(dataLoaded) {
			System.out.println("Cannot construct scene, as scene already has loaded Data");
			return;
		}
		
		for(int i = engine.getSoftObjects().size() - 1; i > -1; i--) {
			objects.add(engine.getSoftObjects().get(i));
		}
		
		for(int i = engine.getLights().size() - 1; i > -1; i--) {
			lights.add(engine.getLights().get(i));
		}
		
		if(engine.getRenderer().getCurrentActiveCamera() != null) {
			this.camera = engine.getRenderer().getCurrentActiveCamera();
		}
	}
	
	public void addObject(SoftObject obj) {
		objects.add(obj);
	}
	
	public void addLight(Light l) {
		lights.add(l);
	}
	
	public void addCamera(Camera cam) {
		this.camera = cam;
	}
	
	public void removeObject(SoftObject obj) {
		for(int i = objects.size() - 1; i > -1 ; i++) {
			if(objects.get(i) == obj) {
				objects.remove(obj);
			}
		}
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public boolean isLoaded() {
		return loaded;
	}


	public boolean isDataLoaded() {
		return dataLoaded;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}
	
}
