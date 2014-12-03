package objects;

import com.badlogic.gdx.graphics.Texture;

import core.Constants;
import core.DirectionType;
import environment.Tile;
import environment.Grid;
import environment.TileType;

// Fish class. Extends the entity.
// Fish spawn from the center and move in a certain direction.
public class Fish extends Entity
{
	DirectionType direction;
	
	// Constructor
	public Fish(Texture texture)
	{
		super(texture);
		init();
	}
	public Fish(Texture texture, Tile spawn)
	{
		super(texture, spawn.getCenterX(), spawn.getCenterY());
		init();
	}
	
	private void init()
	{
		direction = DirectionType.DIRECTION_UP;
	}
	
	public void update(float deltaTime, Grid grid) // tried to send grid, but grid never updates ////////////
	{
		collide(grid);
		updateVelocity(deltaTime);
		super.update(deltaTime);
		
	}

	// Fish velocity is dependent on their direction, so update their velocity depending on their direction
	public void updateVelocity(float deltaTime)
	{
		switch(direction)
		{
		case DIRECTION_UP:
			velocity.set(0.0f, Constants.FISH_SPEED * deltaTime);
			break;
		case DIRECTION_DOWN:
			velocity.set(0.0f, -Constants.FISH_SPEED * deltaTime);
			break;
		case DIRECTION_LEFT:
			velocity.set(-Constants.FISH_SPEED * deltaTime, 0.0f);
			break;
		case DIRECTION_RIGHT:
			velocity.set(Constants.FISH_SPEED * deltaTime, 0.0f);
			break;
		default:
			velocity.x = 0.0f;
			velocity.y = 0.0f;
			break;
		}
		
	}
	
	public void collide(Grid grid)
	{
		// getTile() works as expected now
		// please be sure to do a null check "if(temp != null)" before doing any operations on stuff that could potentially be null
		Tile currentTile = grid.getTile(sprite.getX(), Constants.HEIGHT - sprite.getY());
		if(currentTile != null)
		{
			System.out.println(currentTile.getTileType());
			if(currentTile.getTileType() == TileType.TILE_PLAYER_GATE)
			{
				if(currentTile.getOwner() != null)
					currentTile.getOwner().awardFish(this);
			}
		}
	}
}
