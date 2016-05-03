package com.mygdx.sss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapPiece
{
	public static final int TILESIZE = 50;
	public int x;
	public int y;
	public char type;
	Texture texture;
	
	public MapPiece(int x, int y, char type)
	{
		this.x = x;
		this.y = y;
		this.type = type;
		texture = getSprite(type);
	}
	
	private Texture getSprite(char type)
	{
		Texture t;
		if (type == '0')
		{
			t = null;
		}
		else if (type == '1')
		{
			t = new Texture(Gdx.files.internal("sprites/floorTop.png"));
		}
		else if (type == '2')
		{
			t = new Texture(Gdx.files.internal("sprites/brick.png"));
		}
		else if (type == '3')
		{
			t = new Texture(Gdx.files.internal("sprites/floorBottom.png"));
		}
		else
		{
			t = null;
		}
		return t;
	}
	
	public void draw(SpriteBatch sb, int offset)
	{
		if (texture != null)
			sb.draw(texture, x * TILESIZE + offset, y * TILESIZE, TILESIZE, TILESIZE);
	}
}
