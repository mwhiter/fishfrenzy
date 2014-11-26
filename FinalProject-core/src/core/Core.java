package core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

// Core class
public class Core extends ApplicationAdapter {
	// Using the same structure as JavaStroids for now, because LibGDX is relatively new...
	private GameLogic logic;
	private GameRender render;
	
	@Override
	// Called when Application is created
	public void create()
	{
		// Set up the core game components
		logic 	= new GameLogic();
		render 	= new GameRender(logic);
	}
	
	@Override
	// Called when application should render
	// note: I'm keeping the game loop like this because we'll just use getDeltaTime()
		// I actually don't like multiplying floats by getDeltaTime() because they could cause errors,
		// but that seems to be how libGDX devs do it, so I'll conform to them instead of making this 100x
		// more complicated and potentially breaking something
	public void render()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		logic.update();		// please remember if we are modifying variables that are updated over time to multiply by graphics.getDeltaTime()!
		render.render();	// render the game
	}
	
	@Override
	// Called when application is destroyed
	public void dispose()
	{
		
	}
}
