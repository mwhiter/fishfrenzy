package core;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import objects.Player;
import objects.Entity;
import environment.Grid;

public class GameLogic {
	
	Grid grid;	// todo: level class that stores a grid instead?
	ArrayList<Player> players;
	ArrayList<Entity> entities;
	
	// Constructor
	GameLogic()
	{
		// create a new 7x7 grid (constructor currently handles init)
		grid = new Grid(9, 9);
		
		// create players and entities
		players = new ArrayList<Player>();
		entities = new ArrayList<Entity>();
		
		// For now, just init two players - one human, one AI
		players.add(new Player(true));
		players.add(new Player(false));
	}
	
	public void update()
	{
		processKeyboardInput();
		processMouseInput();
		
		// Update players
		for(int i=0; i < players.size(); i++)
		{
			Player loopPlayer = players.get(i);
			
			loopPlayer.update();
		}
		
		// Update the entities
		for(int i=0; i < entities.size(); i++)
		{
			Entity loopEntity = entities.get(i);
			if(loopEntity.isUpdatable())
			{
				loopEntity.update(Gdx.graphics.getDeltaTime());
			}
		}
	}
	
	public void processKeyboardInput()
	{
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {Player.chooseDir(0);}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) 	{Player.chooseDir(1);}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {Player.chooseDir(2);}
		if (Gdx.input.isKeyPressed(Input.Keys.F)) 	{Player.chooseDir(3);}
		
	}
	
	public void processMouseInput()
	{
		float my, mx; int  tx, ty;
		tx = 0; ty = 8;
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			mx = Gdx.input.getX();
			my = Gdx.input.getY();
			for (float i = 0; i < 600; i+= 64)
			{
				for (float j = 0; j < 600; j+=64)
				{
					if (mx > i && mx < i+64 && my >j && my <j+64)
					{
						Grid.setTile(tx,ty,Player.iDirNum);
					}
						//System.out.println(tx + " " + ty);//1,1 top left. 7,7 bottom right 
					ty--;
				}
				ty = 8;
				tx++;
			}
			//System.out.println( Gdx.input.getX() + " " + Gdx.input.getY() );
		}
		
	}
	
	public Grid getGrid() { return grid; }
	public ArrayList<Player> getPlayers() { return players; }
	public ArrayList<Entity> getEntities() { return entities; }
}
