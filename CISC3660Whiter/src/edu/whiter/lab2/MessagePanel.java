package edu.whiter.lab2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class MessagePanel  extends JPanel
{
	String[] message;
	boolean sendMessage;
	boolean showMenu;
	
	public MessagePanel()
	{
		message = new String[4];
		message[1] = Constants.WAIT_MSG;
		message[2] = Constants.WAIT_MSG;
		message[3] = "";
	}
	
	public void outputMessage(int player, String msg)
	{
		message[player-1] = msg;
		sendMessage = true;
		repaint();
	}
	
	public void showMenu()
	{
		showMenu = true;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	
	private void draw(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		if(sendMessage)
		{
			g2d.drawString("Message to player 1: ", 10, 15);
			g2d.drawString("Message to player 2: ", 10, 90);
			g2d.drawString("Message to player 3: ", 10, 160);
			g2d.drawString(message[0], 10, 35);
			g2d.drawString(message[1], 10, 110);
			g2d.drawString(message[2], 10, 180);
			g2d.drawString(message[3], 10, 250);
			sendMessage = false;
		}
		else if(showMenu)
		{
			g2d.drawString("Welcome to this extremely sophisticated version of Blackjack!", 55, 45);
			g2d.drawString("Press any key to start a new game", 145, 95);
			g2d.drawString("INSTRUCTIONS:", 25, 125);
			g2d.drawString("Player 1: 'q' to request a card, 'w' to stand (Stop asking for more cards)", 25, 145);
			g2d.drawString("Player 2: 'a' to request a card, 's' to stand ", 25, 165);
			g2d.drawString("Player 3: 'z' to request a card, 'x' to stand ", 25, 185);
		}
	}
}
