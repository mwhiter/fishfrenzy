package objects;

// Class: Player
// A player in the game world.
	// Needs to have an isHuman() flag. Because only humans will accept input from the game board
public class Player {
	private boolean bHuman;
	
	public Player(boolean human)
	{
		bHuman = human;
	}
	
	// What does it mean to update a Player? Run its AI (this is called once per frame)
	// Is this really the best thing to do? Maybe not...discuss at some point
	public void update()
	{
		// Don't update for humans because they make their own decisions
		if(!isHuman()) return;
		
		// ai stuff here...
	}
	
	public boolean isHuman()
	{
		return bHuman;
	}
}
