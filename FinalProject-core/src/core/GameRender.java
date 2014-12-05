package core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import core.GameLogic;
import objects.GameObject;

public class GameRender {
	private SpriteBatch spriteBatch;
	private GameLogic logic;
	
	// Constructor
	public GameRender(GameLogic g)
	{
		logic = g;
		spriteBatch = new SpriteBatch(); 
	}
	
	public void render()
	{
		spriteBatch.begin();
		
		// Draw the grid
		logic.getGrid().draw(spriteBatch);
		
		// Draw game objects
		for(GameObject obj : logic.getGameObjects()){
			if(obj.isDrawable())
				obj.sprite.draw(spriteBatch);;
		}
		spriteBatch.end();
	}
}
