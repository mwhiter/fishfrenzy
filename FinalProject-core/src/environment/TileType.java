package environment;

public enum TileType {
	TILE_EMPTY,			// empty
	TILE_SOLID,			// a wall, fish collide with this
	TILE_FISH_GATE,		// Fish spawn (where fish are spawned)
	TILE_PLAYER_GATE	// Player gate (where fish can be captured)
}