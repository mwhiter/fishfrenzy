package objects;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import core.Constants;
import core.DirectionType;
import core.GameLogic;
import environment.Grid;
import environment.Tile;
import environment.TileType;

public class PlayerAI {
	private long lastTilePlacedTime;
	private Player player;
	private GameLogic logic;
	
	PlayerAI(Player player, GameLogic logic)
	{
		this.player = player;
		this.logic = logic;
		
		lastTilePlacedTime = -1;
	}
	
	public void update(Grid grid)
	{
		// Human players can't update their AI
		if(player.isHuman()) 
			return;
		
		if(!isTooSoonForArrow())
			placeArrow(grid);
	}
	
	// AI can't place arrows too quickly.
	public boolean isTooSoonForArrow()
	{
		long currentTime = TimeUtils.millis();
		
		// If it's not the first time we placed an arrow...
		if(lastTilePlacedTime != -1)
		{
			// is it too soon before we can place an arrow?
			if(currentTime - Constants.TIME_BETWEEN_AI_MOVE < lastTilePlacedTime)
				return true;
		}
		
		return false;
	}
	
	// Place an arrow on a tile
	public void placeArrow(Grid grid)
	{
		// scanFish() will return a quadrant with the most fish
		Tile[][] quadrant = scanFish(grid);
		if(quadrant.length == 0) return;
		
		Tile tile = pickTile(quadrant);
		if(tile == null) return;
		
		DirectionType direction = pickDirection(grid, tile);
		if(direction == DirectionType.NO_DIRECTION) return;
		
		player.setActiveDirection(direction); 
		player.assignTile(grid, tile);
		
		lastTilePlacedTime = TimeUtils.millis();
	}
	
	// Look through all the fish in the grid and place them into a quadrant. Whichever quadrant has the most fish, that's the one we process.
	// This function will return the quadrant with the most fish. If no fish are on the map, then it's empty.
	public Tile[][] scanFish(Grid g)
	{
		// Initialize four variables that correspond to each quadrant
		int topLeft = 0, topRight = 0, bottomLeft = 0, bottomRight = 0;
		int fishProcessed = 0;
		
		// grid is dynamic size. Since this is an integer, remember the mid of a 9x9 is 4.
		int midX = g.getWidth() / 2;
		int midY = g.getHeight() / 2;
		
		// Loop through the fish in the game
		for(GameObject obj : logic.getGameObjects())
		{
			if(obj instanceof Fish)
			{
				Fish f = (Fish) obj;
				
				// Process left first
				if ( f.getXloc() <= midX )
				{
					if ( f.getYloc() <= midY )
						topLeft++;
					else bottomLeft++; 
				}
				// Process right second
				else 
				{
					if ( f.getYloc() <= midY )
						topRight++;
					else bottomRight++; 
				}
				
				fishProcessed++;
			}
		}
		
		// No fish were processed
		if(fishProcessed == 0)
		{
			return new Tile[0][0];
		}
		
		int startIndexX = 0, startIndexY = 0;
		int endIndexX = 0, endIndexY = 0;
		
		// Top left
		if(topLeft >= topRight && topLeft >= bottomLeft && topLeft >= bottomRight)
		{
			startIndexX = 0;
			endIndexX = midX;
			startIndexY = 0;
			endIndexY = midY;
		}
		// Top right
		else if(topRight >= topLeft && topRight >= bottomLeft && topRight >= bottomRight)
		{
			startIndexX = midX;
			endIndexX = g.getWidth()-1;
			startIndexY = 0;
			endIndexY = midY;
		}
		// Bottom left
		else if(bottomLeft >= topRight && bottomLeft >= topLeft && bottomLeft >= bottomRight)
		{
			startIndexX = 0;
			endIndexX = midX;
			startIndexY = midY;
			endIndexY = g.getHeight()-1;
		}
		// Bottom right
		else if(bottomRight >= topRight && bottomRight >= bottomLeft && bottomRight >= topLeft)
		{
			startIndexX = midX;
			endIndexX = g.getWidth()-1;
			startIndexY = midY;
			endIndexY = g.getHeight()-1;
		}
		
		// Construct the quadrant
		Tile[][] quadrant = new Tile[endIndexX+1 - startIndexX][endIndexY+1 - startIndexY];
		
		for(int i = startIndexX; i <= endIndexX; i++)
		{
			for(int j = startIndexY; j <= endIndexY; j++)
			{
				quadrant[i-startIndexX][j-startIndexY] = g.getTile(i, j); 
			}
		}
		
		return quadrant;
	}
	
	// Pick a tile from a quadrant to use
	public Tile pickTile(Tile[][] quadrant)
	{
		Tile rtnTile = null;
		int highest = -1;
		
		// Look at each tile and assign a value to them
		for(int i=0; i < quadrant.length; i++)
		{
			for(int j=0; j < quadrant[i].length; j++)
			{
				int value = assignValue(quadrant[i][j]);
				if(value > highest)
				{
					highest = value;
					rtnTile = quadrant[i][j];
				}
			}
		}
		
		return rtnTile;
	}
	
	public int assignValue(Tile tile)
	{
		if(tile == null) return 0;
		
		int rtnValue = 0;
		
		if(!tile.canPlaceDirection(DirectionType.NO_DIRECTION))
			return 0;
		
		// if the tile contains a direction it's more valuable
		if(tile.getDirection() != DirectionType.NO_DIRECTION)
		{
			rtnValue += 10;
		}
		
		
		// If the tile is next to our base it's important
		float distanceFromMe = GameLogic.getDistance(tile, player.getHome());
		if(distanceFromMe < 4)
		{
			rtnValue += 20;
			
			// Direction on the tile
			if(tile.getDirection() != DirectionType.NO_DIRECTION)
			{
				// Not my direction!
				if(tile.getDirectionSetter() != player)
				{
					// defend my territory!
					rtnValue *= 2;
				}
			}
		}
		
		// Some randomness is always nice
		rtnValue += MathUtils.random(0, 30);
		
		return Math.max(0, rtnValue);
	}
	
	public DirectionType pickDirection(Grid g, Tile tile)
	{
		if(tile == null) return DirectionType.NO_DIRECTION;

		ArrayList<DirectionType> directions = new ArrayList<DirectionType>();
		
		for(DirectionType direction : DirectionType.values())
		{
			// only consider valid directions
			if(tile.canPlaceDirection(direction))
			{
				Tile home = player.getHome();
				
				Tile tileAhead = g.getTileInDirection(tile, direction);
				if(tileAhead == null) continue;
					
				if(tileAhead.getTileType() == TileType.TILE_PLAYER_GATE)
					if(tileAhead.getOwner() != player)
						continue;
				
				// If the distance between the tile in this direction is greater than the distance between the tile at home, then go here
				if(GameLogic.getDistance(tileAhead, home) < GameLogic.getDistance(tile, home))
					directions.add(direction);
					
			}
		}
		
		if(directions.isEmpty())
			return DirectionType.NO_DIRECTION;

		int index = MathUtils.random(0, directions.size()-1);
		return directions.get(index);
	}
}
