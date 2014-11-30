package objects;

import java.util.ArrayList;

import core.DirectionType;
import environment.Grid;
import environment.Tile;

// Class: Player
// A player in the game world.
	// Needs to have an isHuman() flag. Because only humans will accept input from the game board
public class Player {
	Grid grid;					// Player accepts a pointer to grid for now (don't want to do this, would rather have GameLogic be a global accessible from any function.
	private boolean bHuman;
	private int iFishCaptured;	// number of fish captured
	private int iScore;			// actual game score (could be different from fish captured
	public int tileAmmo;
	private DirectionType activeDirection;
	ArrayList<Tile> tiles;
	
	// Constructor
	public Player(Grid grid, boolean human)
	{
		this.grid = grid;
		bHuman = human;
		iScore = 0;
		iFishCaptured = 0;
		tileAmmo =0;
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
	
	public void assignTile(Tile tile)
	{
		if(tile == null) return;
		if(tile.getDirection() == getActiveDirection()) return;
		
		// If we've reached the maximum amount of tiles we're allowed, remove the first one
		if (tiles.size() > 2)
	    {
			grid.getTile(tiles.get(0)).setTileDirection(this, DirectionType.NO_DIRECTION);
	     	tiles.remove(0);
		}
		
		// set the tile direction and then add it to our tiles list
		tile.setTileDirection(this, getActiveDirection());
		tiles.add(tile);
	}
	
	public int getNumFishCaptured() { return iFishCaptured; }
	public int getScore()			{ return iScore; }
	public void incTile()			{ tileAmmo ++; }
	
	public DirectionType getActiveDirection() { return activeDirection; }
	public void setActiveDirection(DirectionType eDirection) { activeDirection = eDirection; }
}
