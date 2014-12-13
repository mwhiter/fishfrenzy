package core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import core.GameLogic;
import objects.GameObject;
import objects.Player;

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
				obj.sprite.draw(spriteBatch);
		}
		
		for(Player player : logic.getPlayers()){
			drawScore(player, spriteBatch);
		}
		
		spriteBatch.end();
	}
	
	private void drawScore(Player player, Batch batch)
	{
		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);		
		
		Integer numAlgae = player.getCoins();
		Integer numFish = player.getNumFishCaptured();
		
		font.draw(batch, "Coins: " + numAlgae.toString(), player.getHome().getCenterX() - 24, player.getHome().getCenterY());
		font.draw(batch, "Fish: " + numFish.toString(), player.getHome().getCenterX() - 24, player.getHome().getCenterY() + 16);
	}
}
