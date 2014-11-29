package core;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import objects.Player;
import objects.Entity;
import environment.Grid;
import environment.Tile;

//HAVE TO PRESS L TO place an Arrow

public class GameLogic {
	
	Grid grid;	// todo: level class that stores a grid instead?
	ArrayList<Player> players;
	ArrayList<Entity> entities;

	// Constructor
	GameLogic()
	{
		// create a new 7x7 grid (constructor currently handles init)
		grid = new Grid(0, 0, 9, 9);
		// create players and entities
		players = new ArrayList<Player>();
		entities = new ArrayList<Entity>();
	
		// For now, just init two players - one human, one AI
		// it seems very wrong to send the grid inside this constructor, so todo is fix this and make grid accessible from classes
		players.add(new Player(grid, true));
		players.add(new Player(grid, false));
	}
	
	public void update()
	{
		processKeyboardInput();
		processMouseInput();
		
		// Update players
		for(int i=0; i < players.size(); i++)
		{
			Player loopPlayer = players.get(i);
			
			loopPlayer.update();
		}
		
		// Update the entities
		for(int i=0; i < entities.size(); i++)
		{
			Entity loopEntity = entities.get(i);
			if(loopEntity.isUpdatable())
			{
				loopEntity.update(Gdx.graphics.getDeltaTime());
			}
		}
	}
	
	public void processKeyboardInput()
	{
		if (Gdx.input.isKeyPressed(Input.Keys.A)) { players.get(0).setActiveDirection(DirectionTypes.DIRECTION_LEFT); }
		if (Gdx.input.isKeyPressed(Input.Keys.S)) { players.get(0).setActiveDirection(DirectionTypes.DIRECTION_DOWN); }
		if (Gdx.input.isKeyPressed(Input.Keys.D)) { players.get(0).setActiveDirection(DirectionTypes.DIRECTION_RIGHT); }
		if (Gdx.input.isKeyPressed(Input.Keys.W)) { players.get(0).setActiveDirection(DirectionTypes.DIRECTION_UP); }
		
	}
	
	public void processMouseInput()
	{
		float my, mx;
		//if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))///////////////////////////////////////
		if (Gdx.input.isKeyJustPressed(Input.Keys.L))
		{
			// Store the mouse coordinates
			mx = Gdx.input.getX();
			my = Gdx.input.getY();
			
			// geid.getTile() is overloaded to accept mouse coordinates
			players.get(0).assignTile(grid.getTile(mx, my));
			return;

		}
		
	}
	public Grid getGrid() { return grid; }
	public ArrayList<Player> getPlayers() { return players; }
	public ArrayList<Entity> getEntities() { return entities; }
}
