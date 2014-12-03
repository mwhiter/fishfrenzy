package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

// Class: Entity
// Represents an object in the game world
	// Has both a draw and a controller
	// Example would be the mice that runs around. These are all entities
	// Generic class, should probably inherit from this
public class Entity extends GameObject
{	
	protected Vector2 velocity;
	
	public Entity(Texture texture)
	{
		sprite = new Sprite(texture);
		sprite.setCenter(0, 0);
		init();
	}
	
	public Entity(Texture texture, float x, float y)
	{
		sprite = new Sprite(texture);
		sprite.setCenter(x,  y);
		init();
	}
	
	private void init()
	{
		velocity = new Vector2(0,0);
		setUpdatable(true);
		setDrawable(true);
	}
	
	public void SetVelocity(float x, float y)
	{
		velocity.x = x;
		velocity.y = y;
	}
	
	public void SetVelocity(Vector2 v)
	{
		velocity.x = v.x;
		velocity.y = v.y;
	}
	
	@Override
	public void update(float deltaTime)
	{
		sprite.setX(sprite.getX() + velocity.x * deltaTime);
		sprite.setY(sprite.getY() + velocity.y * deltaTime);
	}
}
