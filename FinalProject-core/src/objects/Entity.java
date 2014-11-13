package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

// Class: Entity
// Represents an object in the game world
	// Has both a draw and a controller
	// Example would be the mice that runs around. These are all entities
	// Generic class, should probably inherit from this
public class Entity extends GameObject
{	
	private Vector2 velocity;
	
	public Entity(Texture texture)
	{
		sprite = new Sprite(texture);
		sprite.setPosition(0, 0);
		velocity = new Vector2(0,0);
	}
	
	@Override
	public void update(float deltaTime)
	{
		sprite.setX(sprite.getX() + velocity.x * Gdx.graphics.getDeltaTime());
		sprite.setY(sprite.getY() + velocity.y * Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void draw()
	{
		
	}
}