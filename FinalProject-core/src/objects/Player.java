package objects;

import java.util.ArrayList;



import core.DirectionType;
import core.GameLogic;
import environment.Grid;
import environment.Tile;

// Class: Player
// A player in the game world.
	// Needs to have an isHuman() flag. Because only humans will accept input from the game board
public class Player {
	private boolean bHuman;
	private boolean doublePoints = false;
	private boolean isFrozen = false;
	private int id;
	private int iFishCaptured;	// number of fish captured
	private int iScore;			// actual game score (could be different from fish captured
	private int iCoins;			// number of coins we've collected
	private Tile home;			// the home gate for this player
	private PlayerAI ai;		// the AI. Every player has an AI but only humans will update.
	
	private DirectionType activeDirection;
	private ArrayList<Tile> tiles;
	
	// Constructor
	public Player(GameLogic logic, int id, boolean bHuman)
	{
		this.id = id;
		this.bHuman = bHuman;
		this.ai = new PlayerAI(this, logic);
		
		home = null;
		iScore = 0;
		iFishCaptured = 0;
		iCoins = 0;
				
		activeDirection = DirectionType.NO_DIRECTION;
		tiles = new ArrayList<Tile>();
	}
	
	// What does it mean to update a Player? Run its AI (this is called once per frame)
	// Is this really the best thing to do? Maybe not...discuss at some point
	public void update(Grid grid)
	{	
		if (isFrozen == false)
		ai.update(grid);
	}
	
	public boolean isHuman()
	{
		return bHuman;
	}
	
	public void assignTile(Grid grid, Tile tile)
	{
		if(grid == null) return;
		if(tile == null) return;
		
		// We tried to place a tile on a tile with a direction - kill it first
		if(tile.getDirection() != DirectionType.NO_DIRECTION)
		{
			unassignTile(tile);
		}
		
		// set the tile direction and then add it to our tiles list
		// will return false if direction was not assigned
		if(tile.setTileDirection(this, getActiveDirection()))
		{
			// If we've reached the maximum amount of tiles we're allowed, remove the first one
			if (tiles.size() > 2)
		    {
				unassignTile(tiles.get(0));
			}
			
			tiles.add(tile);
			tile.setDirectionSetter(this);
		}
	}
	
	public void unassignTile(Tile tile)
	{
		Player owner = tile.getDirectionSetter();

		ArrayList<Tile> owner_tiles = owner.getTiles();

		tile.setTileDirection(this, DirectionType.NO_DIRECTION);
		tile.setDirectionSetter(null);
		owner_tiles.remove(tile);
	}
	
	// Awards a fish to player. Set the fish to be killed next player and give him score
	public void awardFish(Fish fish)
	{
		iFishCaptured++;
		iScore++;
		if (doublePoints == true){iFishCaptured++;iScore++;}
		
		//System.out.println("Player " + id + " captured a fish (coin: " + fish.isHasCoin() + ")");
		
		if(fish.isHasCoin())
		{
			iCoins++;
		}
		
		fish.setDelayedDeath(true);
	}
	
	public int getID() { return id; }
	public void setHome(Tile tile) { home = tile; }
	public Tile getHome() { return home; }
	public int getNumFishCaptured() { return iFishCaptured; }
	public int getScore()			{ return iScore; }
	public int getCoins()			{ return iCoins; }
	public void useCoins(int s)		{iCoins -= s;}
	//public void incTile()			{ tileAmmo ++; }
	public boolean getDBPowerup()		{return doublePoints;} 
	public void setDBPowerup(boolean b) {doublePoints = b;}
	public boolean getFZPowerup()		{return isFrozen;} 
	public void setFZPowerup(boolean b) {isFrozen = b;}
	
	public ArrayList<Tile> getTiles() { return tiles; }
	public DirectionType getActiveDirection() { return activeDirection; }
	public void setActiveDirection(DirectionType eDirection) { activeDirection = eDirection; }
}
