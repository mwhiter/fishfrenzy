package core;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import objects.Player;
import objects.GameObject;
import objects.Fish;
import environment.Grid;
import environment.Tile;

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
	
	private static long dbPowerTime = -1;
	private static long fzPowerTime  = -1;
	
	public static OrthographicCamera cam;
	private DirectionType waveSpawnDirection;
	
	// Victory conditions
	private int goalFish;
	private long timeLimit;
	
	// Time keeping
	private long currentTime;
	private long pauseStartedTime;
	private long pauseEndedTime;
	
	private Player winner;
	
	// Constructor
	public GameLogic(long timeLimit)
	{
		// create players and entities
		players = new ArrayList<Player>();
		gameObjects = new ArrayList<GameObject>();
		
		// class specific helper structures
		deadIndices = new ArrayList<Integer>();
		
		// For now, just init two players - one human, one AI
		players.add(new Player(this, 1, true));
		players.add(new Player(this, 2, false));

		// Grid actually needs to know the players now so it has to come after they're initialized
		grid = new Grid(this, "grids/grid0.txt");

		gameStartTime = TimeUtils.millis();
		lastFishWaveSpawnTime = -1;
		fishSpawnCount = 0;
		numFishActive = 0;
		
		lastAlgaeWaveSpawnTime = -1;
		algaeSpawnCount = 0;
		numAlgaeActive = 0;

		goalFish = 50;					
		this.timeLimit = timeLimit;
		
		gameStartTime = TimeUtils.millis();
		waveSpawnDirection = DirectionType.NO_DIRECTION;
		
		currentTime = 0;
		pauseStartedTime = 0;
		pauseEndedTime = 0;
		
		winner = null;
		cam = new OrthographicCamera(Constants.WIDTH,Constants.HEIGHT);
		cam.update();
	}
	
	public void update()
	{
		currentTime = getTimePauseStarted() + (TimeUtils.millis() - getTimePauseEnded());
		
		doCreateFish();
		doCreateAlgae();
		
		checkDBPowerUp();
		checkFZPowerUp();
		
		// Check victory conditions
		if(checkVictory())
			return;
		
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
	
	private boolean checkVictory()
	{
		Player winner = null;
		
		// Time limit
		if(getTimeRemaining() == 0)
		{
			int highestCount = -1;
			// Highest score
			// Loop through all the players
			for(int i=0; i < players.size(); i++)
			{
				if (players.get(i).getScore() > highestCount)
				{
					winner = players.get(i);
					highestCount = players.get(i).getScore();
				}
				// tie at the highest
				else if(players.get(i).getScore() == highestCount)
				{
					winner = null;
				}
			}
			
			// someone has to win (for now)
			if(winner == null)
				winner = players.get(0);
		}
		// See if anyone has captured the max fish
		else
		{
			// Loop through all the players
			for(int i=0; i < players.size(); i++)
			{
				if (players.get(i).getScore() >= goalFish)
				{
					winner = players.get(i);
					break;
				}
			}
		}
		
		// We have a winner! (or time ran out...)
		if(winner != null)
		{
			this.winner = winner;
			return true;
		}
		
		return false;
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
		
		if (key == Input.Keys.R) 
		{
			if (players.get(0).getCoins() >= 2)  
			{players.get(0).useCoins(2);rmPowerUP();}
		}
		if (key == Input.Keys.T) 
		{
			if (players.get(0).getCoins() >= 3)  
			{players.get(0).useCoins(4);FZPowerUP();}
		}
		if (key == Input.Keys.Y) 
		{
			if (players.get(0).getCoins() >= 4)  
			{players.get(0).useCoins(6);dbPowerUP();}
		}
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
	
	public void rmPowerUP()
	{
		if (players.get(1).getTiles().size() >= 1)
		{
			int rm = MathUtils.random(0, players.get(1).getTiles().size()-1);
			players.get(1).unassignTile(players.get(1).getTiles().get(rm));
		}
	}
	public void dbPowerUP()
	{
		players.get(0).setDBPowerup(true);
	}
		
	public void checkDBPowerUp ()
	{
	   if (players.get(0).getDBPowerup() == false)
		{
			dbPowerTime = TimeUtils.millis();
			return;
		}
		else 
		{
			long currentTime = TimeUtils.millis();
			if(dbPowerTime != -1)
			{
				if(currentTime - Constants.TIME_FOR_DOUBLE_POINTS > dbPowerTime)
					players.get(0).setDBPowerup(false);
			}
		}
	}
	public void FZPowerUP()
	{
		players.get(1).setFZPowerup(true);
	}
	public void checkFZPowerUp()
	{
	   if (players.get(1).getFZPowerup() == false)
		{
			fzPowerTime = TimeUtils.millis();
			return;
		}
		else 
		{
			long currentTime = TimeUtils.millis();
			if(fzPowerTime != -1)
			{
				if(currentTime - Constants.TIME_FOR_FREEZE > fzPowerTime)
					players.get(1).setFZPowerup(false);
				//Sets the value to true but does not change it back to false
			}
		}
	}
	
	public Player getWinner() { return winner; }
	
	public void setTimePauseStarted(long time) { pauseStartedTime = time; }
	public long getTimePauseStarted() { return pauseStartedTime; }
	
	public void setTimePauseEnded(long time) { pauseEndedTime = time; }
	public long getTimePauseEnded() { return pauseEndedTime; }
	
	public long getPauseDuration() { return getTimePauseEnded() - getTimePauseStarted(); }
	
	public int getGoalFish() { return goalFish; }
	public void setGoalFish(int iValue) { goalFish = Math.max(0, iValue); }
	
	public void setCurrentTime(long newTime) { currentTime = newTime;}
	public long getCurrentTime() { return currentTime; }
	
	public long getTimeElapsedInGame() { return currentTime - gameStartTime; }
	public long getTimeRemaining() { return Math.max(0, timeLimit - getTimeElapsedInGame()); }
}
