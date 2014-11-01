package edu.whiter.lab3.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.whiter.lab3.gameobjects.GameObject;

public class Renderer {
	
	private SpriteBatch spriteBatch;
	private Controller control;
	BitmapFont font;
	
	public Renderer(Controller c){
		control = c;
		spriteBatch = new SpriteBatch(); 
		font = new BitmapFont();
	}
	
	public void render(){
		spriteBatch.begin();
		for(GameObject gObj : control.getDrawableObjects()){
			gObj.sprite.draw(spriteBatch);
		}
		spriteBatch.end();
	}

}
