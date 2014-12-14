package core;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import objects.Player;
import objects.GameObject;
import objects.Fish;
import environment.Grid;
import environment.Tile;

//HAVE TO PRESS L TO place an Arrow

public class GameLogic {
	
	private static Grid grid;
	private static ArrayList<Player> players;
	private static ArrayList<GameObject> gameObjects;
	private static ArrayList<Integer> deadIndices;
	
	private static long gameStartTime;
	
	private static long lastFishWaveSpawnTime;	// last time we've completed spawning a wave
	private static long lastFishSpawnTime;		// last time a fish was spawned during this wave
	private static int fishSpawnCount;			// amount of fish we've spawned in this wave
	private static int numFishActive;			// number of fish currently active
	
	private static long lastAlgaeWaveSpawnTime;	
	private static long lastAlgaeSpawnTime;	
	private static int algaeSpawnCount;
	private static int numAlgaeActive;
	
	private DirectionType waveSpawnDirection;
	
	// Victory conditions
	private int goalFish;
	private long timeLimit;
	
	// Constructor
	public GameLogic()
	{		
		// create players and entities
		players = new ArrayList<Player>();
		gameObjects = new ArrayList<GameObject>();
		
		// class specific helper structures
		deadIndices = new ArrayList<Integer>();
		
		// For now, just init two players - one human, one AI
		players.add(new Player(this, 0, true));
		players.add(new Player(this, 1, false));

		// Grid actually needs to know the players now so it has to come after they're initialized
		grid = new Grid(this, "grids/grid0.txt");

		gameStartTime = 0;
		lastFishWaveSpawnTime = -1;
		fishSpawnCount = 0;
		numFishActive = 0;
		
		lastAlgaeWaveSpawnTime = -1;
		algaeSpawnCount = 0;
		numAlgaeActive = 0;

		// TODO: Test victory conditions values! Fix it!
		goalFish = 200;		// 200 fish to capture
		timeLimit = 60000;	// 60 ms time limit
		gameStartTime = TimeUtils.millis();
		
		waveSpawnDirection = DirectionType.NO_DIRECTION;
	}
	
