package environment;

import com.badlogic.gdx.graphics.g2d.Sprite;

import core.Constants;

public class Tile {
	public Sprite sprite;
	int x, y;
	
	public Tile(int x, int y)
	{
		sprite = new Sprite();
		sprite.setPosition(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);
	}
	
	void draw()
	{
		
	}
}
