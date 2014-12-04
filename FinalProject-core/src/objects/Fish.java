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
	int midX = 0, midY = 32;
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
		
		checkTile(grid);
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
			midY = 32;
			break;
		case DIRECTION_DOWN:
			velocity.set(0.0f, -Constants.FISH_SPEED * deltaTime);
			midY = -32;
			break;
		case DIRECTION_LEFT:
			velocity.set(-Constants.FISH_SPEED * deltaTime, 0.0f);
			midX = 32;
			break;
		case DIRECTION_RIGHT:
			velocity.set(Constants.FISH_SPEED * deltaTime, 0.0f);
			midX = -32;
			break;
		default:
			velocity.x = 0.0f;
			velocity.y = 0.0f;
			break;
		}
		
	}
	public void checkTile(Grid grid)
	{
		Tile currentTile = grid.getTile(sprite.getX(), Constants.HEIGHT - sprite.getY());
		if(currentTile != null)
		{
			if (sprite.getX() <=currentTile.getCenterX() +8 && sprite.getX() >=currentTile.getCenterX() -8  && sprite.getY() <=currentTile.getCenterY() +8 && sprite.getY() >=currentTile.getCenterY() -8 )
			{
				if (currentTile.getDirection() == DirectionType.DIRECTION_LEFT)
				{
					direction = DirectionType.DIRECTION_LEFT;
				}
				if (currentTile.getDirection() == DirectionType.DIRECTION_RIGHT)
				{
					direction = DirectionType.DIRECTION_RIGHT;
				}
				if (currentTile.getDirection() == DirectionType.DIRECTION_UP)
				{
					direction = DirectionType.DIRECTION_UP;
				}
				if (currentTile.getDirection() == DirectionType.DIRECTION_DOWN)
				{
					direction = DirectionType.DIRECTION_DOWN;
				}
			}
		}
		
	}
	
	public void collide(Grid grid)
	{
		// getTile() works as expected now
		// please be sure to do a null check "if(temp != null)" before doing any operations on stuff that could potentially be null
		
		//Tile currentTile = grid.getTile(sprite.getX()+midX, Constants.HEIGHT - sprite.getY()+midY); 
		// Had prob with that if not needed then midX and midY is also unused.
		Tile currentTile = grid.getTile(sprite.getX(), Constants.HEIGHT - sprite.getY());
		if(currentTile != null)
		{
			//System.out.println(currentTile.getCenterX() + " " + currentTile.getCenterY() + " " + sprite.getX()  + " "+ sprite.getY() );
			
			if (sprite.getX() <=currentTile.getCenterX() +8 && sprite.getX() >=currentTile.getCenterX() -8  && sprite.getY() <=currentTile.getCenterY() +8 && sprite.getY() >=currentTile.getCenterY() -8 )
			{
				if (direction == DirectionType.DIRECTION_UP)
				{
					Tile nextTile = grid.getTile(currentTile.getX(),currentTile.getY()-1);
					if(nextTile != null)
					{
						if (nextTile.getTileType() == TileType.TILE_SOLID )
							direction = DirectionType.DIRECTION_RIGHT;
					}
				}
				if (direction == DirectionType.DIRECTION_RIGHT)
				{
					Tile nextTile = grid.getTile(currentTile.getX()+1,currentTile.getY());
					if(nextTile != null)
					{
						if (nextTile.getTileType() == TileType.TILE_SOLID )
							direction = DirectionType.DIRECTION_DOWN;
					}
				}
				if (direction == DirectionType.DIRECTION_DOWN)
				{
					Tile nextTile = grid.getTile(currentTile.getX(),currentTile.getY()+1);
					if(nextTile != null)
					{
						if (nextTile.getTileType() == TileType.TILE_SOLID )
							direction = DirectionType.DIRECTION_LEFT;
					}
				}
				if (direction == DirectionType.DIRECTION_LEFT)
				{
					Tile nextTile = grid.getTile(currentTile.getX()-1,currentTile.getY());
					if(nextTile != null)
					{
						if (nextTile.getTileType() == TileType.TILE_SOLID )
							direction = DirectionType.DIRECTION_UP;
					}
				}
				//if(currentTile != null)
				//System.out.println( grid.getTile(currentTile.getX(),currentTile.getY()-1).getTileType());
				
				if(currentTile.getTileType() == TileType.TILE_PLAYER_GATE)
				{
					if(currentTile.getOwner() != null)
						currentTile.getOwner().awardFish(this);
				}
			}
		}
	}
	
}
