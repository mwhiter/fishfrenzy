package environment;

//import com.badlogic.gdx.Gdx;
import objects.Player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;

import environment.TileType;
import core.Constants;
import core.DirectionType;

// A tile in the grid
// Tiles have a tile type and a direction type
	// tile type: innate, never-changing tile type (solid, empty, etc.)
	// direction type: changes on player input, directs the entities
public class Tile {
	public Sprite sprite;
	public TileType tileType;
	public DirectionType direction;
	
	private Texture texture;
	private TextureRegion[][] tileTextures;
	
	// Location coordinates
	int x, y;				// grid coordinates (i.e. x = 0 is the left corner)
	float real_x, real_y;	// real coordinates (i.e. x * Constants.TILE_SIZE)
	
	Grid grid;	// tiles will store a pointer to the Grid.
	
	public Tile(Grid grid, TileType eTileType, int x, int y)
	{
		this.grid = grid;
		this.x = x;
		this.y = y;
		
		real_x = grid.getStartX() + x * Constants.TILE_SIZE;
		real_y = grid.getStartY() + y * Constants.TILE_SIZE;
		
		sprite = new Sprite();
		sprite.setPosition(real_x, real_y);
		
		tileType = eTileType;
		direction = DirectionType.NO_DIRECTION;
		
		texture = new Texture("tilesheet.png");
		tileTextures = TextureRegion.split(texture, Constants.TILE_SIZE, Constants.TILE_SIZE);
		
		assignTexture(getTextureIndex(eTileType));
	}
	
	// Consult tile sheet for correct index
	private void assignTexture(int index)
	{
		// to-do: we need to check to make sure we don't send an invalid index here!
		int iX = index % (texture.getWidth() / Constants.TILE_SIZE);
		int iY = (int) index / (texture.getWidth() / Constants.TILE_SIZE);
		
		sprite = new Sprite(tileTextures[iY][iX]);
		sprite.setPosition(grid.getStartX() + x * Constants.TILE_SIZE, grid.getStartY() + y * Constants.TILE_SIZE);
	}
	
	public float getTilePos() {return this.sprite.getY();}
	
	// tiles X and Y's should never change outside of the constructor!
	//public void setX(int x) {x = x;}
	//public void setY(int y) {y = y;}
	
	public float getCenterX() { return getRealX() + Constants.TILE_SIZE / 2; }
	public float getCenterY() { return getRealY() + Constants.TILE_SIZE / 2; }
	public float getRealX() { return x * Constants.TILE_SIZE; }
	public float getRealY() { return y * Constants.TILE_SIZE; }
	public int getX() { return x; }
	public int getY() { return y; }
	public DirectionType getDirection() { return direction; }
	
	public TileType getTileType() { return tileType; }
	
	// Find the correct texture index
	private int getTextureIndex(TileType eType)
	{
		switch(eType)
		{
		case TILE_EMPTY: return 0;
		case TILE_SOLID: return 1;
		case TILE_FISH_GATE: return 2;
		case TILE_PLAYER_GATE: return 3;
		default: return 0;
		}
	}
	
	// Find the correct texture index for arrow placement
	private int getDirectionTextureIndex(Player player, DirectionType eDirection)
	{
		switch(eDirection)
		{
		case DIRECTION_UP:
			if(player.isHuman())
				return 4;
			else
				return 8;
		case DIRECTION_DOWN:
			if(player.isHuman())
				return 5;
			else
				return 9;
		case DIRECTION_LEFT:
			if(player.isHuman())
				return 6;
			else
				return 10;
		case DIRECTION_RIGHT:
			if(player.isHuman())
				return 7;
			else
				return 11;
		default:
			return getTextureIndex(getTileType());
		}
	}
	
	// Set the tile direction (and update it's graphics)
	public void setTileDirection(Player player, DirectionType eDirection)
	{
		if(tileType != TileType.TILE_EMPTY) return;
		
		direction = eDirection;
		assignTexture(getDirectionTextureIndex(player, eDirection));
	}
}
