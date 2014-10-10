package edu.whiter.lab2;

import edu.whiter.lab1.Card;

public class Controller {
	MessagePanel msg;
	boolean playing = false;
	State state;
	int activePlayer;
	
	public Controller(){}
	
	public void init()
	{
		state = new State();
		
		reset();
	}
	
	public void reset()
	{
		// Initialize player states
		for(int i=0; i < Constants.NUM_PLAYERS; i++)
		{
			Player player = state.getPlayer(i);
			
			player.playerState = PlayerState.WAIT;
			
			if(player.getHandValue() == Constants.BLACKJACK_VALUE)
				player.playerState = PlayerState.BLACKJACK;
		}
		
		activePlayer = 1;
		playing = true;
		outputMessages();
	}
	
	public int getActivePlayer()
	{
		return activePlayer;
	}
	
	public void outputMessages()
	{
		for(int i = 1; i < Constants.NUM_PLAYERS; i++)
		{
			// Game in progress
			if(playing)
			{	
				// We're the active player
				if(i == getActivePlayer())
				{
					msg.outputMessage(i,  "It is your turn. Your hand: "
							+ state.getPlayer(i).getHandString()
							+ " ("
							+ state.getPlayer(i).getHandValue()
							+ ") Dealer's hand:"
							+ state.getDealer().getHandString());
				}
				// Not the active player
				else
				{
					switch(state.getPlayer(i).playerState)
					{
					case WAIT:
						msg.outputMessage(i, Constants.WAIT_MSG);
						break;
					case BLACKJACK:
						msg.outputMessage(i, Constants.BLACKJACK);
						break;
					case BUST:
						msg.outputMessage(i, Constants.BUST);
						break;
					case STAY:
						msg.outputMessage(i, "You have ended your turn. Your hand: "
								+ state.getPlayer(i).getHandString()
								+ " ("
								+ state.getPlayer(i).getHandValue()
								+ ") Dealer's hand:"
								+ state.getDealer().getHandString());
						break;
					default:
						break;
					}
				}
			}
			else
			{
				// End of the round - dealer is the active player now
				if(getActivePlayer() == 0)
				{
					switch(state.getPlayer(i).playerState)
					{
					case WIN:
						msg.outputMessage(i, Constants.WON);
						break;
					case DRAW:
						msg.outputMessage(i,  Constants.DRAW);
						break;
					case LOSE:
						msg.outputMessage(i,  Constants.LOST);
						break;
					default:
						msg.outputMessage(i,  "Error!");
						break;
					}
				}
				
				msg.outputMessage(4, "Dealer's hand:" + state.getDealer().getHandString());
			}
		}
	}
	
	public void clientRequest(char c)
	{
		if(playing)
		{
			// Decide based on c
			changeState(c);
			return;
		}
		
		init();
	}
	
	public void setMessenger(MessagePanel message)
	{
		msg = message;
		msg.showMenu();
	}
	
	private void showErrorMsgPlayerKey(char c)
	{
		int player = -1;
		if(c == 'q' || c == 'w')
		{
			player = 1;
		}
		else if(c == 'a' || c == 's')
		{
			player = 2;
		}
		else if(c == 'z' || c == 'x')
		{
			player = 3;
		}
		
		if(player > 0 && player < Constants.NUM_PLAYERS)
			msg.outputMessage(player, Constants.WRONG_TURN_MSG);
	}
	
	// returns false if c is not valid
	private boolean validateKey(char c)
	{
		if(getActivePlayer() == 1)
			if(c != 'q' && c != 'w')
				return false;
		
		if(getActivePlayer() == 2)
			if(c != 'a' && c != 's')
				return false;
		
		if(getActivePlayer() == 3)
			if(c != 'z' && c != 'x')
				return false;
		
		return true;
	}
	
	private void changeState(char c)
	{
		if(!validateKey(c))
		{
			showErrorMsgPlayerKey(c);
			return;
		}
		
		switch(c)
		{
		case 'q':
			hit(1);
			break;
		case 'w':
			stay(1);
			break;
		case 'a':
			hit(2);
			break;
		case 's':
			stay(2);
			break;
		case 'z':
			hit(3);
			break;
		case 'x':
			stay(3);
			break;
		}
	}
	
