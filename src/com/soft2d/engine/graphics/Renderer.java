package com.soft2d.engine.graphics;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import com.soft2d.engine.Engine;
import com.soft2d.engine.ext.Vector2;

public class Renderer {
	
	private Vector2 size;
	private int[] pixelData;
	private Engine engine;
	private Camera currentActiveCamera;
	
	public Renderer(Engine engine) {
		this.engine = engine;
		size = engine.getWindow().getSize();
		pixelData = ((DataBufferInt)engine.getWindow().getImage().getRaster().getDataBuffer()).getData();
	}
	
	public void setPixel(Vector2 pixelPos, int value, int alpha, boolean drawLight) {
		
		if(pixelPos.x >=  this.size.x ||pixelPos.y >= this.size.y || pixelPos.x < 0 || pixelPos.y < 0) {
			return;
		}
		
		int pixelalpha = ((value >> 24) & 0xff);
		
		if(drawLight) {
			alpha = calculateLights(pixelPos, alpha);
		}
		if(alpha < 10) {
			return;
		}
		
		if(alpha < 255) {
			pixelalpha = alpha;
		}
		
		if(pixelalpha == 255) {
			pixelData[(int)pixelPos.x + (int)pixelPos.y * (int)size.x] = value;
		}
		else
		{
			int pixelColor = pixelData[(int)pixelPos.x + (int)pixelPos.y * (int)size.x];
			
			int blendRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (pixelalpha / 255f));
			int blendGreen = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (pixelalpha / 255f));
			int blendBlue = (pixelColor & 0xff) - (int)(((pixelColor & 0xff) - (value & 0xff)) * (pixelalpha / 255f));
			
			pixelData[(int)pixelPos.x + (int)pixelPos.y * (int)size.x] = (255 << 24 | blendRed << 16 | blendGreen << 8 | blendBlue);
		}
		
		//Light color correction
		
	}
	
	public void drawImage(SoftImage image, Vector2 offset) {
		
		if(currentActiveCamera == null) {
			System.out.println("Soft2D Is trying to render a Object, but no camera was found. Make sure to initialize a Camera!");
			return;
		}
		
		if(currentActiveCamera.positionCorner.x < 0) {
			currentActiveCamera.position.x = currentActiveCamera.dimensions.x / 2;
		}
		
		if(currentActiveCamera.positionCorner.y < 0) {
			currentActiveCamera.position.y = currentActiveCamera.dimensions.y / 2;
		}
		
		offset = new Vector2(offset.x + (currentActiveCamera.positionCorner.x * -1),offset.y + (currentActiveCamera.positionCorner.y * -1));
		
		int alpha = image.alpha;
		boolean drawLight = image.drawLight;
		
		for(int x = 0; x < image.getSize().x; x++) {
			for(int y = 0; y < image.getSize().y; y++) {
				setPixel(new Vector2(x + offset.x,y + offset.y), image.getPixelData()[x + y * (int)image.getSize().x],alpha,drawLight);
			}
		}
	}
		
	public int calculateLights(Vector2 pixelPos, int alpha) {
		//Lighting handling;
		int nearestLight = -1;
		int distanceToNearestLight = 0;
		int alphaValue = 0;
		
		for(int i = 0; i < engine.getLights().size(); i++) {
			
			Light l = engine.getLights().get(i);
			Vector2 posWithOffset = new Vector2(l.position.x + (currentActiveCamera.positionCorner.x * -1), l.position.y + (currentActiveCamera.positionCorner.y * -1));
				
			int distance = Vector2.getDistance(posWithOffset, pixelPos);
						
			if(nearestLight == -1) {
				nearestLight = i;
				distanceToNearestLight = Vector2.getDistance(posWithOffset, pixelPos);
			}
			
			if(distanceToNearestLight > distance) {
				nearestLight = i;
				distanceToNearestLight = Vector2.getDistance(posWithOffset, pixelPos);
			}
		}
		
		
		
		if(nearestLight != -1) {
			
			Light l = engine.getLights().get(nearestLight);
			Vector2 posWithOffset = new Vector2(l.position.x + (currentActiveCamera.positionCorner.x * -1), l.position.y + (currentActiveCamera.positionCorner.y * -1));
		
			if(distanceToNearestLight < 0) {
				distanceToNearestLight *= -1;
			}
			
			if(distanceToNearestLight / l.getRadius() > 255 || distanceToNearestLight / l.getRadius() < -255) {
				return 0;
			}
			else {
				alphaValue = (int) (alpha - (distanceToNearestLight / l.getRadius()));
			}
			
			alpha = (int) (alphaValue * l.getIntensity());
		}
		
		return alpha;
	}
	
	
	public void clear() {
		for(int i = 0; i < pixelData.length; i++) {
			pixelData[i] = 0;
		}
	}

	public Camera getCurrentActiveCamera() {
		return currentActiveCamera;
	}

	public void setCurrentActiveCamera(Camera currentActiveCamera) {
		this.currentActiveCamera = currentActiveCamera;
	}
}
