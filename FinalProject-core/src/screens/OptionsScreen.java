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

public class OptionsScreen implements Screen, InputProcessor {
	Core game;
	private SpriteBatch spriteBatch;
	private Texture background;
	private ArrayList<UIElement> ui_elements;
	private MainMenuScreen menu;
	
	public OptionsScreen(MainMenuScreen menu, Core game)
	{
		super();
		this.game = game;
		this.menu = menu;
		
		spriteBatch = new SpriteBatch();

		ui_elements = new ArrayList<UIElement>();
	}
	
	private void InitMenuButtons()
	{
		ui_elements.add(new UIElement("Unused", Constants.WIDTH/2 - 70, Constants.HEIGHT/2 - 64, 140, 48));
		ui_elements.add(new UIElement("Return", Constants.WIDTH/2 - 70, Constants.HEIGHT/2 - 128, 140, 48));
		
		UIElement unused = ui_elements.get(0);
		UIElement leaveOptions = ui_elements.get(1);
		
		unused.setCallbackFunction(new CallbackFunction() {
			public void func()
			{
			}
		});
		
		leaveOptions.setCallbackFunction(new CallbackFunction() {
			public void func()
			{
				game.setScreen(menu);
				dispose();
			}
		});
	}
	
	@Override
	public void render(float deltaTime)
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0);
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
