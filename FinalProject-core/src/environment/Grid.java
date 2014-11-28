package environment;

import core.Constants;
import environment.TileType;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Grid {
	private int width;
	private int height;
	public Tile[][] grid;
	//static int[][][] gridInfo;
	
	public Grid(int width, int height)
	{
		this.width =  width;
		this.height = height;
		grid = new Tile[width][height];
		//gridInfo = new int [width][height][6];
		
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
				
				grid[i][j] = new Tile(eTileType, i, j);
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
	
	public void setTile(int i, int j, int t)
	{
		TileType eTileType = TileType.TILE_EMPTY;
		if (t == 0) eTileType = TileType.TILE_PLAYER1_LEFT;
		if (t == 1) eTileType = TileType.TILE_PLAYER1_RIGHT;
		if (t == 2) eTileType = TileType.TILE_PLAYER1_UP;
		if (t == 3) eTileType = TileType.TILE_PLAYER1_DOWN;
		grid[i][j] = new Tile(eTileType, i, j);
	}
	public void removeTile (int i, int j)
	{
		grid[i][j] = new Tile(TileType.TILE_EMPTY, i, j);
	}
	
	public int getWidth() 	{ return width; }
	public int getHeight() 	{ return height; }
	public int getSizeX()	{ return width * Constants.TILE_SIZE; }
	public int getSizeY()	{ return height * Constants.TILE_SIZE; }
	
	//public void setTile(int n, int  x, int y)
	//{
	
		
	//}
}
