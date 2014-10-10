package edu.whiter.lab2;

public class Blackjack {
	public static void main(String[] args)
	{
		startBlackjack();
	}
	
	public static void startBlackjack()
	{
		Server server = new Server(new Controller());
	}
}
