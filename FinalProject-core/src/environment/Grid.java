package environment;

import java.util.ArrayList;

import core.Constants;
import core.DirectionType;
import core.GameLogic;
import environment.TileType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;

public class Grid {
	// the starting X and Y locations for the grid
	private float startX;
	private float startY;
	private GameLogic logic;
	
	private int width;
	private int height;
	public Tile[][] grid;
	
	private ArrayList<Tile> validTiles;
	private ArrayList<Tile> fishSpawns;
	
	public Grid(GameLogic logic, String file_name)
	{
		this.logic = logic;
		fishSpawns = new ArrayList<Tile>();
		validTiles = new ArrayList<Tile>();
		ReadGrid(file_name);
	}
	
	// init the grid
	// todo: read in level data from a file to fill in the grid?
	private void ReadGrid(String file_name)
	{
		FileHandle handle = Gdx.files.internal(file_name);
		String text = handle.readString();
		
		int tilesProcessed = 0;
		for(int i = 0; i < text.length(); i++)
		{
			// Process "header" information (basically read in the size)
			if(i == 0 || i == 1)
			{
				char c = text.charAt(0);
				String size = Character.toString(c);
				// first is width
				if(i == 0)
					this.width = Integer.valueOf(size);
				// second is height
				else if(i == 1)
				{
					this.height = Integer.valueOf(size);
					grid = new Tile[this.width][this.height];
					
					// center the grid on the board correctly
					startX = (Constants.WIDTH - this.width * Constants.TILE_SIZE) / 2;
					startY = (Constants.HEIGHT - this.height * Constants.TILE_SIZE) / 2;
				}
			}
			else
			{
				char c = text.charAt(i);
				TileType eTileType = TileType.TILE_EMPTY;
				
				switch(c)
				{
				case '0': eTileType = TileType.TILE_EMPTY; break;
				case '1': eTileType = TileType.TILE_SOLID; break;
				case '2': eTileType = TileType.TILE_FISH_GATE; break;
				case '3': eTileType = TileType.TILE_PLAYER_GATE; break;
				default: continue;
				}
				
				// index 0 is used for the size of the grid, so the grid data start is 1				
				int iX = tilesProcessed % this.width;
				int iY = (int) (tilesProcessed / this.height);
				tilesProcessed++;
				
				grid[iX][iY] = new Tile(this, eTileType, null, iX, iY);
			}
		}
		
		// POST-PROCESSING OF GRID
		
		int playerSpawnAssigned = 0;
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				switch(grid[i][j].getTileType())
				{
				// Add the fish spawns to the array list so we can support multiple fish spawns
				case TILE_EMPTY:
					validTiles.add(grid[i][j]);
					break;
				case TILE_FISH_GATE:
					fishSpawns.add(grid[i][j]);
					break;
				// Found a player gate - assign ownership on a first-come, first-serve basis
				case TILE_PLAYER_GATE:
					if(playerSpawnAssigned < logic.getPlayers().size())
					{
						logic.getPlayer(playerSpawnAssigned).setHome(grid[i][j]);
						grid[i][j].setOwner(logic.getPlayer(playerSpawnAssigned));
						playerSpawnAssigned++;
					}
					break;
				default:
					break;
				}
			}
		}
		
		// If we have no fish spawns...just pick a random valid tile to be a spawn
		// naive algorithm, a better one exists but this situation should not ever happen
		// this can get super inefficient QUICK if the grid is full of invalid tiles. But I'm assuming it isn't
		while(fishSpawns.isEmpty())
		{
			int randX = MathUtils.random(width-1);
			int randY = MathUtils.random(height-1);
			
			if(grid[randX][randY].getTileType() == TileType.TILE_EMPTY)
			{
				grid[randX][randY].setTileType(TileType.TILE_FISH_GATE);
				fishSpawns.add(grid[randX][randY]);
			}
		}
	}
	
	public void draw(Batch spriteBatch)
	{
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				grid[i][j].draw(spriteBatch);
			}
		}
	}
	
	// Given coordinates (x, y), return the tile that we've intersected
	// Can potentially return null so be sure to do a null check
	public Tile getTile(float x, float y)
	{
		Tile rtn = null;
		
		// Easy cases where we clicked out the boundaries of the grid
		if(x < startX || x > startX + getSizeX()) return null;
		if(y < startY || y > startY + getSizeY()) return null;
		
		// Find out which tile we clicked
		int tileX = (int) ((x - startX) / Constants.TILE_SIZE);
		int tileY = (int) ((y - startY) / Constants.TILE_SIZE);
		
		rtn = grid[tileX][tileY];
		
		return rtn;
	}
	
	public Tile getTile(int x, int y)
	{
		if(x < 0 || x >= width || y < 0 || y >= height)
		{
			return null;
		}
		return grid[x][y];
	}
	public Tile getTile(Tile tile)
	{
		if(tile == null) return null;
		return grid[tile.getX()][tile.getY()];
	}
	public Tile getTileInDirection(Tile tile, DirectionType direction)
	{	
		if(direction == DirectionType.NO_DIRECTION)
			return null;
		
		switch(direction)
		{
		case DIRECTION_UP:
			return getTile(tile.getX(), tile.getY()-1);
		case DIRECTION_DOWN:
			return getTile(tile.getX(), tile.getY()+1);
		case DIRECTION_LEFT:
			return getTile(tile.getX()-1, tile.getY());
		case DIRECTION_RIGHT:
			return getTile(tile.getX()+1, tile.getY());
		default:
			return null;
		}
	}
	
	public int getWidth() 	{ return width; }
	public int getHeight() 	{ return height; }
	public float getStartX() { return startX; }
	public float getStartY() { return startY; }
	
	// returns width * tile size
	public int getSizeX()	{ return width * Constants.TILE_SIZE; }
	// returns height * tile size
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
	
	public ArrayList<Tile> GetValidTiles() { return validTiles; }
	public Tile GetRandomValidTile()
	{
		if(validTiles.size() == 0) return null;
		int random = MathUtils.random(validTiles.size()-1);
		return validTiles.get(random);
	}
}
