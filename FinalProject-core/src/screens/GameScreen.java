package screens;

import java.util.ArrayList;

import ui.UIElement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;

import core.Core;
import core.GameLogic;
import core.GameRender;

public class GameScreen implements Screen, InputProcessor {
	Core game;

	private GameLogic logic;
	private GameRender render;
	
	Music ambience;
	
	private ArrayList<UIElement> ui_elements;
	
	public GameScreen(Core game)
	{
		super();
		this.game = game;
		ui_elements = new ArrayList<UIElement>();
		
		logic 	= new GameLogic();
		render 	= new GameRender(logic);
		
		ambience = Gdx.audio.newMusic(Gdx.files.internal("music/ambience.ogg"));
	}
	
	@Override
	public void render(float deltaTime)
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		logic.update();
		render.render();
	}
	
	@Override
	public void resize(int width, int height)
	{
		
	}
	
	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(this);
		ambience.play();
	}
	
	@Override
	public void hide()
	{
		ambience.pause();
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
		ambience.dispose();
	}
	
	@Override
	public boolean keyDown(int keyCode)
	{
		return false;
	}
	
	@Override
	public boolean keyTyped(char character)
	{
		switch(character)
		{
		case 'p':
		case 'P':
			game.setScreen(new PauseScreen(game, render, this));
			break;
		}
		
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
			if(element.intersect(screenX, screenY))
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
