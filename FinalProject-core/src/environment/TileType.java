package environment;

public enum TileType 
{
	TILE_EMPTY(0),			// empty
	TILE_SOLID(1),			// a wall, fish collide with this
	TILE_FISH_GATE(2),		// Fish spawn (where fish are spawned)
	TILE_PLAYER_GATE(3);	// Player gate (where fish can be captured)
	
	public int id;
	
	TileType(int id)
	{
		this.id = id;
	}
	
	// Don't need TileTypes for arrow directions
	// Tiles themselves store a direction, use that instead
	/*
	TILE_PLAYER1_LEFT,
	TILE_PLAYER1_RIGHT,
	TILE_PLAYER1_UP,
	TILE_PLAYER1_DOWN,
	TILE_PLAYER2_LEFT,
	TILE_PLAYER2_RIGHT,
	TILE_PLAYER2_UP,
	TILE_PLAYER2_DOWN
	*/
}