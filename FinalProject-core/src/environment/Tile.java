package environment;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;

import core.Constants;

public class Tile {
	public Sprite sprite;
	int x, y;
	
	public Tile(Texture tex, int x, int y)
	{
		sprite = new Sprite(tex);
		sprite.setPosition(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);
	}
}
