package screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import core.Constants;
import core.Core;
import ui.CallbackFunction;
import ui.UIElement;

public class MainMenuScreen implements Screen, InputProcessor {
	Core game;
	private SpriteBatch spriteBatch;
	private Texture background;
	private Texture title;
	private ArrayList<UIElement> ui_elements;
	
	private long timeLimit;
	
	public MainMenuScreen(Core game)
	{
		super();
		this.game = game;
		
		spriteBatch = new SpriteBatch();
		timeLimit = 60000;
		
		ui_elements = new ArrayList<UIElement>();
	}
	
	private void InitMenuButtons()
	{
		ui_elements.add(new UIElement("Start Game!", Constants.WIDTH/2 - 70, Constants.HEIGHT/2 - 0, 140, 48));		// Start Game
		ui_elements.add(new UIElement("How to play", Constants.WIDTH/2 - 70, Constants.HEIGHT/2 - 64, 140, 48));	// How to play
		ui_elements.add(new UIElement("Options", Constants.WIDTH/2 - 70, Constants.HEIGHT/2 - 128, 140, 48));		// Options
		ui_elements.add(new UIElement("Exit", Constants.WIDTH/2 - 70, Constants.HEIGHT/2 - 192, 140, 48));			// End Game
		
		UIElement startGame = ui_elements.get(0);
		UIElement howToPlay = ui_elements.get(1);
		UIElement options = ui_elements.get(2);
		UIElement endGame = ui_elements.get(3);
		
		// Start Game button
		startGame.setCallbackFunction(new CallbackFunction() {
			public void func()
			{
				game.setScreen(new GameScreen(timeLimit, game));
				dispose();
			}
		});
		
		howToPlay.setCallbackFunction(new CallbackFunction() {
			public void func()
			{
				game.setScreen(new HowToPlayScreen(game));
				dispose();
			}
		});
		
		// Options button
		options.setCallbackFunction(new CallbackFunction() {
			public void func()
			{
				game.setScreen(new OptionsScreen(game));
				dispose();
			}
		});
		
		// End Game button
		endGame.setCallbackFunction(new CallbackFunction() {
			public void func()
			{
				Gdx.app.exit();
			}
		});
	}
	
	@Override
	public void render(float deltaTime)
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0);
		spriteBatch.draw(title, 280, Constants.HEIGHT - 200);
		for(UIElement element : ui_elements)
		{
			element.draw(spriteBatch);
		}
		spriteBatch.end();
	}
	
	@Override
	public void resize(int width, int height)
	{
		
	}
	
	@Override
	public void show()
	{
		title = new Texture("images/logo.png");
		background = new Texture("images/background.jpg");
		InitMenuButtons();
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void hide()
	{
		
	}
	
	@Override
	public void pause()
	{
		
	}
	
	@Override
	public void resume()
	{
		
	}
	
	@Override
	public void dispose()
	{
		
	}
	
	@Override
	public boolean keyDown(int keyCode)
	{
		return false;
	}
	
	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}
	
	@Override
	public boolean keyUp(int keyCode)
	{
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}
	
	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
	
	@Override
	// 7 points for the home team!!!!
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
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
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
}
