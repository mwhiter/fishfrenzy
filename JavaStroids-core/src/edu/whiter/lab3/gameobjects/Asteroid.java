package edu.whiter.lab3.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import edu.whiter.lab3.game.Constants;

public class Asteroid extends GameObject implements Updatable{

	private float rotationalVel;
	
	public Asteroid(Texture tex){
		sprite = new Sprite(tex);
		sprite.setSize(Constants.ASTEROIDS_SIZE, Constants.ASTEROIDS_SIZE); 
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		setIsDrawable(true);
	}
	
	@Override
	public void update(float deltaTime) {
		sprite.rotate(getRotVel()); // TODO: Student, use delta time here
		
		// Student, create Asteroid behavior here.
		
	}
		
	public void setRotVel(float vel){
		rotationalVel = vel;
	}
	public float getRotVel(){
		return rotationalVel;
	}

}
