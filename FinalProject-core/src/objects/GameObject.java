package objects;

import com.badlogic.gdx.graphics.g2d.Sprite;

import environment.Grid;


public abstract class GameObject
{
	private boolean bUpdatable;
	private boolean bDrawable;
	public boolean isFish = false;
	public Sprite sprite;
	
	public GameObject() {}
	
	// Abstract methods - subclasses must implement this
	public abstract void update(float deltaTime);
	public abstract void update(float deltaTime, Grid grid);
	
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
	public void setFish(){
		isFish = true;
	}
	public boolean getFish(){
		return isFish;
	}
}
