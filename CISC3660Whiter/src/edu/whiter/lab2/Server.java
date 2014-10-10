package edu.whiter.lab2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Server extends JFrame implements KeyListener
{
	MessagePanel message;
	Controller controller;
	
	public Server(Controller cont)
	{
		controller = cont;
		message = new MessagePanel();
		cont.setMessenger(message);
		add(message);
		setTitle("Game Server");
		setSize(510, 300);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		requestFocus();
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		char c = e.getKeyChar();
		controller.clientRequest(c);
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
}
