package edu.whiter.lab2;

import java.util.ArrayList;
import java.util.Collections;

import edu.whiter.lab1.Card;
import edu.whiter.lab1.Rank;

public class Player {
	private ArrayList<Card> hand;
	public PlayerState playerState; 
	
	// Constructor
	public Player()
	{
		hand = new ArrayList<Card>();
		playerState = PlayerState.WAIT;
	}
	
	public void receiveCard(Card c)
	{
		hand.add(c);
	}
	
	public ArrayList<Card> getHand()
	{
		return hand;
	}
	
	// Get the value of the hand. This is trickier than you'd think because of how aces work!
	public int getHandValue()
	{	
		int value = 0;
		
		// separate any aces for last
		ArrayList<Card> aces = new ArrayList<Card>();
		
		// Loop through the hand and get the value of the card
		for(Card c : hand)
		{
			// aces are a special case
			if(c.getRank() == Rank.ACE)
			{
				aces.add(c);
			}
			else
			{
				// face cards get a value of 10
				if(c.getRank() == Rank.JACK || c.getRank() == Rank.QUEEN || c.getRank() == Rank.KING)
				{
					value += 10;
				}
				// non-face cards get a value of 1 + ordinal()
				else
				{
					value += c.getRank().ordinal() + 1;
				}
			}
		}
		
		int minExtra = aces.size();	// the minimum number of value we have to add from aces
		int acesChecked = 0;		// how many aces we've processed so far
		
		// if the value of all the aces at value of 1 is greater than or equal to 21, don't even care if they're worth 10
		if(value + minExtra >= Constants.BLACKJACK_VALUE)
		{
			value += minExtra;
		}
		else
		{
			// Check the aces and determine their value
			for(Card c : aces)
			{
				// Count this ace as checked
				acesChecked++;
				
				// Basically, take our hand value (before aces), add in 11 and see if that will make the hand value go over 21. If so, don't want that
				int newValueIfAceIsEleven = value + 11 + (minExtra - acesChecked);
				
				if(newValueIfAceIsEleven > Constants.BLACKJACK_VALUE)
				{
					value += 1;
				}
				else
				{
					value += 11;
				}
			}
		}
		
		return value;
	}
	
	public String getHandString()
	{
		String handStr = "";
		for(Card c : hand)
		{
			handStr += c.getRank() + " ";
		}
		return handStr;
	}
}
