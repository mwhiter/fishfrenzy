package screens;

import java.util.ArrayList;

import ui.CallbackFunction;
import ui.UIElement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import core.Constants;
import core.Core;

public class HowToPlayScreen implements InputProcessor, Screen {

	Core game;
	private SpriteBatch spriteBatch;
	private Texture background;
	private ArrayList<UIElement> ui_elements;
	private MainMenuScreen menu;
	
	public HowToPlayScreen(MainMenuScreen menu, Core game)
	{
		super();
		this.game = game;
		this.menu = menu;
		spriteBatch = new SpriteBatch();
		ui_elements = new ArrayList<UIElement>();
	}
	
	private void InitMenuButtons()
	{	
		ui_elements.add(new UIElement("Return", Constants.WIDTH/2 - 70, 100, 140, 48));
		
		ui_elements.get(0).setCallbackFunction(new CallbackFunction() {
			public void func()
			{
				game.setScreen(menu);
				dispose();
			}
		});
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float arg0) {
		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0);
		for(UIElement element : ui_elements)
		{
			element.draw(spriteBatch);
		}
		
		renderText(spriteBatch);
		spriteBatch.end();
	}
	
	public void renderText(Batch batch)
	{	
		String sz1 = "Welcome to Fish Frenzy!";
		String sz2 = "The objective of the game is to lead the fish into your home base (Bottom left for Player 1, Bottom right for Player 2).";
		String sz3 = "In order to do this, you must place arrows on the grid in order to guide them home!";
		String sz4 = "W (up), A (left), S (down), D (right) keys will select a direction. Then click on the tile you want to place the arrow.";
		String sz5 = "You can only place an arrow on an empty tile, and it can't lead the fish into a solid wall.";
		String sz6 = "The winner is the player with the most fish after time runs out (or you hit the goal amount of fish).";
		String sz7 = "Coins appear on the map. If a fish swims over the coins, it will collect it. Capture this fish and you will capture the coin!";
		String sz8 = "Coins can be used to activate powerups.";
		String sz9 = "R (2 Coins - Remove Enemy Arrow), T (4 Coins - Freeze Enemy), Y (6 Coins - Double Score)";
				
		drawTextWithShadow(batch, sz1, Constants.WIDTH/2.0f, Constants.HEIGHT - 96.0f, 1, true);
		drawTextWithShadow(batch, sz2, Constants.WIDTH/2.0f, Constants.HEIGHT - 128.0f, 1, true);
		drawTextWithShadow(batch, sz3, Constants.WIDTH/2.0f, Constants.HEIGHT - 144.0f, 1, true);
		drawTextWithShadow(batch, sz4, Constants.WIDTH/2.0f, Constants.HEIGHT - 176.0f, 1, true);
		drawTextWithShadow(batch, sz5, Constants.WIDTH/2.0f, Constants.HEIGHT - 208.0f, 1, true);
		drawTextWithShadow(batch, sz6, Constants.WIDTH/2.0f, Constants.HEIGHT - 240.0f, 1, true);
		drawTextWithShadow(batch, sz7, Constants.WIDTH/2.0f, Constants.HEIGHT - 272.0f, 1, true);
		drawTextWithShadow(batch, sz8, Constants.WIDTH/2.0f, Constants.HEIGHT - 304.0f, 1, true);
		drawTextWithShadow(batch, sz9, Constants.WIDTH/2.0f, Constants.HEIGHT - 320.0f, 1, true);
	}
	
	private void drawTextWithShadow(Batch batch, String text, float x, float y, int shadowOffset, boolean centered)
	{
		float centerOffsetX = 0;
		BitmapFont font = new BitmapFont();
		
		if(centered) centerOffsetX = font.getBounds(text).width / 2;
		
		font.setColor(Color.BLACK);
		font.draw(batch, text, x - centerOffsetX - shadowOffset, y - shadowOffset);
		
		font.setColor(Color.WHITE);
		font.draw(batch, text, x - centerOffsetX, y);
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		background = new Texture("images/background.jpg");
		InitMenuButtons();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		// Loop through UIElements and determine if something needs to be done
		for(UIElement element : ui_elements)
		{
			// Did we intersect?
			if(element.intersect(screenX, Constants.HEIGHT-screenY))
			{
				if(element.callback())
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
