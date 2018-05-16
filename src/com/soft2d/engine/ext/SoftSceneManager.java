package com.soft2d.engine.ext;

import java.util.ArrayList;
import java.util.List;

import com.soft2d.engine.Engine;

public class SoftSceneManager {
	
	Engine engine;
	private List<SoftScene>scenes;
	private SoftScene currentLoadedScene;
	
	public SoftSceneManager(Engine engine) {
		this.engine = engine;
		scenes = new ArrayList<SoftScene>();
	}
	
	public void addScene(String tag) {
		SoftScene scene = new SoftScene();
		scene.setTag(tag);
		scenes.add(scene);
	}
	
	public void removeScene(String tag) {
		for(int i = 0; i < scenes.size(); i++) {
			if(scenes.get(i).getTag() == tag) {
				SoftScene scene = scenes.get(i);
				if(scene.isLoaded()) {
					scene.unLoadScene(engine);
				}
				
				if(scene.isDataLoaded()) {
					scene.unloadData();
				}
				
				scenes.remove(i);
				scene = null;
			}
		}
	}	
	
	public void loadScene(String tag) {		
		for(int i = 0; i < scenes.size(); i++) {
			if(scenes.get(i).getTag() == tag) {
				if(currentLoadedScene == scenes.get(i)) {
					System.out.println("Scene is already loaded!");
					return;
				}
				
				if(currentLoadedScene != scenes.get(i) && currentLoadedScene != null) {
					currentLoadedScene.unLoadScene(engine);
					currentLoadedScene.unloadData();
				}
				
				scenes.get(i).loadData();
				scenes.get(i).loadScene(engine);
				currentLoadedScene = scenes.get(i);
			}
		}
	}
	
	public void saveSceneData(String tag) {
		for(int i = 0; i < scenes.size(); i++) {
			if(scenes.get(i).getTag() == tag) {
				scenes.get(i).saveData();
			}
		}
	}
	
	public void setPath(String tag,String path) {
		for(int i = 0; i < scenes.size(); i++) {
			if(scenes.get(i).getTag() == tag) {
				scenes.get(i).setPath(path);
			}
		}
	}
	
	public void constructScene(String tag) {
		for(int i = 0; i < scenes.size(); i++) {
			if(scenes.get(i).getTag() == tag) {
				scenes.get(i).constructScene(engine);
			}
		}
	}
	
	public void reloadCurrentScene() {
		if(currentLoadedScene == null) {
			return;
		}
		
		currentLoadedScene.unLoadScene(engine);
		currentLoadedScene.unloadData();
		currentLoadedScene.loadData();
		currentLoadedScene.loadScene(engine);
	}
	
	public SoftScene getCurrentLoadedScene() {
		return currentLoadedScene;
	}
}
