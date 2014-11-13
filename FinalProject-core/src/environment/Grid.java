package environment;

import core.Constants;

public class Grid {
	private int width;
	private int height;
	Tile[][] grid;
	
	public Grid(int width, int height)
	{
		grid = new Tile[width][height];
		
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
				grid[width][height] = new Tile(i, j);
			}
		}
	}
	
	public void draw()
	{
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				grid[i][j].draw();
			}
		}
	}
	
	public int getWidth() 	{ return width; }
	public int getHeight() 	{ return height; }
	public int getSizeX()	{ return width * Constants.TILE_SIZE; }
	public int getSizeY()	{ return height * Constants.TILE_SIZE; }
}
