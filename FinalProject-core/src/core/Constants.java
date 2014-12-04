package core;

public class Constants {
	public final static int TILE_SIZE = 64;
	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;//only used with GameLogic.java so far
	
	public final static int TILE_COLLIDE_BOX = 8;	// Small box inside a tile which marks the center of it
	
	/* Spawning Fish */
	public final static int TIME_BETWEEN_FISH_WAVE_SPAWN = 4000;	// in ms, and probably shouldn't be a constant, because we might want to change this, time between group fish spawns
	public final static int TIME_BETWEEN_FISH_SPAWN = 100;			// in ms, might be wise to keep this as a constant. Time between individual fish spawns
	public final static int FISH_SPAWN_WAVE_SIZE = 15;				// how many fish spawn during a wave
	public final static int MAX_NUM_ACTIVE_FISH = 100;				// maximum amount of fish active at once
	
	/* Spawning Coins */
	public final static int TIME_BETWEEN_COIN_WAVE_SPAWN = 10000;
	public final static int TIME_BETWEEN_COIN_SPAWN = 1000;
	public final static int COIN_SPAWN_WAVE_SIZE = 2;
	public final static int MAX_NUM_COINS = 4;
	
	public final static int FISH_SPEED = 20000;						// speed of the fish
}
