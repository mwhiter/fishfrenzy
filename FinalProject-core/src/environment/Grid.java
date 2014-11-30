package environment;

import java.util.ArrayList;

import core.Constants;
import environment.TileType;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;

public class Grid {
	// the starting X and Y locations for the grid
	private float startX;
	private float startY;
	
	private int width;
	private int height;
	public Tile[][] grid;
	
	private ArrayList<Tile> fishSpawns;
	
	public Grid(float startX, float startY, int width, int height)
	{
		this.startX = startX;
		this.startY = startY;
		this.width =  width;
		this.height = height;
		grid = new Tile[width][height];
		fishSpawns = new ArrayList<Tile>();
		init();
	}
	
	// init the grid
	// todo: read in level data from a file to fill in the grid?
	public void init()
	{
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				// Pre-defined constants for now.
				// Obviously, we need to change this so the grid is defined in a file.
				TileType eTileType = TileType.TILE_EMPTY;
				if(i == 0 || i == 8 || j == 0 || j == 8) eTileType = TileType.TILE_SOLID;
				//if(i == 7) eTileType = TileType.TILE_SOLID;
				if(i == 0 && j == 4) eTileType = TileType.TILE_PLAYER_GATE;
				if(i == 4 && j == 4) eTileType = TileType.TILE_FISH_GATE;
				if(i == 8 && j == 4) eTileType = TileType.TILE_PLAYER_GATE;
				
				grid[i][j] = new Tile(this, eTileType, i, j);
				if(eTileType == TileType.TILE_FISH_GATE)
					fishSpawns.add(grid[i][j]);
				//System.out.println(grid[i][j].getTilePos());
			}
		}
	}
	
	public void draw(Batch spriteBatch)
	{
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				grid[i][j].sprite.draw(spriteBatch);
			}
		}
	}
	
	// Given coordinates (x, y), return the tile that we've intersected
	public Tile getTile(float x, float y)
	{
		Tile rtn = null;
		
		// Easy cases where we clicked out the boundaries of the grid
		if(x < startX || x > startX + getSizeX()) return null;
		if(y < startY || y > startY + getSizeY()) return null;
		
		// Find out which tile we clicked
		int tileX = (int) ((x - startX) / Constants.TILE_SIZE);
		int tileY = (getHeight()-1) - (int)(((y - startY) / Constants.TILE_SIZE));
		
		rtn = grid[tileX][tileY];
		
		return rtn;
	}
	
	public Tile getTile(int x, int y) { return grid[x][y]; }
	public Tile getTile(Tile tile)
	{
		if(tile == null) return null;
		return grid[tile.getX()][tile.getY()];
	}
	
	public int getWidth() 	{ return width; }
	public int getHeight() 	{ return height; }
	public float getStartX() { return startX; }
	public float getStartY() { return startY; }
	public int getSizeX()	{ return width * Constants.TILE_SIZE; }
	public int getSizeY()	{ return height * Constants.TILE_SIZE; }
	
	/* Support for multiple fish spawns */
	public ArrayList<Tile> GetFishSpawns() { return fishSpawns; }
	// Get a particular fish spawn
	public Tile GetFishSpawn(int index)
	{
		if(index < 0 || index >= fishSpawns.size()) return null;
		return fishSpawns.get(index);
	}
	// Get a random fish spawn (out of a list of all of them)
	public Tile GetRandomFishSpawn()
	{
		if(fishSpawns.size() == 0) return null;
		int random = MathUtils.random(fishSpawns.size()-1);
		return fishSpawns.get(random);
	}
}
