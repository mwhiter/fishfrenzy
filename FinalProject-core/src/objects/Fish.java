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
		collideTiles(grid);
		updateVelocity(grid, deltaTime);
		super.update(deltaTime);
	}

	// Fish velocity is dependent on their direction, so update their velocity depending on their direction
	public void updateVelocity(Grid grid, float deltaTime)
	{
		Tile currentTile = grid.getTile(sprite.getX(), sprite.getY());
		if(currentTile == null) return;
		
		switch(direction)
		{
		case DIRECTION_UP:
			velocity.set(0.0f, Constants.FISH_SPEED * deltaTime);
			sprite.setCenterX(currentTile.getCenterX());
			break;
		case DIRECTION_DOWN:
			velocity.set(0.0f, -Constants.FISH_SPEED * deltaTime);
			sprite.setCenterX(currentTile.getCenterX());
			break;
		case DIRECTION_LEFT:
			velocity.set(-Constants.FISH_SPEED * deltaTime, 0.0f);
			sprite.setCenterY(Constants.HEIGHT - currentTile.getCenterY());
			break;
		case DIRECTION_RIGHT:
			velocity.set(Constants.FISH_SPEED * deltaTime, 0.0f);
			sprite.setCenterY(Constants.HEIGHT - currentTile.getCenterY());
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
	
	// Turn toward a direction.
	public void turn(DirectionType eTurnDirection)
	{	
		if(eTurnDirection == DirectionType.DIRECTION_LEFT)
		{
			switch(direction)
			{
			case DIRECTION_UP: 		direction = DirectionType.DIRECTION_LEFT; 	break;
			case DIRECTION_RIGHT: 	direction = DirectionType.DIRECTION_UP; 	break;
			case DIRECTION_DOWN: 	direction = DirectionType.DIRECTION_RIGHT; 	break;
			case DIRECTION_LEFT: 	direction = DirectionType.DIRECTION_DOWN; 	break;
			default: break;
			}
		}
		else if(eTurnDirection == DirectionType.DIRECTION_RIGHT)
		{	
			switch(direction)
			{
			case DIRECTION_UP: 		direction = DirectionType.DIRECTION_RIGHT; 	break;
			case DIRECTION_RIGHT: 	direction = DirectionType.DIRECTION_DOWN; 	break;
			case DIRECTION_DOWN: 	direction = DirectionType.DIRECTION_LEFT; 	break;
			case DIRECTION_LEFT: 	direction = DirectionType.DIRECTION_UP; 	break;
			default: break;
			}
		}
	}
	
	// Fish checks tiles it could possibly collide with
	public void collideTiles(Grid grid)
	{
		// getTile() works as expected now
		// please be sure to do a null check "if(temp != null)" before doing any operations on stuff that could potentially be null
		
		//Tile currentTile = grid.getTile(sprite.getX()+midX, Constants.HEIGHT - sprite.getY()+midY); 
		// Had prob with that if not needed then midX and midY is also unused.
		Tile currentTile = grid.getTile(sprite.getX(), Constants.HEIGHT - sprite.getY());
		if(currentTile != null)
		{
			//System.out.println(currentTile.getCenterX() + " " + currentTile.getCenterY() + " " + sprite.getX()  + " "+ sprite.getY() );

			if(currentTile.getTileType() == TileType.TILE_PLAYER_GATE)
			{
				if(currentTile.getOwner() != null)
					currentTile.getOwner().awardFish(this);
			}
			
			// If we're near the center of the tile...
			if (	
					sprite.getX() + sprite.getWidth()/2 >= currentTile.getCenterX() - Constants.TILE_COLLIDE_BOX &&
					sprite.getX() + sprite.getWidth()/2 <= currentTile.getCenterX() + Constants.TILE_COLLIDE_BOX &&
					sprite.getY() + sprite.getHeight()/2 >= currentTile.getCenterY() - Constants.TILE_COLLIDE_BOX &&
					sprite.getY() + sprite.getHeight()/2 <= currentTile.getCenterY() + Constants.TILE_COLLIDE_BOX
				)
			{
				// If the current tile has a direction, go through that direction.
				if(currentTile.getDirection() != DirectionType.NO_DIRECTION)
				{
					direction = currentTile.getDirection();
					return;		// don't worry about anything else
				}
				
				// Fish need to look ahead and prevent themselves from colliding with solid tiles
				
				Tile nextTile = null;
				switch(direction)
				{
				case DIRECTION_UP:
					nextTile = grid.getTile(currentTile.getX(),currentTile.getY() - 1);
					if(nextTile != null)
					{
						if (nextTile.getTileType() == TileType.TILE_SOLID )
							turn(DirectionType.DIRECTION_RIGHT);
					}
					break;
				case DIRECTION_RIGHT:
					nextTile = grid.getTile(currentTile.getX() + 1,currentTile.getY());
					if(nextTile != null)
					{
						if (nextTile.getTileType() == TileType.TILE_SOLID )
							turn(DirectionType.DIRECTION_RIGHT);
					}
					break;
				case DIRECTION_DOWN:
					nextTile = grid.getTile(currentTile.getX(),currentTile.getY() + 1);
					if(nextTile != null)
					{
						if (nextTile.getTileType() == TileType.TILE_SOLID )
							turn(DirectionType.DIRECTION_RIGHT);
					}
					break;
				case DIRECTION_LEFT:
					nextTile = grid.getTile(currentTile.getX() - 1,currentTile.getY());
					if(nextTile != null)
					{
						if (nextTile.getTileType() == TileType.TILE_SOLID )
							turn(DirectionType.DIRECTION_RIGHT);
					}
					break;
				default:
					break;
				}
			}
		}
	}
	
}
