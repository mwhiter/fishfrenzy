package objects;

import com.badlogic.gdx.graphics.Texture;

import environment.Tile;
import environment.Grid;

public class Algae extends Entity
{
	public boolean inGrid = true;
	
	// Constructor
	public Algae(Tile spawn)
	{
		super(new Texture("objects/coin.png"), spawn.getCenterX(), spawn.getCenterY());
		
		init();
	}
	
	private void init()
	{
		setUpdatable(false);
	}
	
	public void update(float deltaTime)
	{
		super.update(deltaTime);	
	}

	
	public void checkColl (Grid grid)
	{
		
	}
	
}
