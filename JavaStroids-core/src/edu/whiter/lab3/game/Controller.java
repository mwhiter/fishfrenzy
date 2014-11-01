package edu.whiter.lab3.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;

import edu.whiter.lab3.gameobjects.Asteroid;
import edu.whiter.lab3.gameobjects.GameObject;
import edu.whiter.lab3.gameobjects.Ship;

public class Controller {
	
	ArrayList<GameObject> drawableObjects; 
	Ship ship;
	
	public Controller(){
		drawableObjects = new ArrayList<GameObject>(); 
		initShip();
		initAsteroids(10);
	}
	
	private void initShip(){
		int w = Constants.SHIP_WIDTH; 
		int h = Constants.SHIP_HEIGHT; 
		Pixmap pmap = new Pixmap(w, h, Format.RGBA8888); // TODO: Check Image Format
		pmap.setColor(1, 1, 1, 1);
		pmap.drawLine(0, h, w/2, 0);
		pmap.drawLine(w, h, w/2, 0);
		pmap.drawLine(1, h-1, w, h-1);
		ship = new Ship(new Texture(pmap), 100, 100);
		drawableObjects.add(ship);
	}
	
	private void initAsteroids(int num){
		Random rand = new Random();
		for(int i = 0; i<num; i++){
			Asteroid asteroid = new Asteroid(new Texture("Asteroid_tex.png"));
			asteroid.sprite.setPosition(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
			asteroid.sprite.setOrigin(asteroid.sprite.getWidth() / 2, asteroid.sprite.getHeight() / 2);
			asteroid.setRotVel(rand.nextFloat()*8-4);
			drawableObjects.add(asteroid);
		}
	}
	
	public void update(){
		processKeyboardInput();
		
		// Update Asteroids
		for(GameObject gObg : drawableObjects){
			if(gObg instanceof Asteroid){
				((Asteroid) gObg).update(Gdx.graphics.getDeltaTime()); 
			}
		}
		// Update ship
		ship.update(Gdx.graphics.getDeltaTime());
	}
	
	private void processKeyboardInput(){
		if (Gdx.app.getType() != ApplicationType.Desktop) return; // Just in case :)
		if (Gdx.input.isKeyPressed(Keys.UP)) 
			ship.moveForward(Gdx.graphics.getDeltaTime());
		// Student, your code goes here
	}
	
	public ArrayList<GameObject> getDrawableObjects(){
		return drawableObjects;
	}
}
