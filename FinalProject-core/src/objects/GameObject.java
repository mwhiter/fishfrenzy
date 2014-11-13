package objects;

import com.badlogic.gdx.graphics.g2d.Sprite;


public abstract class GameObject
{
	private boolean bUpdatable;
	private boolean bDrawable;
	public Sprite sprite;
	
	public GameObject() {}
	
	// Abstract methods - subclasses must implement this
	public abstract void update(float deltaTime);
	public abstract void draw();
	
	public boolean isDrawable(){
		return bUpdatable;
	}
	public boolean isUpdatable(){
		return bDrawable;
	}
	public void setDrawable(boolean val){
		bUpdatable = val;
	}
	public void setUpdatable(boolean val){
		bDrawable = val;
	}
}
