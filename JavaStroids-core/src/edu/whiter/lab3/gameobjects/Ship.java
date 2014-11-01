package edu.whiter.lab3.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ship extends GameObject implements Updatable{
	
	public Ship(Texture texture, int x, int y) {
		sprite = new Sprite(texture);
		sprite.setOrigin(texture.getWidth()/2, texture.getHeight()/2);
		sprite.setPosition(x, y);
		setIsDrawable(true);
	}
	
	@Override
	public void update(float deltaTime) {
//		Student, your code goes here		
	}

	public void moveForward(float deltaTime) {
//		Student, your code goes here		
		
	}

}
