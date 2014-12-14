package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import core.Constants;
import core.DirectionType;
import core.GameLogic;
import environment.Tile;
import environment.Grid;
import environment.TileType;

// Fish class. Extends the entity.
// Fish spawn from the center and move in a certain direction.
public class Fish extends Entity
{
	Grid grid;
	DirectionType direction;
	private boolean hasCoin;
	
	// Constructor
	public Fish(DirectionType direction, Grid grid, Texture texture)
	{
		super(texture);
		init(direction, grid);
	}
	public Fish(DirectionType direction, Grid grid, Texture texture, Tile spawn)
	{
		super(texture, spawn.getCenterX(), spawn.getCenterY());
		init(direction, grid);
	}
	private void init(DirectionType direction, Grid grid)
	{
		this.grid = grid;
		this.direction = direction;
		hasCoin = false;
	}
	
	public Tile getTile()
	{
		return grid.getTile(sprite.getX(), Constants.HEIGHT - sprite.getY());
	}
	public int getXloc()
	{
		Tile t = grid.getTile(sprite.getX(), Constants.HEIGHT - sprite.getY());
		if(t == null) return 0;
		return t.getX();
	}
	public int getYloc()
	{
		Tile t = grid.getTile(sprite.getX(), Constants.HEIGHT - sprite.getY());
		if(t == null) return 0;
		return t.getY();
	}
	
	public boolean isHasCoin() { return hasCoin; }
	public void setHasCoin(boolean bVal) { hasCoin = bVal; }
	
	public void update(float deltaTime, Grid grid) // tried to send grid, but grid never updates ////////////
	{
		super.update(deltaTime);
		updateRotation();
		updateVelocity(grid, deltaTime);
		collideTiles(deltaTime, grid);
	}

	public void updateRotation()
	{
		switch(direction)
		{
		case DIRECTION_UP: 		sprite.setRotation(0); break;
		case DIRECTION_DOWN: 	sprite.setRotation(180); break;
		case DIRECTION_LEFT: 	sprite.setRotation(90); break;
		case DIRECTION_RIGHT: 	sprite.setRotation(270); break;
		default: break;
		}
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
	
	public boolean canMoveInto(Tile dest)
	{
		if(dest == null) return false;
		
		if(dest.getTileType() == TileType.TILE_SOLID)
			return false;
		
		if(dest.getTileType() == TileType.TILE_FISH_GATE)
			return false;
		
		return true;
	}
	
	// Fish checks tiles it could possibly collide with
	public void collideTiles(float deltaTime, Grid grid)
	{
		// getTile() works as expected now
		// please be sure to do a null check "if(temp != null)" before doing any operations on stuff that could potentially be null
		
		//Tile currentTile = grid.getTile(sprite.getX()+midX, Constants.HEIGHT - sprite.getY()+midY); 
		// Had prob with that if not needed then midX and midY is also unused.
		Tile currentTile = grid.getTile(sprite.getX(), Constants.HEIGHT - sprite.getY());
		if(currentTile != null)
		{
			//System.out.println(currentTile.getCenterX() + " " + currentTile.getCenterY() + " " + sprite.getX()  + " "+ sprite.getY() );
			
			// If we're near the center of the tile...
			if (sprite.getBoundingRectangle().contains(new Rectangle( currentTile.getCenterX() - Constants.TILE_COLLIDE_BOX, currentTile.getCenterY() - Constants.TILE_COLLIDE_BOX, Constants.TILE_COLLIDE_BOX, Constants.TILE_COLLIDE_BOX)))
			{
				// If we hit a player gate tile, award that fish to the player
				if(currentTile.getTileType() == TileType.TILE_PLAYER_GATE)
				{
					if(currentTile.getOwner() != null)
					{
						currentTile.getOwner().awardFish(this);
						return;
					}
				}
				
				// If the current tile has a coin, this fish picks up the coin
				if(!isHasCoin())
				{
					if(currentTile.hasCoin())
					{
						currentTile.removeCoin();
						setHasCoin(true);
						GameLogic.changeNumAlgaeActive(-1);
					}
				}
				
				// If the current tile has a direction, go through that direction.
				if(currentTile.getDirection() != DirectionType.NO_DIRECTION)
				{
					direction = currentTile.getDirection();
					return;		// don't worry about anything else
				}
				
				// Fish need to look ahead and prevent themselves from colliding with solid tiles
				
				Tile nextTile = grid.getTileInDirection(currentTile, direction);
				if(!canMoveInto(nextTile))
					turn(DirectionType.DIRECTION_RIGHT);
				
				// bugfix: do it again!
				nextTile = grid.getTileInDirection(currentTile, direction);
				if(!canMoveInto(nextTile))
					turn(DirectionType.DIRECTION_RIGHT);
			}
		}
		// yeah we somehow ended out of the map. Kill us.
		else
		{
			setDelayedDeath(true);
		}
	}
}
