package objects;

import com.badlogic.gdx.graphics.Texture;

import core.Constants;
import core.DirectionType;
import environment.Tile;
import environment.Grid;

// Fish class. Extends the entity.
// Fish spawn from the center and move in a certain direction.
public class Fish extends Entity
{
	DirectionType direction;
	public boolean inGrid = true;
	
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
		checkColl(grid);
		updateVelocity(deltaTime);
		//super.update(deltaTime); //Make sure you dont check out of bounds
		if ( this.sprite.getX() < Constants.WIDTH - 50  && this.sprite.getY() < Constants.HEIGHT - 50  )
		{
			super.update(deltaTime);
		}
		
	}

	// Fish velocity is dependent on their direction, so update their velocity depending on their direction
	public void  updateVelocity(float deltaTime)
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
	public void checkColl (Grid grid)
	{
		Tile temp =  grid.getTile(this.sprite.getX(),this.sprite.getY());
		//System.out.println(this.sprite.getX() + " " + this.sprite.getY());
		//System.out.println(temp.getX() + " " + temp.getX());
		//Prob 1) the Fish will always say =it is at 4,4
		//2) the tile are all the default way they are set up
		switch(direction)
		{
		case DIRECTION_UP:
			if (this.sprite.getX() < Constants.WIDTH - 50 && this.sprite.getY() < Constants.HEIGHT - 50)
			{
				//if ( grid.getTile(this.sprite.getX(),this.sprite.getY()).getDirection() == DirectionType.DIRECTION_LEFT)
				//if (temp.getDirection() == DirectionType.DIRECTION_LEFT)
				//	this.direction = DirectionType.DIRECTION_LEFT ;
				//if (temp.getDirection() == DirectionType.NO_DIRECTION)
				System.out.println(temp.getTileType());
				System.out.println(temp.getDirection());
			}
			break;
			
		
		default:
			return;
		}
	}
	
}
