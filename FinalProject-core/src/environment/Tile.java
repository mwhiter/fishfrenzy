package environment;

//import com.badlogic.gdx.Gdx;
import objects.Algae;
import objects.Player;

import com.badlogic.gdx.graphics.g2d.Batch;
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
	private TileType tileType;
	private DirectionType direction;
	private Player owner;
	
	private Algae coin;
	
	private Texture texture;
	private TextureRegion[][] tileTextures;
	
	// Location coordinates
	int x, y;				// grid coordinates (i.e. x = 0 is the left corner)
	float real_x, real_y;	// real coordinates (i.e. x * Constants.TILE_SIZE)
	
	Grid grid;	// tiles will store a pointer to the Grid.
	
	public Tile(Grid grid, TileType eTileType, Player owner, int x, int y)
	{
		this.grid = grid;
		this.x = x;
		this.y = y;
		this.owner = owner;	// can be null!!!!
		coin = null;
		
		real_x = grid.getStartX() + (x * Constants.TILE_SIZE);
		real_y = Constants.HEIGHT - (grid.getStartY() + (y * Constants.TILE_SIZE)) - Constants.TILE_SIZE;
		
		tileType = eTileType;
		direction = DirectionType.NO_DIRECTION;
		
		texture = new Texture("tilesheet.png");
		tileTextures = TextureRegion.split(texture, Constants.TILE_SIZE, Constants.TILE_SIZE);
		
		assignTexture(getTextureIndex(eTileType));
	}
	
	public void draw(Batch spriteBatch)
	{
		sprite.draw(spriteBatch);
		if(hasCoin())
			coin.sprite.draw(spriteBatch);
	}
	
	// Consult tile sheet for correct index
	private void assignTexture(int index)
	{
		// TODO we need to check to make sure we don't send an invalid index here!
		int iX = index % (texture.getWidth() / Constants.TILE_SIZE);
		int iY = (int) index / (texture.getWidth() / Constants.TILE_SIZE);
		
		sprite = new Sprite(tileTextures[iY][iX]);
		sprite.setPosition(real_x, real_y);
	}
	
	public float getTilePos() {return this.sprite.getY();}
	
	// tiles X and Y's should never change outside of the constructor!
	//public void setX(int x) {x = x;}
	//public void setY(int y) {y = y;}
	
	public float getCenterX() { return getRealX() + Constants.TILE_SIZE / 2; }
	public float getCenterY() { return getRealY() + Constants.TILE_SIZE / 2; }
	public float getRealX() { return real_x; }
	public float getRealY() { return real_y; }
	public int getX() { return x; }
	public int getY() { return y; }
	public DirectionType getDirection() { return direction; }
	// Returns the tile's owner. Can be null!
	public Player getOwner() { return owner; }
	public void setOwner(Player player) { owner = player; }
	public boolean hasOwner() { return owner != null; }
	public TileType getTileType() { return tileType; }
	public void setTileType(TileType eNewType) { tileType = eNewType; }
	
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
	public boolean setTileDirection(Player player, DirectionType eDirection)
	{
		if(player == null) return false;
		if(!canPlaceDirection()) return false;
		
		direction = eDirection;
		assignTexture(getDirectionTextureIndex(player, eDirection));
		return true;
	}
	
	public boolean canPlaceDirection()
	{
		if(tileType != TileType.TILE_EMPTY)
			return false;
		
		return true;
	}
	
	public boolean canSpawnCoin()
	{
		if(hasCoin())
			return false;
		return true;
	}
	
	public boolean hasCoin() { return (coin != null); }
	public Algae getCoin() { return coin; }
	public void createCoin()
	{
		if(hasCoin()) return;
		
		coin = new Algae(this);
	}
	public void removeCoin()
	{
		coin.setDelayedDeath(true);
		coin = null;
	}
}
