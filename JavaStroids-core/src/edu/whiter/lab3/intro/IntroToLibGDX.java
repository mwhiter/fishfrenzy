package edu.whiter.lab3.intro;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class IntroToLibGDX extends ApplicationAdapter{

	private SpriteBatch spriteBatch;
	private Sprite bug;
	private Sprite chest;
	private float rotDeg;
	
	// state 0 = top-right
	// state 1 = bottom-left
	// state 2 = rotating
	
	private Vector2 v;
	private int bugState;
	
	@Override
	public void create() {
		// Game Initialization  
		spriteBatch = new SpriteBatch(); 
		
		initBug();
		
		chest = new Sprite(new Texture("ChestClosed.png"));
		chest.setSize(50, 85);
		chest.setOrigin(chest.getWidth()/2, chest.getHeight()/2);
		chest.setPosition(270, 240);
		rotDeg = 3;
	}
	
	public void initBug()
	{
		bug = new Sprite(new Texture("EnemyBug.png"));
		bug.setSize(50, 85);
		bug.setOrigin(bug.getWidth() / 2, bug.getHeight() / 2);
		bug.setPosition(0, 0);

		// Set the vector towards the top of the screen and normalize it
		v = new Vector2(Gdx.graphics.getWidth() - (bug.getWidth()), Gdx.graphics.getHeight() - (bug.getHeight()));
		v.nor();
		v.scl(100);	// scale the vector so it moves 10 pixels per second
		
		bug.rotate(v.angle());
		bugState = 0;
	}

	@Override
	public void render() {
		// Game Loop
		Gdx.gl.glClearColor(0.7f, 0.7f, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		
		switch(bugState)
		{
		// Bug is moving towards the top-right
		case 0:
			bug.translate(v.x * Gdx.graphics.getDeltaTime(), v.y * Gdx.graphics.getDeltaTime());
			
			// State change once destination reached
			if(bug.getX() >= (Gdx.graphics.getWidth() - (bug.getWidth())) && bug.getY() >= Gdx.graphics.getHeight() - (bug.getHeight()))
			{
				bug.setPosition(Gdx.graphics.getWidth() - (bug.getWidth()), Gdx.graphics.getHeight() - (bug.getHeight()));
				bugState = 2;
			}
			break;
		// Bug is moving towards the bottom-left
		case 1:
			bug.translate(-1 * v.x * Gdx.graphics.getDeltaTime(), -1 * v.y * Gdx.graphics.getDeltaTime());
			
			if(bug.getX() <= 0 && bug.getY() <= 0)
			{
				bug.setPosition(0, 0);
				bugState = 2;
			}
			break;
		// Bug is rotating
		case 2:
			bug.rotate(rotDeg);
			
			while(bug.getRotation() >= 360)
				bug.setRotation(bug.getRotation() - 360);
			
			// Where are we at right now?
			boolean bTopRight = (bug.getX() == Gdx.graphics.getWidth() - (bug.getWidth()) && bug.getY() == Gdx.graphics.getHeight() - (bug.getHeight()));
			
			// Check which side of the screen we are on
			if(bTopRight)
			{
				// for top-right, we need to check if our angle is within a certain range of 180 degrees + the vector angle
				if(bug.getRotation() <= v.angle() + 180 + 3 && bug.getRotation() >= v.angle() + 180 - 3)
				{
					// on a state change, make sure our bug is in the correct position
					bugState = 1;
					bug.setPosition(Gdx.graphics.getWidth() - (bug.getWidth()), Gdx.graphics.getHeight() - (bug.getHeight()));
					bug.setRotation(v.angle() + 180);
				}
			}
			else
			{
				// for bottom-left, we need to check if our angle is within a certain range of the vector angle
				if(bug.getRotation() <= v.angle() + 3 && bug.getRotation() >= v.angle() - 3)
				{
					// on a state change, make sure our bug is in the correct position
					bugState = 0;
					bug.setPosition(0, 0);
					bug.setRotation(v.angle());
				}
			}
			break;
		default:
			break;
		}
		
		bug.draw(spriteBatch);
		chest.draw(spriteBatch);
		spriteBatch.end();
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		super.dispose();
	}
}