	public void update()
	{	
		// Fish spawn logic - pretty basic right now
		doCreateFish();
		doCreateAlgae();
		
		
		// Update players
		for(int i=0; i < players.size(); i++)
		{
			Player loopPlayer = players.get(i);
			loopPlayer.update(grid);
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
				if(loopObject instanceof Fish) changeNumFishActive(-1);
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
						loopObject.update(Gdx.graphics.getDeltaTime());
				}
			}
		}
		
		for(Integer index : deadIndices)
		{	
			try
			{
				@SuppressWarnings("unused")
				GameObject dead = gameObjects.remove((int)index);
				dead = null;
			}
			catch(IndexOutOfBoundsException e)
			{
				System.out.println("Tried to delete an object with index " + index);
			}
		}
		//System.out.println(TopLeft +  " " + TopRight + " "  + BottomLeft  + " "  + BottomRight);
		//System.out.println(mostFish() + "Has the most fish");
		//AIinput();
		//doCreateAI();
		//resetCount();//to keep track of fish locations Fish Locations
	}
	
	// Create fish. Will only run at set intervals
	private void doCreateFish()
	{
		long currentTime = TimeUtils.millis();
		
		if(numFishActive >= Constants.MAX_NUM_ACTIVE_FISH)
			return;
		
		// initial delay
		if(getTimeElapsedInGame() <= Constants.FISH_DELAY)
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
		
		if(waveSpawnDirection == DirectionType.NO_DIRECTION)
			pickNewFishDirection();
		
		gameObjects.add(new Fish(waveSpawnDirection, grid, new Texture("objects/fish.png"), grid.GetRandomFishSpawn()));
		
		if(fishSpawnCount >= Constants.FISH_SPAWN_WAVE_SIZE)
		{
			lastFishWaveSpawnTime = TimeUtils.millis();
			fishSpawnCount = 0;
			
			pickNewFishDirection();
		}
	}
	
	private void pickNewFishDirection()
	{
		int newDirection = MathUtils.random(0, 3);
		switch(newDirection)
		{
			case 0: waveSpawnDirection = DirectionType.DIRECTION_UP; break;
			case 1: waveSpawnDirection = DirectionType.DIRECTION_DOWN; break;
			case 2: waveSpawnDirection = DirectionType.DIRECTION_RIGHT; break;
			case 3: waveSpawnDirection = DirectionType.DIRECTION_LEFT; break;
			default: break;
		}
	}
	
	public void processKeyboardInput(int key)
	{
		if (key == Input.Keys.A) { players.get(0).setActiveDirection(DirectionType.DIRECTION_LEFT);		}
		if (key == Input.Keys.S) { players.get(0).setActiveDirection(DirectionType.DIRECTION_DOWN); 	}
		if (key == Input.Keys.D) { players.get(0).setActiveDirection(DirectionType.DIRECTION_RIGHT); 	}
		if (key == Input.Keys.W) { players.get(0).setActiveDirection(DirectionType.DIRECTION_UP); 		}
		
	}
	
	public void processMouseInput(float screenX, float screenY, int button)
	{
		if (button == Input.Buttons.LEFT)
		{	
			players.get(0).assignTile(grid, grid.getTile(screenX, screenY));
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
	
	public void doCreateAlgae()
	{
		long currentTime = TimeUtils.millis();
		
		if(numAlgaeActive >= Constants.MAX_NUM_COINS)
			return;
		
		if(lastAlgaeWaveSpawnTime != -1)
		{
			if(currentTime - Constants.TIME_BETWEEN_COIN_WAVE_SPAWN < lastAlgaeWaveSpawnTime)
				return;
		}
		
		if(lastAlgaeSpawnTime != -1)
		{
			if(currentTime - Constants.TIME_BETWEEN_COIN_SPAWN < lastAlgaeSpawnTime)
				return;
		}
		lastAlgaeSpawnTime = TimeUtils.millis();
		algaeSpawnCount++;
		numAlgaeActive++;

		Tile spawnTile = null;
		while(spawnTile == null)
		{
			spawnTile = grid.GetRandomValidTile();
			if(!spawnTile.canSpawnCoin())
			{
				spawnTile = null;
			}
		}
		
		spawnTile.createCoin();
		
		if(algaeSpawnCount >= Constants.COIN_SPAWN_WAVE_SIZE)
		{
			lastAlgaeWaveSpawnTime = TimeUtils.millis();
			algaeSpawnCount = 0;
		}
	}
	
	// Returns the distance between two tiles
	public static float getDistance(Tile t1, Tile t2)
	{
		return (float)Math.sqrt(Math.pow((double)(t2.getX()-t1.getX()), 2) + Math.pow((double)(t2.getY()-t1.getY()), 2));
	}
	
	public static void changeNumAlgaeActive(int iChange) { numAlgaeActive += iChange; }
	
	public int getNumFishActive() { return numFishActive; }
	public void changeNumFishActive(int iChange) { numFishActive += iChange; }
	
	public Player getPlayerMostFish()
	{
		Player rtnPlayer = null;
		int highestFish = -1;
		
		for(Player player : players)
		{
			if(player.getNumFishCaptured() > highestFish)
			{
				highestFish = player.getNumFishCaptured();
				rtnPlayer = player;
			}
		}
		
		return rtnPlayer;
	}
	
	public int getGoalFish() { return goalFish; }
	public void setGoalFish(int iValue) { goalFish = Math.max(0, iValue); }
	
	public long getTimeElapsedInGame() { return TimeUtils.millis() - gameStartTime; }
	public long getTimeRemaining() { return Math.max(0, timeLimit - getTimeElapsedInGame()); }
}
