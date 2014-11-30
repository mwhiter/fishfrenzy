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
	
	public boolean isDrawable(){
		return bDrawable;
	}
	public boolean isUpdatable(){
		return bUpdatable;
	}
	public void setDrawable(boolean val){
		bDrawable = val;
	}
	public void setUpdatable(boolean val){
		bUpdatable = val;
	}
}
