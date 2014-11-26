package environment;

//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;

import environment.TileType;
import core.Constants;

public class Tile {
	public Sprite sprite;
	public TileType tileType;
	int x, y;
	
	public Tile(TileType eTileType, int x, int y)
	{
		tileType = eTileType;
		sprite = new Sprite(getTexture(tileType));
		sprite.setPosition(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);
	}
	public float getTilePos() {return this.sprite.getY();}
	
	public TileType getTileType() { return tileType; }
	
	private Texture getTexture(TileType eType)
	{
		if(eType == TileType.TILE_SOLID)
			return new Texture("tile_solid.png");
		if(eType == TileType.TILE_FISH_GATE)
			return new Texture("tile_fish_gate.png");
		if(eType == TileType.TILE_PLAYER_GATE)
			return new Texture("tile_player_gate.png");
		
		if(eType == TileType.TILE_PLAYER1_LEFT)
			return new Texture("player1_left.png");
		if(eType == TileType.TILE_PLAYER1_RIGHT)
			return new Texture("player1_right.png");
		if(eType == TileType.TILE_PLAYER1_UP)
			return new Texture("player1_up.png");
		if(eType == TileType.TILE_PLAYER1_DOWN)
			return new Texture("player1_down.png");

		if(eType == TileType.TILE_PLAYER2_LEFT)
			return new Texture("player2_left.png");
		if(eType == TileType.TILE_PLAYER2_RIGHT)
			return new Texture("player2_right.png");
		if(eType == TileType.TILE_PLAYER2_UP)
			return new Texture("player2_up.png");
		if(eType == TileType.TILE_PLAYER2_DOWN)
			return new Texture("player2_down.png");
		
		
		
		return new Texture(("tile_empty.png"));
	}
}
