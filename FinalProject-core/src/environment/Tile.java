package environment;

//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;

import environment.TileType;
import core.Constants;
import core.DirectionTypes;

// A tile in the grid
// Tiles have a tile type and a direction type
	// tile type: innate, never-changing tile type (solid, empty, etc.)
	// direction type: changes on player input, directs the entities
public class Tile {
	public Sprite sprite;
	public TileType tileType;
	public DirectionTypes direction;
	
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
		
		tileType = eTileType;
		direction = DirectionTypes.NO_DIRECTION;
		sprite = new Sprite(getTexture(tileType));
		sprite.setPosition(real_x, real_y);
	}
	
	public float getTilePos() {return this.sprite.getY();}
	
	// tiles X and Y's should never change outside of the constructor!
	//public void setX(int x) {x = x;}
	//public void setY(int y) {y = y;}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public DirectionTypes getDirection() { return direction; }
	
	public TileType getTileType() { return tileType; }
	
	private Texture getTexture(TileType eType)
	{
		if(eType == TileType.TILE_SOLID)
			return new Texture("tile_solid.png");
		if(eType == TileType.TILE_FISH_GATE)
			return new Texture("tile_fish_gate.png");
		if(eType == TileType.TILE_PLAYER_GATE)
			return new Texture("tile_player_gate.png");
		
		return new Texture(("tile_empty.png"));
	}
	
	public void setTileDirection(DirectionTypes eDirection)
	{
		if(tileType != TileType.TILE_EMPTY) return;
		
		direction = eDirection;

		if(direction == DirectionTypes.NO_DIRECTION)
			sprite.setTexture(new Texture("tile_empty.png"));
		if(direction == DirectionTypes.DIRECTION_LEFT)
			sprite.setTexture(new Texture("player1_left.png"));
		if(direction == DirectionTypes.DIRECTION_RIGHT)
			sprite.setTexture(new Texture("player1_right.png"));
		if(direction == DirectionTypes.DIRECTION_UP)
			sprite.setTexture(new Texture("player1_up.png"));
		if(direction == DirectionTypes.DIRECTION_DOWN)
			sprite.setTexture(new Texture("player1_down.png"));

		// Player 2 stuff
		// To-do: because we need to keep track of who actually placed the tile down!
		// What might be useful is to have a draw() function for the tile
		/*
		if(direction == DirectionTypes.DIRECTION_LEFT)
			sprite.setTexture(new Texture("player2_left.png"));
		if(direction == DirectionTypes.DIRECTION_RIGHT)
			sprite.setTexture(new Texture("player2_right.png"));
		if(direction == DirectionTypes.DIRECTION_UP)
			sprite.setTexture(new Texture("player2_up.png"));
		if(direction == DirectionTypes.DIRECTION_DOWN)
			sprite.setTexture(new Texture("player2_down.png"));
		*/
	}
}
