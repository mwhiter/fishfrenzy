package core;

public class Constants {
	public final static int TILE_SIZE = 64;
	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;//only used with GameLogic.java so far
	
	/* Spawning Fish */
	public final static int TIME_BETWEEN_FISH_WAVE_SPAWN = 15000;	// in ms, and probably shouldn't be a constant, because we might want to change this, time between group fish spawns
	public final static int TIME_BETWEEN_FISH_SPAWN = 250;			// in ms, might be wise to keep this as a constant. Time between individual fish spawns
	public final static int FISH_SPAWN_WAVE_SIZE = 5;				// how many fish spawn during a wave
	public final static int MAX_NUM_ACTIVE_FISH = 20;				// maximum amount of fish active at once
	
	public final static int FISH_SPEED = 5000;						// speed of the fish
}
