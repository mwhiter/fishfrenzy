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
		
		
		drawGame(spriteBatch);
		
		spriteBatch.end();
	}
	
	// time string in mm:ss:ms format
	private String getTimeString(long time)
	{
		String rtnString = "";
		
		Long ms = time;
		ms %= 1000;
		
		Long seconds = time / 1000;
		seconds %= 60;
		
		Long minutes = seconds / 60;
		
		if(time < 10000)
			rtnString = minutes.toString() + ":" + seconds.toString() + ":" + ms.toString();
		else
			rtnString  = minutes.toString() + ":" + seconds.toString();
		
		return rtnString;
	}
	
	private void drawGame(Batch batch)
	{
		BitmapFont font = new BitmapFont();
		
		String szTime = "Time Remaining: " + getTimeString(logic.getTimeRemaining());
		String szFish = "Fish Left: " + logic.getPlayerMostFish().getNumFishCaptured() + " (Player " + (logic.getPlayerMostFish().getID()+1) + ") / " + logic.getGoalFish();
		
		/*
		Float deltaTime = Gdx.graphics.getDeltaTime();
		String szDebug1 = deltaTime.toString();
		String szDebug2 = "Num objects: " + logic.getGameObjects().size();
		font.draw(batch, szDebug1, Constants.WIDTH/2 - (font.getBounds(szDebug1).width / 2), Constants.HEIGHT - 32);
		font.draw(batch, szDebug2, Constants.WIDTH/2 - (font.getBounds(szDebug2).width / 2), Constants.HEIGHT - 48);
		*/
		
		font.draw(batch, szTime, Constants.WIDTH/2 - (font.getBounds(szTime).width / 2), Constants.HEIGHT);
		font.draw(batch, szFish, Constants.WIDTH/2 - (font.getBounds(szFish).width / 2), Constants.HEIGHT - 16);
	}
	
	private void drawScore(Player player, Batch batch)
	{
		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);		
		
		String sz1 = "Player " + player.getID()+1;
		String sz2 = player.getCoins() + " Coins";
		String sz3 = player.getNumFishCaptured() + " Fish";
		
		font.draw(batch, sz1, player.getHome().getCenterX() - (font.getBounds(sz1).width / 2), player.getHome().getCenterY() + 24);
		font.draw(batch, sz2, player.getHome().getCenterX() - (font.getBounds(sz2).width / 2), player.getHome().getCenterY() - 8);
		font.draw(batch, sz3, player.getHome().getCenterX() - (font.getBounds(sz3).width / 2), player.getHome().getCenterY() + 8);
	}
	
	public void renderGameOver()
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
		
		
		drawGame(spriteBatch);
		drawGameOver(spriteBatch);
		
		spriteBatch.end();
	}
	
	private void drawGameOver(Batch batch)
	{
		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.setScale(2.0f);
		String sz1 = "GAME OVER!";
		String sz2 = "Player " + logic.getWinner().getID() + " wins!";
		String sz3 = "Press any key to continue.";
		
		font.draw(batch, sz1, Constants.WIDTH/2 - (font.getBounds(sz1).width / 2), Constants.HEIGHT/2);
		font.draw(batch, sz2, Constants.WIDTH/2 - (font.getBounds(sz2).width / 2), Constants.HEIGHT/2 - 50);
		font.draw(batch, sz3, Constants.WIDTH/2 - (font.getBounds(sz3).width / 2), Constants.HEIGHT/2 - 80);
	}
}
