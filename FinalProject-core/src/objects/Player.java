package objects;

import java.util.ArrayList;

import core.DirectionType;
import environment.Grid;
import environment.Tile;

// Class: Player
// A player in the game world.
	// Needs to have an isHuman() flag. Because only humans will accept input from the game board
public class Player {
	private boolean bHuman;
	private int id;
	private int iFishCaptured;	// number of fish captured
	private int iScore;			// actual game score (could be different from fish captured
	private int iCoins;			// number of coins we've collected
	
	private DirectionType activeDirection;
	ArrayList<Tile> tiles;
	
	// Constructor
	public Player(int id, boolean bHuman)
	{
		this.id = id;
		this.bHuman = bHuman;
		
		iScore = 0;
		iFishCaptured = 0;
		iCoins = 0;
				
		activeDirection = DirectionType.NO_DIRECTION;
		tiles = new ArrayList<Tile>();
	}
	
	// What does it mean to update a Player? Run its AI (this is called once per frame)
	// Is this really the best thing to do? Maybe not...discuss at some point
	public void update()
	{
		// Don't update for humans because they make their own decisions
		if(!isHuman()) 
		{
			return;
		}
		
		// ai stuff here...
	}
	
	public boolean isHuman()
	{
		return bHuman;
	}
	
	public void assignTile(Grid grid, Tile tile)
	{
		if(grid == null) return;
		if(tile == null) return;
		if(tile.getDirection() == getActiveDirection()) return;
		
		// set the tile direction and then add it to our tiles list
		// will return false if direction was not assigned
		if(tile.setTileDirection(this, getActiveDirection()))
		{
			// If we've reached the maximum amount of tiles we're allowed, remove the first one
			if (tiles.size() > 2)
		    {
				grid.getTile(tiles.get(0)).setTileDirection(this, DirectionType.NO_DIRECTION);
		     	tiles.remove(0);
			}
			
			tiles.add(tile);
		}
	}
	
	// Awards a fish to player. Set the fish to be killed next player and give him score
	public void awardFish(Fish fish)
	{
		iFishCaptured++;
		iScore++;
		
		System.out.println("Player " + id + " captured a fish (coin: " + fish.isHasCoin() + ")");
		
		if(fish.isHasCoin())
		{
			iCoins++;
		}
		
		fish.setDelayedDeath(true);
	}
	
	public int getNumFishCaptured() { return iFishCaptured; }
	public int getScore()			{ return iScore; }
	public int getCoins()			{ return iCoins; }
	//public void incTile()			{ tileAmmo ++; }
	
	public DirectionType getActiveDirection() { return activeDirection; }
	public void setActiveDirection(DirectionType eDirection) { activeDirection = eDirection; }
}
