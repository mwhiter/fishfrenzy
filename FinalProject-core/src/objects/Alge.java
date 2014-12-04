package objects;

import com.badlogic.gdx.graphics.Texture;

import core.Constants;
import core.DirectionType;
import environment.Tile;
import environment.Grid;

public class Alge extends Entity
{
	public boolean inGrid = true;
	
	// Constructor
	public Alge(Texture texture)
	{
		super(texture);
		init();
	}
	public Alge(Texture texture, Tile spawn)
	{
		super(texture, spawn.getRealX()+32, spawn.getRealY()+32);
		init();
	}
	
	private void init()
	{

	}
	
	public void update(float deltaTime) // tried to send grid, but grid never updates ////////////
	{
		super.update(deltaTime);
		
	}

	
	public void checkColl (Grid grid)
	{
		
	}
	
}
