package edu.whiter.lab2;

import edu.whiter.lab1.Deck;

public class State {
	private byte turn; // 1 - 2 - 3
	private Player [] players; // players[0] is the dealer
	private Deck deck;
	
	public State()
	{
		init();
	}
	
	public void init()
	{
		turn = 1;
		deck = new Deck();
		deck.initFullDeck();
		deck.shuffle();
		players = new Player[Constants.NUM_PLAYERS];
		
		players[0] = new Player();
		players[0].receiveCard(deck.dealCard());
		
		// Give 2 cards to each Player
		for(int i = 1; i< Constants.NUM_PLAYERS; i++)
		{
			players[i] = new Player();
			players[i].receiveCard(deck.dealCard());
			players[i].receiveCard(deck.dealCard());
		}
	}
	
	public Player getPlayer(int playerNum)
	{
		if(playerNum >= 0 || playerNum < Constants.NUM_PLAYERS)		
			return players[playerNum];
		
		return null;
	}
	
	public Player getDealer()
	{
		return players[0];
	}
	
	public Deck getDeck() { return deck; }
	public byte getTurn() { return turn; }
	public void nextTurn() { turn++; }
}