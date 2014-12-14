package core;

public class Constants {
	public final static int TILE_SIZE = 64;
	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;//only used with GameLogic.java so far
	
	public final static int TILE_COLLIDE_BOX = 8;	// Small box inside a tile which marks the center of it
	
	public final static int MAX_ARROWS = 3;
	
	/* Spawning Fish */
	public final static int FISH_DELAY = 3000;						// delay at start of game before fish can come out (in ms)
	public final static int TIME_BETWEEN_FISH_WAVE_SPAWN = 4000;	// in ms, and probably shouldn't be a constant, because we might want to change this, time between group fish spawns
	public final static int TIME_BETWEEN_FISH_SPAWN = 500;			// in ms, might be wise to keep this as a constant. Time between individual fish spawns // 100
	public final static int FISH_SPAWN_WAVE_SIZE = 6;				// how many fish spawn during a wave  //15
	public final static int MAX_NUM_ACTIVE_FISH = 100;				// maximum amount of fish active at once
	
	/* Spawning Coins */
	public final static int TIME_BETWEEN_COIN_WAVE_SPAWN = 10000;
	public final static int TIME_BETWEEN_COIN_SPAWN = 1000;
	public final static int COIN_SPAWN_WAVE_SIZE = 2;
	public final static int MAX_NUM_COINS = 4;
	
	/* Power Ups */
	public final static int TIME_FOR_DOUBLE_POINTS = 4000;
	public final static int TIME_FOR_FREEZE = 5000;
	
	/* AI Consts */
	// TODO: difficulty speeds up these values
	public final static int TIME_BETWEEN_AI_MOVE = 800;
	
	public final static int FISH_SPEED = 25000;						// speed of the fish
}
