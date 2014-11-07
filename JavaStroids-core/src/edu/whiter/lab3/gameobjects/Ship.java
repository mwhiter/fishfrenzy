package edu.whiter.lab3.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Ship extends GameObject implements Updatable{
	
	private Vector2 direction;
	private Vector2 targetDirection;
	
	public Ship(Texture texture, int x, int y) {
		sprite = new Sprite(texture);
		sprite.setOrigin(texture.getWidth()/2, texture.getHeight()/2);
		sprite.setPosition(x, y);
		direction = new Vector2(0, -1);
		targetDirection = new Vector2(0, -1);
		setIsDrawable(true);
	}
	
	public void face(Vector2 targetPos)	{
		targetDirection = targetPos;
	}
	
	@Override
	public void update(float deltaTime) {		
		Vector2 n = targetDirection.nor();
		double dot = direction.dot(n);
		double ang = Math.toDegrees(Math.acos(dot));
		double deg = ang * Gdx.graphics.getDeltaTime();
				
		if(direction.crs(n) > 0.0f)
			deg = -deg;
		
		direction.rotate(-1.0f * (float)deg);	// for some reason this vector needs to rotate opposite otherwise it picks the completely wrong spot to rotate to.
		sprite.rotate((float)deg);
	}

	public void moveForward(float deltaTime) {
//		Student, your code goes here		
		
	}

}
