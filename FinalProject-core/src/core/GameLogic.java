package core;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import objects.Player;
import objects.Entity;

import environment.Grid;

public class GameLogic {
	
	Grid grid;	// todo: level class that stores a grid instead?
	ArrayList<Player> players;
	ArrayList<Entity> entities;
	
	// Constructor
	GameLogic()
	{
		// create a new 7x7 grid (constructor currently handles init)
		grid = new Grid(7, 7);
		
		// create players and entities
		players = new ArrayList<Player>();
		entities = new ArrayList<Entity>();
		
		// For now, just init two players - one human, one AI
		players.add(new Player(true));
		players.add(new Player(false));
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
		
	}
	
	public void processMouseInput()
	{
		
	}
	
	public Grid getGrid() { return grid; }
	public ArrayList<Player> getPlayers() { return players; }
	public ArrayList<Entity> getEntities() { return entities; }
}
