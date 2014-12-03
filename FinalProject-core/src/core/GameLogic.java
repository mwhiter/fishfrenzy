package core;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import objects.Player;
import objects.GameObject;
import objects.Fish;
import environment.Grid;

//HAVE TO PRESS L TO place an Arrow

public class GameLogic {
	
	Grid grid;	// todo: level class that stores a grid instead?
	ArrayList<Player> players;
	ArrayList<GameObject> gameObjects;

	public long lastFishWaveSpawnTime;	// last time we've completed spawning a wave
	public long lastFishSpawnTime;		// last time a fish was spawned during this wave
	public int fishSpawnCount;			// amount of fish we've spawned in this wave
	public int numFishActive;			// number of fish currently active
	
	// Constructor
	GameLogic()
	{
		// create a new 7x7 grid (constructor currently handles init)
		grid = new Grid(0, 0, 9, 9);
		// create players and entities
		players = new ArrayList<Player>();
		gameObjects = new ArrayList<GameObject>();
	
		lastFishWaveSpawnTime = -1;
		lastFishWaveSpawnTime = -1;
		fishSpawnCount = 0;
		numFishActive = 0;
		
		// For now, just init two players - one human, one AI
		// it seems very wrong to send the grid inside this constructor, so todo is fix this and make grid accessible from classes
		players.add(new Player(grid, true));
		players.add(new Player(grid, false));
	}
	
	public void update()
	{
		processKeyboardInput();
		processMouseInput();
		
		
		
		doCreateFish();
		
		// Update players
		for(int i=0; i < players.size(); i++)
		{
			Player loopPlayer = players.get(i);
			
			loopPlayer.update();
			grid = loopPlayer.returnUpdatedGrid();
		}
		
		// Update the game objects
		for(int i=0; i < gameObjects.size(); i++)
		{
			GameObject loopObject = gameObjects.get(i);
			if(loopObject.isUpdatable())
			{
				if (loopObject.getFish()) // to send the grid to A Fish//////////////
				{
					loopObject.update(Gdx.graphics.getDeltaTime(),grid);
				}
				else
				loopObject.update(Gdx.graphics.getDeltaTime());/////////////////////
			}
		}
	}
	
	// Create fish. Will only run at set intervals
	public void doCreateFish()
	{
		long currentTime = TimeUtils.millis();
		
		if(numFishActive >= Constants.MAX_NUM_ACTIVE_FISH)
			return;
		
		// Wave of fish
		if(lastFishWaveSpawnTime != -1)
		{
			if(currentTime - Constants.TIME_BETWEEN_FISH_WAVE_SPAWN < lastFishWaveSpawnTime)
				return;
		}
		
		// Individual fish
		if(lastFishSpawnTime != -1)
		{
			if(currentTime - Constants.TIME_BETWEEN_FISH_SPAWN < lastFishSpawnTime)
				return;
		}
		
		// if we get to here, spawn a fish
		// Right now fish spawn at a random fish spawn. We have support for multiple fish spawns if we wanted to
		lastFishSpawnTime = TimeUtils.millis();
		fishSpawnCount++;
		numFishActive++;
		gameObjects.add(new Fish(new Texture("fish.png"), grid.GetRandomFishSpawn()));
		gameObjects.get(gameObjects.size()-1).setFish(); /////////////////SO it knows that the object is a fish
		
		if(fishSpawnCount >= Constants.FISH_SPAWN_WAVE_SIZE)
		{
			lastFishWaveSpawnTime = TimeUtils.millis();
			fishSpawnCount = 0;
		}
	}
	
	public void processKeyboardInput()
	{
		if (Gdx.input.isKeyPressed(Input.Keys.A)) { players.get(0).setActiveDirection(DirectionType.DIRECTION_LEFT); }
		if (Gdx.input.isKeyPressed(Input.Keys.S)) { players.get(0).setActiveDirection(DirectionType.DIRECTION_DOWN); }
		if (Gdx.input.isKeyPressed(Input.Keys.D)) { players.get(0).setActiveDirection(DirectionType.DIRECTION_RIGHT); }
		if (Gdx.input.isKeyPressed(Input.Keys.W)) { players.get(0).setActiveDirection(DirectionType.DIRECTION_UP); }
		
	}
	
	public void processMouseInput()
	{
		float my, mx;
		if (Gdx.input.isKeyJustPressed(Input.Keys.L))
		{
			// Store the mouse coordinates
			mx = Gdx.input.getX();
			my = Gdx.input.getY();
			
			// grid.getTile() is overloaded to accept mouse coordinates
			players.get(0).assignTile(grid.getTile(mx, my));
			return;
		}
		
	}
	public Grid getGrid() { return grid; }
	public ArrayList<Player> getPlayers() { return players; }
	public ArrayList<GameObject> getGameObjects() { return gameObjects; }
}
