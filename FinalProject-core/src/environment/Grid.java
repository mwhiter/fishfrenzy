package environment;

import core.Constants;
import environment.TileType;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Grid {
	private int width;
	private int height;
	Tile[][] grid;
	
	public Grid(int width, int height)
	{
		this.width =  width;
		this.height = height;
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
				// Pre-defined constants for now.
				// Obviously, we need to change this so the grid is defined in a file.
				TileType eTileType = TileType.TILE_EMPTY;
				if(i == 0 && j == 3) eTileType = TileType.TILE_PLAYER_GATE;
				if(i == 3 && j == 3) eTileType = TileType.TILE_FISH_GATE;
				if(i == 6 && j == 3) eTileType = TileType.TILE_PLAYER_GATE;
				
				grid[i][j] = new Tile(eTileType, i, j);
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
	
	public int getWidth() 	{ return width; }
	public int getHeight() 	{ return height; }
	public int getSizeX()	{ return width * Constants.TILE_SIZE; }
	public int getSizeY()	{ return height * Constants.TILE_SIZE; }
}
