package environment;

public enum TileType {
	TILE_EMPTY,			// empty
	TILE_SOLID,			// a wall, fish collide with this
	TILE_FISH_GATE,		// Fish spawn (where fish are spawned)
	TILE_PLAYER_GATE,	// Player gate (where fish can be captured)
	TILE_PLAYER1_LEFT,
	TILE_PLAYER1_RIGHT,
	TILE_PLAYER1_UP,
	TILE_PLAYER1_DOWN,
	TILE_PLAYER2_LEFT,
	TILE_PLAYER2_RIGHT,
	TILE_PLAYER2_UP,
	TILE_PLAYER2_DOWN
	
}