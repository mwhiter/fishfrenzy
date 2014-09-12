package edu.cisc3660.lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	// Member variable initialization
	private ArrayList<Card> deck = new ArrayList<Card>();
	
	// Member methods
	// Constructor
	public Deck()
	{
		for(Rank r : Rank.values()){
			for(Suit s : Suit.values())
			{
				deck.add(new Card(s,r));
			}
		}
	}
	
	// Boilerplate code
	public List<Card> getDeck() { return Collections.unmodifiableList(deck); }
	public Card removeCardDeck(int index) { return deck.remove(index); }
	public void addOneCard(Card c) { deck.add(c); }
	
	// Remove one card of same rank and suit
	public void removeAParticularCard(Card c)
	{
		for(int i=deck.size()-1; i >= 0; i--)
		{
			if(deck.get(i).getRank() == c.getRank() && deck.get(i).getSuit() == c.getSuit())
			{
				deck.remove(i);
				break;
			}
		}
	}
	
	public void removeAllCardsOfRank(Rank r)
	{
		for(int i=deck.size()-1; i >= 0; i--)
		{
			if(deck.get(i).getRank() == r)
			{
				deck.remove(i);
			}
		}
	}
	
	@Override
	public String toString()
	{
		String s = "";
		for(Card c: deck)
		{
			s = s + c.getSuit().toString() + " " + c.getRank().toString() + "\n";
		}
		return s;
	}
}
