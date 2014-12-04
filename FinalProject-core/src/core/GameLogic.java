package core;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import objects.Alge;
import objects.Player;
import objects.GameObject;
import objects.Fish;
import environment.Grid;

//HAVE TO PRESS L TO place an Arrow

public class GameLogic {
	
	private Grid grid;	// todo: level class that stores a grid instead?
	private ArrayList<Player> players;
	private ArrayList<GameObject> gameObjects;
	private ArrayList<Integer> deadIndices;
	
	private long lastFishWaveSpawnTime;	// last time we've completed spawning a wave
	private long lastFishSpawnTime;		// last time a fish was spawned during this wave
	private int fishSpawnCount;			// amount of fish we've spawned in this wave
	private int numFishActive;			// number of fish currently active
	
	private long lastAlgeWaveSpawnTime;	
	private long lastAlgeSpawnTime;	
	private int algeSpawnCount;
	private int numAlgeActive;
	
	// Constructor
	GameLogic()
	{		
		// create players and entities
		players = new ArrayList<Player>();
		gameObjects = new ArrayList<GameObject>();
		
		// class specific helper structures
		deadIndices = new ArrayList<Integer>();
		
		// For now, just init two players - one human, one AI
		players.add(new Player(true));
		players.add(new Player(false));

		// Grid actually needs to know the players now so it has to come after they're initialized
		grid = new Grid(this, "grids/grid0.txt");

		lastFishWaveSpawnTime = -1;
		fishSpawnCount = 0;
		numFishActive = 0;
		lastAlgeWaveSpawnTime = -1;
		algeSpawnCount = 0;
		numAlgeActive = 0;
	}
	
	public void update()
	{
		processKeyboardInput();
		processMouseInput();
		
		// Fish spawn logic - pretty basic right now
		doCreateFish();
		doCreateAlge();
		
		// Update players
		for(int i=0; i < players.size(); i++)
		{
			Player loopPlayer = players.get(i);
			loopPlayer.update();
			//grid = loopPlayer.returnUpdatedGrid();
		}
		
		deadIndices.clear();
		
		// Update the game objects
		for(int i=0; i < gameObjects.size(); i++)
		{
			GameObject loopObject = gameObjects.get(i);
			
			// Object marked for death - no processing but kill it instead
			if(loopObject.isDelayedDeath())
			{
				deadIndices.add(i);
			}
			else
			{
				if(loopObject.isUpdatable())
				{
					// Update fish
					if (loopObject instanceof Fish)
					{
						((Fish) loopObject).update(Gdx.graphics.getDeltaTime(), grid);
					}
					// Update other objects
					else
						loopObject.update(Gdx.graphics.getDeltaTime());/////////////////////
				}
			}
		}
		
		for(Integer index : deadIndices)
		{
			GameObject dead = gameObjects.remove((int)index);
			dead = null;
		}
	}
	
	// Create fish. Will only run at set intervals
	private void doCreateFish()
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
		
		if(fishSpawnCount >= Constants.FISH_SPAWN_WAVE_SIZE)
		{
			lastFishWaveSpawnTime = TimeUtils.millis();
			fishSpawnCount = 0;
		}
	}
	
	private void processKeyboardInput()
	{
		if (Gdx.input.isKeyPressed(Input.Keys.A)) { players.get(0).setActiveDirection(DirectionType.DIRECTION_LEFT); }
		if (Gdx.input.isKeyPressed(Input.Keys.S)) { players.get(0).setActiveDirection(DirectionType.DIRECTION_DOWN); }
		if (Gdx.input.isKeyPressed(Input.Keys.D)) { players.get(0).setActiveDirection(DirectionType.DIRECTION_RIGHT); }
		if (Gdx.input.isKeyPressed(Input.Keys.W)) { players.get(0).setActiveDirection(DirectionType.DIRECTION_UP); }
		
	}
	
	private void processMouseInput()
	{
		float my, mx;
		if (Gdx.input.isKeyJustPressed(Input.Keys.L))
		{
			// Store the mouse coordinates
			mx = Gdx.input.getX();
			my = Gdx.input.getY();
			
			players.get(0).assignTile(grid, grid.getTile(mx, my));
			return;
		}
		
	}
	
	public Grid getGrid() { return grid; }
	public Player getPlayer(int index)
	{
		if(index < 0 || index >= players.size()) return null;
		return players.get(index);
	}
	public ArrayList<Player> getPlayers() { return players; }
	public ArrayList<GameObject> getGameObjects() { return gameObjects; }
	
	public void doCreateAlge()
	{
		System.out.println(numAlgeActive);
		long currentTime = TimeUtils.millis();
		
		if(numAlgeActive >= Constants.MAX_NUM_ACTIVE_FISH)
			return;
		
		if(lastAlgeWaveSpawnTime != -1)
		{
			if(currentTime - Constants.TIME_BETWEEN_FISH_WAVE_SPAWN < lastAlgeWaveSpawnTime)
				return;
		}
		
		if(lastAlgeSpawnTime != -1)
		{
			if(currentTime - Constants.TIME_BETWEEN_FISH_SPAWN < lastAlgeSpawnTime)
				return;
		}
		lastAlgeSpawnTime = TimeUtils.millis();
		algeSpawnCount++;
		numAlgeActive++;
		float randomX = MathUtils.random(100,500);
		float randomY = MathUtils.random(100,500);
		gameObjects.add(new Alge(new Texture("AlgeCoin.png"), grid.getTile(randomX, randomY)));
		//gameObjects.get(gameObjects.size()-1).setFish(); /////////////////SO it knows that the object is a fish
		
		if(algeSpawnCount >= Constants.FISH_SPAWN_WAVE_SIZE)
		{
			lastAlgeWaveSpawnTime = TimeUtils.millis();
			algeSpawnCount = 0;
		}
	}
	
	
	
}
