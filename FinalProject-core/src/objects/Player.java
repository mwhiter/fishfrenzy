package objects;

// Class: Player
// A player in the game world.
	// Needs to have an isHuman() flag. Because only humans will accept input from the game board
public class Player {
	private boolean bHuman;
	private int iFishCaptured;	// number of fish captured
	private int iScore;			// actual game score (could be different from fish captured
	public static int iDirNum;
	
	// Constructor
	public Player(boolean human)
	{
		bHuman = human;
		iScore = 0;
		iFishCaptured = 0;
		iDirNum = 0;
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
	public static void chooseDir(int n){iDirNum  = n;}
	
	public int getNumFishCaptured() { return iFishCaptured; }
	public int getScore()			{ return iScore; }
}