	// special function for the dealer. Returns false if a card couldn't be deal (for some reason?)
	// this is horrible practice and I would never consider doing something like this but time is short for me and I really don't want to spend too much time making this 'pretty'
	private boolean dealerHit()
	{
		Card card = state.getDeck().dealCard();
		if(card == null)
			return false;
		
		state.getDealer().receiveCard(card);
		return true;
	}
	
	private void hit(int iPlayer)
	{
		// Not iPlayer's turn!
		if(getActivePlayer() != iPlayer)
			return;
		
		// Don't do anything for dealer.
		if(iPlayer == 0)
			return;
		
		// Retrieve a card from the deck and give it to player
		Player player = state.getPlayer(iPlayer);
		Card card = state.getDeck().dealCard();
		
		// card doesn't exist for some reason - just stay instead...
		if(card == null)
		{
			stay(iPlayer);
			return;
		}
		
		// give the card to the player
		player.receiveCard(card);
		
		int handValue = player.getHandValue();
		
		// check his hand value
		if(handValue == Constants.BLACKJACK_VALUE)
		{
			player.playerState = PlayerState.BLACKJACK;
			endTurn();
		}
		else if(handValue > Constants.BLACKJACK_VALUE)
		{
			player.playerState = PlayerState.BUST;
			endTurn();
		}
		else
		{
			outputMessages();
		}
	}
	
	private void stay(int iPlayer)
	{
		// Not iPlayer's turn!
		if(getActivePlayer() != iPlayer)
			return;
		
		// Don't do anything for dealer.
		if(iPlayer == 0)
			return;
		
		Player player = state.getPlayer(iPlayer);
		player.playerState = PlayerState.STAY;
		endTurn();
	}
	
	private void endTurn()
	{
		// Advance the active player by 1
		activePlayer += 1;
				
		// The round is over, process the dealer.
		if(activePlayer >= Constants.NUM_PLAYERS)
		{
			// Dealer must hit on less than 17
			while(state.getDealer().getHandValue() < 17)
			{
				// dealerHit() will return false if it couldn't deal a card
				// yeah, it's stupid, I would never do this in real code
				if(!dealerHit())
					break;
			}
			
			// Dealer busts
			if(state.getDealer().getHandValue() > Constants.BLACKJACK_VALUE)
			{
				for(int i = 1; i < Constants.NUM_PLAYERS; i++)
				{
					if(state.getPlayer(i).playerState != PlayerState.BUST)
					{
						state.getPlayer(i).playerState = PlayerState.WIN;
					}
					else
					{
						state.getPlayer(i).playerState = PlayerState.LOSE;
					}
				}
			}
			// Dealer hits blackjack
			else if(state.getDealer().getHandValue() == Constants.BLACKJACK_VALUE)
			{
				// Everyone loses
				for(int i = 1; i < Constants.NUM_PLAYERS; i++)
				{
					state.getPlayer(i).playerState = PlayerState.LOSE;
				}
			}
			else
			{
				for(int i = 1; i < Constants.NUM_PLAYERS; i++)
				{
					if(state.getPlayer(i).playerState != PlayerState.BUST)
					{
						if(state.getPlayer(i).getHandValue() > state.getDealer().getHandValue())
						{
							state.getPlayer(i).playerState = PlayerState.WIN;
						}
						else if(state.getPlayer(i).getHandValue() < state.getDealer().getHandValue())
						{
							state.getPlayer(i).playerState = PlayerState.LOSE;
						}
						else
						{
							state.getPlayer(i).playerState = PlayerState.DRAW;
						}
					}
					else
					{
						state.getPlayer(i).playerState = PlayerState.LOSE;
					}
				}
			}
			
			// set the active player to the dealer
			activePlayer = 0;
			playing = false;
		}
		// Round is not over - make sure the player wasn't dealt blackjack
		else
		{
			// If dealt two cards that are blackjack...just end the turn
			if(state.getPlayer(activePlayer).playerState == PlayerState.BLACKJACK)
			{
				endTurn();
			}
		}
		
		// Start the new turn here - Update the messages
		outputMessages();
	}
}
