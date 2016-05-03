package com.mygdx.sss;

import Entity.Bird;
import Entity.EntityManager;
import Entity.Player;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class ScrollingShooter extends ApplicationAdapter {
	SpriteBatch sb;
	ShapeRenderer sr;
	Map map;
	Player p;
	Bird b;
        EntityManager em;
	@Override
	public void create () {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
		map = new Map("map1");
                Vector2 pos = new Vector2(50,200);
                Vector2 direction = new Vector2(0,0);
		p = new Player(pos,direction);
                pos = new Vector2(100,400);
                direction = new Vector2();
                em = new EntityManager(p);
               // b = new Bird(pos,direction,p);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		p.update();
                em.update();
		sb.begin();
		sb.draw(new Texture(Gdx.files.internal("sprites/sky.png")), 0, 0, 1300, 600);
		checkPlayer();
		map.draw(sb);
		sb.end();
		sr.begin(ShapeRenderer.ShapeType.Filled);
		p.draw(sr);
                em.renderBirds(sr);
		sr.end();
	}
	
	private void checkPlayer()
	{
		//Move map to follow player
		if(p.getPos().x < 200)
		{
			if(map.offset < 0)
			{
				p.getPos().x = 200;
				map.offset += 10;
			}
		}
		else if(p.getPos().x > 1100)
		{
			if(map.offset > -2150)
			{
				p.getPos().x = 1100;
				map.offset -= 10;
			}
		}
		//Check bounds
		if(p.getPos().x < 50)
			p.getPos().x = 50;
		if(p.getPos().x > 1200)
			p.getPos().x = 1200;
		//Check Collision (Basic)
		for(int x = 0; x < 12; x++)
		{
			for(int y = 0; y < map.columns; y++)
			{
				MapPiece target = map.map[x][y];
				if(p.getPos().y < ((target.y*50)+MapPiece.TILESIZE) && target.type == '1'
						&& (p.getPos().x + 25) < (target.x*50)+MapPiece.TILESIZE
						&& (p.getPos().x + 25) > (target.x*50))
				{
					p.getPos().y = ((target.y*50)+MapPiece.TILESIZE);
				}
			}
		}
	}

//	public void checkInputs()
//	{
//		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
//		{
//			p.x -= 10;
//		}
//		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
//		{
//			p.x += 10;
//		}
//		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
//		{
//			p.y -= 10;
//		}
//		if(Gdx.input.isKeyPressed(Input.Keys.UP))
//		{
//			p.y += 10;
//		}
//	}
}
