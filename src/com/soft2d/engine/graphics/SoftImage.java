package com.soft2d.engine.graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.soft2d.engine.ext.*;

public class SoftImage {
	
	private Vector2 size;
	private int[] pixelData;
	public int alpha;
	public boolean drawLight;
	public String filePath;
	
	public SoftImage(String path) {	
		if(path != "") {
			drawLight = false;
			alpha = 255;
			BufferedImage image = null;
			
			try {
				image = ImageIO.read(Image.class.getResourceAsStream(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			size = new Vector2(image.getWidth(),image.getHeight());
			pixelData = image.getRGB(0, 0, (int)size.x, (int)size.y, null, 0, (int)size.x);
			image.flush();
			this.filePath = path;
		}
	}
	
	//Getters & Setters 
	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public int[] getPixelData() {
		return pixelData;
	}

	public void setPixelData(int[] pixelData) {
		this.pixelData = pixelData;
	}

	public String getFilePath() {
		return filePath;
	}
}
