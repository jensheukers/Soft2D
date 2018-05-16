package com.soft2d.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import com.soft2d.engine.ext.*;

public class Window {
	private Engine engine;
	
	private Vector2 size;
	private int scale;
	private String title;
	
	private JFrame frame;
	private BufferedImage image;
	private Canvas canvas;
	private BufferStrategy bufferStrategy;
	private Graphics graphics;
	
	public Window(Engine engine,Vector2 resolution, int scale) {
		this.engine = engine;
		
		size = new Vector2(resolution.x / scale,resolution.y / scale);
		this.scale = scale;
		title = "Soft2D Engine";
		
		image = new BufferedImage((int)size.x,(int)size.y, BufferedImage.TYPE_INT_RGB);
		canvas = new Canvas();
		Dimension dimension = new Dimension(Math.round(size.x) * scale, Math.round(size.y) * scale);
		canvas.setPreferredSize(dimension);
		canvas.setMaximumSize(dimension);
		canvas.setMinimumSize(dimension);
		
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();
	}

	public void update() {
		graphics.drawImage(image,0, 0, canvas.getWidth(), canvas.getHeight(), null);
		bufferStrategy.show();
	}
	
	
	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BufferedImage getImage() {
		return image;
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
