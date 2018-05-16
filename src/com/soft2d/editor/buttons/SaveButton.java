package com.soft2d.editor.buttons;

import java.awt.event.MouseEvent;

import com.soft2d.editor.Editor;
import com.soft2d.engine.Engine;
import com.soft2d.engine.Event;
import com.soft2d.engine.ext.SoftObject;
import com.soft2d.engine.ext.Vector2;
import com.soft2d.engine.graphics.SoftImage;
import com.soft2d.engine.ui.SoftButton;

public class SaveButton extends SoftButton{
	
	protected boolean isClicked;
	
	protected Editor editor;
	public SaveButton(Editor editor) {
		this.setSoftImage(new SoftImage("/test2.png"));
		this.editor = editor;
		editor.buttons.add(this);
	}

	public void onClick() {
		if(editor.getEngine().getSoftSceneManager().getCurrentLoadedScene() != null) {
			String currentSceneTag = editor.getEngine().getSoftSceneManager().getCurrentLoadedScene().getTag();
			editor.getEngine().getSoftSceneManager().constructScene(currentSceneTag);
			editor.getEngine().getSoftSceneManager().saveSceneData(currentSceneTag);
		}
	}
	
	public void onHover() {
		this.getSoftImage().alpha = 255;
	}
	
	public void onNotHover() {
		this.getSoftImage().alpha = 200;
	}
	
	public boolean isClicked() {
		return isClicked;
	}
}
