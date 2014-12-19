package ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class UIElement {
	private float x, y;
	private float size_x, size_y;
	private CallbackFunction callback;
	public Sprite sprite;
	private BitmapFont font;
	private CharSequence text;
	
	public UIElement(CharSequence text, float x, float y, float size_x, float size_y)
	{
		this.x = x;
		this.y = y;
		this.size_x = size_x;
		this.size_y = size_y;
		this.text = text;
		
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		
		sprite = new Sprite();
		sprite.setTexture(new Texture("ui/button.png"));
		sprite.setPosition(x, y);
		sprite.setSize(size_x, size_y);

		callback = null;
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getSizeX() { return size_x; }
	public float getSizeY() { return size_y; }
	
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int size_x, int size_y)
	{
		this.size_x = size_x;
		this.size_y = size_y;
	}
	
	// Call the callback. Returns true if the callback was successful
	public boolean callback()
	{
		if(callback == null) return false;
		callback.func();
		return true;
	}
	
	// If you don't know what you're doing, best not to do this...
	public void setCallbackFunction(CallbackFunction func)
	{
		callback = func;
	}
	
	public CallbackFunction getCallbackFunction() { return callback; }
	
	// Does coordinates (x,y) intersect with the bounding box of the element?
	public boolean intersect(int x, int y)
	{
		return (x >= this.x && x <= this.x + this.size_x && y >= this.y && y <= this.y + this.size_y);
	}
	
	public void draw(Batch batch)
	{
		sprite.draw(batch);
		font.setScale(2);
		font.draw(batch, text, (getX() + size_x / 2) - (font.getBounds(text).width / 2), (getY() + size_y / 2) + (font.getBounds(text).height / 2));
	}
}