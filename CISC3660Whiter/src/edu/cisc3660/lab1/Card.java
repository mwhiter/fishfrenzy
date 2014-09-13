package edu.cisc3660.lab1;

public class Card implements Comparable<Card>{
	// Member variable initialization
	private Suit suit;
	private Rank rank;
	
	// Member methods
	// Constructor
	public Card(Suit suit, Rank rank)
	{
		this.suit = suit;
		this.rank = rank;
	}
	
	// Do these cards have the same rank and suit?
	public static boolean compare(Card c1, Card c2)
	{
		return compareRank(c1, c2) && compareSuit(c1, c2);
	}
	
	// Do these cards have the same rank
	public static boolean compareRank(Card c1, Card c2)
	{
		return c1.rank == c2.rank;
	}
	
	// Do these cards have the same suit?
	public static boolean compareSuit(Card c1, Card c2)
	{
		return c1.suit == c2.suit;
	}
	
	// What is the value of this card (for sort purposes)
	public int CardValue()
	{
		final int NUMBER_SUITS = 4;
		
		return rank.ordinal() * NUMBER_SUITS + suit.ordinal();
	}
	
	@Override
	public int compareTo(Card c)
	{
		return this.CardValue() - c.CardValue();
	}
	
	// Boilerplate code
	public Suit getSuit() { return suit; }
	public void setSuit(Suit suit) { this.suit = suit; }
	public Rank getRank() { return rank; }
	public void setRank(Rank rank) { this.rank = rank; }
}
