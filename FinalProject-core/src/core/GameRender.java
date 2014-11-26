package core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import core.GameLogic;
import objects.Entity;

public class GameRender {
	private SpriteBatch spriteBatch;
	private GameLogic logic;
	
	// Constructor
	GameRender(GameLogic g)
	{
		logic = g;
		spriteBatch = new SpriteBatch(); 
	}
	
	public void render()
	{
		spriteBatch.begin();
		
		// Draw the grid
		logic.getGrid().draw(spriteBatch);
		
		// Draw entities
		for(Entity ent : logic.getEntities()){
			ent.sprite.draw(spriteBatch);
		}
		spriteBatch.end();
	}
}
