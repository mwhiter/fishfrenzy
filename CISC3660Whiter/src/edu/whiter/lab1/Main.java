package edu.whiter.lab1;

import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		Deck deck = new Deck();
		
		System.out.println("Shuffling deck...");
		deck.shuffle();
		
		System.out.println(deck.toString());
		
		ArrayList<Card> a1 = new ArrayList<Card>();
		ArrayList<Card> a2 = new ArrayList<Card>();
		
		System.out.println("Dealing 10 cards to each player...");
		
		for(int i=0; i < 10; i++)
			a1.add(deck.dealCard());
		
		for(int i=0; i < 10; i++)
			a2.add(deck.dealCard());
		
		System.out.println("Player one's hand...");
		for(int i=0; i < a1.size(); i++)
			System.out.println(a1.get(i).getRank().toString());
		
		System.out.println("Player two's hand...");
		for(int i=0; i < a2.size(); i++)
			System.out.println(a2.get(i).getRank().toString());
		
		int sum_a1 = 0;
		int sum_a2 = 0;
		
		// this doesn't take into account suit...
		for(int i=0; i < a1.size(); i++)
			sum_a1 += a1.get(i).getRank().ordinal();
		
		for(int i=0; i < a2.size(); i++)
			sum_a2 += a2.get(i).getRank().ordinal();
		
		if(sum_a1 > sum_a2)
			System.out.println("(" + sum_a1 + ", " + sum_a2 + ") Player 1 wins!");
		else if(sum_a1 < sum_a2)
			System.out.println("(" + sum_a1 + ", " + sum_a2 + ") Player 2 wins!");
		else if(sum_a1 == sum_a2)
			System.out.println("(" + sum_a1 + ", " + sum_a2 + ") No one wins!");
	}
}
