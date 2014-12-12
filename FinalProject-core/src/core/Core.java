package core;

import screens.GameScreen;
import screens.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

// Core class
public class Core extends Game {
	public MainMenuScreen menuScreen;
	public GameScreen gameScreen;
	
	@Override
	// Called when Application is created
	public void create()
	{
		menuScreen = new MainMenuScreen(this);
		setScreen(menuScreen);
	}
	
	@Override
	public void render()
	{
		getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	// Called when application is destroyed
	public void dispose()
	{
		
	}
}
