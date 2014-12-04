package objects;

import com.badlogic.gdx.graphics.Texture;

import environment.Tile;
import environment.Grid;

public class Algae extends Entity
{
	public boolean inGrid = true;
	
	// Constructor
	public Algae(Texture texture)
	{
		super(texture);
		init();
	}
	public Algae(Texture texture, Tile spawn)
	{
		super(texture, spawn.getCenterX(), spawn.getCenterY());
		spawn.setHasCoin(true);
		init();
	}
	
	private void init()
	{

	}
	
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		
	}

	
	public void checkColl (Grid grid)
	{
		
	}
	
}
