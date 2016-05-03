package com.mygdx.sss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map
{
	/**
	 * Maps must be made with a height of 12. width must be at least 26.
	 */
	public static final int ROWS = 11;
	MapPiece[][] map;
	String name;
	int columns;
	public int offset;
	
	public Map(String fileName)
	{
		name = fileName;
		columns = 0;
		offset = 0;
		map = produceMap(Gdx.files.internal("maps\\" + name + ".map"));
	}
	
	public void draw(SpriteBatch sb)
	{
		for (int x = 0; x < ROWS + 1; x++)
		{
			for (int y = 0; y < columns; y++)
			{
				map[x][y].draw(sb, offset);
			}
		}
	}
	
	public MapPiece[][] produceMap(FileHandle file)
	{
		String s = file.readString();
		int tempC = 0;
		for (int x = 0; x < s.length(); x++)
		{
			if (s.charAt(x) == '\n')
			{
				s = s.substring(0, x) + s.substring(x + 1);
				x--;
			}
			if (s.charAt(x) == '\r')
			{
				if (columns == 0)
					columns = tempC;
				s = s.substring(0, x) + s.substring(x + 1);
				x--;
			}
			tempC++;
		}
		MapPiece[][] map = new MapPiece[12][columns];
		for (int x = ROWS; x >= 0; x--)
		{
			for (int y = 0; y < columns; y++)
			{
				map[x][y] = new MapPiece(y, x, s.charAt(((ROWS - x) * columns) + y));
			}
		}
		
		return map;
	}
}
