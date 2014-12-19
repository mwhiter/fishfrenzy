package screens;

import java.util.ArrayList;

import ui.CallbackFunction;
import ui.UIElement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
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
	
	public HowToPlayScreen(Core game)
	{
		super();
		this.game = game;
		spriteBatch = new SpriteBatch();
		ui_elements = new ArrayList<UIElement>();
	}
	
	private void InitMenuButtons()
	{	
		ui_elements.add(new UIElement("Return", Constants.WIDTH/2 - 70, 100, 140, 48));
		
		ui_elements.get(0).setCallbackFunction(new CallbackFunction() {
			public void func()
			{
				game.setScreen(new MainMenuScreen(game));
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
		BitmapFont font = new BitmapFont();
		
		String sz1 = "Welcome to Fish Frenzy!";
		String sz2 = "The objective of the game is to lead the fish into your home base (Bottom left for Player 1, Bottom right for Player 2).";
		String sz3 = "In order to do this, you must place arrows on the grid in order to guide them home!";
		String sz4 = "W (up), A (left), S (down), D (right) keys will select a direction. Then click on the tile you want to place the arrow.";
		String sz5 = "You can only place an arrow on an empty tile, and it can't lead the fish into a solid wall.";
		String sz6 = "The winner is the player with the most fish after time runs out (or you hit the goal amount of fish).";
		
		font.draw(batch, sz1, Constants.WIDTH/2 - (font.getBounds(sz1).width / 2), Constants.HEIGHT - 32);
		font.draw(batch, sz2, Constants.WIDTH/2 - (font.getBounds(sz2).width / 2), Constants.HEIGHT - 64);
		font.draw(batch, sz3, Constants.WIDTH/2 - (font.getBounds(sz3).width / 2), Constants.HEIGHT - 96);
		font.draw(batch, sz4, Constants.WIDTH/2 - (font.getBounds(sz4).width / 2), Constants.HEIGHT - 128);
		font.draw(batch, sz5, Constants.WIDTH/2 - (font.getBounds(sz5).width / 2), Constants.HEIGHT - 144);
		font.draw(batch, sz6, Constants.WIDTH/2 - (font.getBounds(sz6).width / 2), Constants.HEIGHT - 176);
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
