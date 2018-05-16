package com.soft2d.engine;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import com.soft2d.engine.ext.Vector2;

class Axis {
	String name;
	int positive;
	int negative;
	int value;
}

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	
	private Engine engine;
	private boolean[] keys;
	private boolean[] keysLast;
	private boolean[] buttons;
	private boolean[] buttonsLast;
	
	private Vector2 mousePos;
	private int scrollWheel;
	
	private List<Axis>axises;
	
	public Input(Engine engine) {
		this.engine = engine;
		keys = new boolean[256];
		keysLast = new boolean[256];
		buttons = new boolean[5];
		buttonsLast = new boolean[5];
		
		mousePos = new Vector2(0,0);
		
		axises = new ArrayList<Axis>();
		engine.getWindow().getCanvas().addKeyListener(this);
		engine.getWindow().getCanvas().addMouseListener(this);
		engine.getWindow().getCanvas().addMouseMotionListener(this);
		engine.getWindow().getCanvas().addMouseWheelListener(this);
	}
	
	public void update() {
		for(int i = 0; i < keys.length; i++) {
			keysLast[i] = keys[i];
		}
		
		for(int i = 0; i < buttons.length; i++) {
			buttonsLast[i] = buttons[i];
		}
		
		updateAxis();
	}
	
	void updateAxis() {
		for(int i = 0; i < axises.size(); i++) {
			axises.get(i).value = 0;
			if(this.isKeyDown(axises.get(i).positive)) {
				if(axises.get(i).value != 1) {
					axises.get(i).value += 1;
				}
			}
			
			if(this.isKeyDown(axises.get(i).negative)) {
				if(axises.get(i).value != -1) {
					axises.get(i).value -= 1;
				}
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scrollWheel = e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePos.x = (int)(e.getX() / engine.getWindow().getScale());
		mousePos.y = (int)(e.getY() / engine.getWindow().getScale());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos.x = (e.getX() / engine.getWindow().getScale());
		mousePos.y = (e.getY() / engine.getWindow().getScale());	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}
	
	
	
	public boolean isKey(int keyCode) {
		return keys[keyCode] && !keysLast[keyCode];
	}
	
	public boolean isKeyDown(int keyCode) {
		return keys[keyCode];
	}
	
	public boolean isKeyUp(int keyCode) {
		return keysLast[keyCode] && !keys[keyCode];
	}
	
	public boolean isButton(int keyCode)
	{
		return buttons[keyCode];
	}
	
	public boolean isButtonUp(int keyCode)
	{
		return !buttons[keyCode] && buttonsLast[keyCode];
	}
	
	public boolean isButtonDown(int keyCode)
	{
		return buttons[keyCode] && !buttonsLast[keyCode];
	}
	
	
	public void addAxis(String axisName,int positive, int negative) {
		Axis axis = new Axis();
		axis.name = axisName;
		axis.positive = positive;
		axis.negative = negative;
		axises.add(axis);
	}
	
	public int getAxis(String axisName) {
		for(int i = 0; i < axises.size(); i++) {
			if(axises.get(i).name == axisName) {
				return axises.get(i).value;
			}
		}
		return 0;
	}
	
	public void invertAxis(String axisName) {
		for(int i = 0; i < axises.size(); i++) {
			if(axises.get(i).name == axisName) {
				int positive = axises.get(i).positive;
				axises.get(i).positive = axises.get(i).negative;
				axises.get(i).negative = positive;
			}
		}
	}
	
	public void removeAxis(String axisName) {
		for(int i = 0; i < axises.size(); i++) {
			if(axises.get(i).name == axisName) {
				Axis axisToRemove = axises.get(i);
				axisToRemove = null;
				axises.remove(i);
			}
		}
	}

	public Vector2 getMousePos() {
		if(engine.getRenderer().getCurrentActiveCamera() != null) {
			return new Vector2(mousePos.x + engine.getRenderer().getCurrentActiveCamera().positionCorner.x,mousePos.y + engine.getRenderer().getCurrentActiveCamera().positionCorner.y);
		}
		else
		{
			return mousePos;
		}
	}
}
