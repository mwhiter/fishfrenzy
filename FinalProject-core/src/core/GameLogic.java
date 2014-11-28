package core;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import objects.Player;
import objects.Entity;
import environment.Grid;
import environment.TileType;
import environment.Tile;

//HAVE TO PRESS L TO place an Arrow


public class GameLogic {
	
	Grid grid;	// todo: level class that stores a grid instead?
	ArrayList<Player> players;
	ArrayList<Entity> entities;
	ArrayList<Tile> tiles;

// Constructor
	GameLogic()
	{
		// create a new 7x7 grid (constructor currently handles init)
		grid = new Grid(9, 9);
		tiles = new ArrayList<Tile>();
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
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {players.get(0).chooseDir(0);}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {players.get(0).chooseDir(1);}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {players.get(0).chooseDir(2);}
		if (Gdx.input.isKeyPressed(Input.Keys.F)) {players.get(0).chooseDir(3);}
		
	}
	
	public void processMouseInput()
	{
		float my, mx; int  tx, ty;
		tx = 0; ty = 8;
		//if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))///////////////////////////////////////
		if (Gdx.input.isKeyJustPressed(Input.Keys.L))
		{
			mx = Gdx.input.getX();
			my = Gdx.input.getY();
			for (float i = 0; i < 600; i+= 64)
			{//System.out.println("tx = "+tx);
				for (float j = 0; j < 600; j+=64)
				{//System.out.println("ty = "+ty);
					if (mx > i && mx < i+64 && my >j && my <j+64 )//&& tx!=0 && tx != 8 && ty!=0 && ty!=8 
					{
						TileType temp = grid.grid[tx][ty].getTileType();
						if (temp == TileType.TILE_SOLID || temp == TileType.TILE_FISH_GATE || temp == TileType.TILE_PLAYER_GATE || temp == TileType.TILE_PLAYER1_LEFT){}
						else	
							{	
							if (tiles.size() > 2 )
						    {
								grid.removeTile(tiles.get(0).getGx(),tiles.get(0).getGy());
						     	tiles.remove(0);
							}
							grid.setTile(tx,ty,players.get(0).iDirNum);
							tiles.add(new Tile(grid.grid[tx][ty].getTileType(),tx,ty));
							//for (int p =0; p < tiles.size(); p++)//System.out.println(tiles.get(p).getGx() + " " + tiles.get(p).getGy() + " " + tiles.get(p).getTileType());//System.out.println(tx + " " + ty);
							}
						return;
					}//System.out.println(tx + " " + ty);//1,1 top left. 7,7 bottom right 
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
