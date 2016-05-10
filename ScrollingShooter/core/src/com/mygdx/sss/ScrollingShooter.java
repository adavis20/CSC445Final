package com.mygdx.sss;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import entity.Bird;
import entity.EntityManager;
import entity.Player;

public class ScrollingShooter extends ApplicationAdapter
{
	SpriteBatch sb;
	ShapeRenderer sr;
	BitmapFont bf;
	Map map;
	Player p;
	Bird b;
	EntityManager em;
	Music music;
	boolean boss = false;
	
	public char state = 's';
	
	@Override
	public void create()
	{
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		bf = new BitmapFont();
		sr.setAutoShapeType(true);
		map = new Map("map1");
		Vector2 pos = new Vector2(50, 200);
		Vector2 direction = new Vector2(0, 0);
		p = new Player(pos, direction);
		pos = new Vector2(100, 400);
		direction = new Vector2();
		em = new EntityManager(p);
		boss = false;
	}
	
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		checkInputs();
		
		switch(state)
		{
			case 's':
			{
				sr.begin(ShapeRenderer.ShapeType.Filled);
				sr.setColor(Color.DARK_GRAY);
				sr.rect(400, 350, 500, 300);
				sr.end();

				sb.begin();
				bf.setColor(Color.GOLD);
				bf.draw(sb, "Press 'Enter' to start.", 600, 75);
				sb.draw(new Texture(Gdx.files.internal("sprites/birdemic.png")), 400, 100, 500, 500);
				sb.end();
				break;
			}
			case 'g':
			{
                            
				//Updates
				p.update();
				em.update();
				//Checks
				checkPlayer();
				if(em.bossKilled)
				{
					state = 's';
					gameRestart();
				}
				//Sprite Batch
				sb.begin();
				sb.draw(new Texture(Gdx.files.internal("sprites/sky.png")), 0, 0, 1300, 600);
				map.draw(sb);
				p.draw(sb);
				em.renderBirds(sb);
				bf.setColor(Color.GOLD);
				bf.draw(sb, "Health: " + p.health, 30, 30);
				bf.draw(sb, "Score: " + em.getScore(), 120, 30);
				if(em.bossSpawned)
				{
					bf.draw(sb, "Boss Health: " + em.getBoss().health, 200, 30);
				}
				sb.end();
				//Shape Renderer
				sr.begin(ShapeRenderer.ShapeType.Filled);
				p.draw(sr);
				sr.end();
				break;
			}
			default:
			{
				break;
			}
		}
	}
	
	private void checkPlayer()
	{
		//Check health
		if(p.health <= 0)
		{
			state = 's';
			//restart
			gameRestart();
		}
		// Move map to follow player
		if(!em.bossSpawned)
		{
			if (p.pos.x < 200)
			{
				if (map.offset < 0)
				{
					p.pos.x = 200;
					map.offset += 10;
				}
			}
			else if (p.pos.x > 1100)
			{
				if (map.offset > -2150)
				{
					p.pos.x = 1100;
					map.offset -= 10;
				}
			}
		}
		else
		{
			if (p.pos.x < 500)
			{
				if (map.offset < 0)
				{
					p.pos.x = 500;
					map.offset += 10;
				}
			}
			else if (p.pos.x > 1100)
			{
				if (map.offset > -2150)
				{
					p.pos.x = 1100;
					map.offset -= 10;
				}
			}
		}
		// Check bounds
		if (p.pos.x < 50)
			p.pos.x = 50;
		if (p.pos.x > 1200)
			p.pos.x = 1200;
		if (p.pos.y > 550)
			p.pos.y = 550;
		if (p.pos.y < 50)
			p.pos.y = 50;
		// Check Collision (Basic)
		for (int x = 0; x < 12; x++)
		{
			for (int y = 0; y < map.columns; y++)
			{
				MapPiece target = map.map[x][y];
				switch (target.type)
				{
					case '1':
					{
						if (p.pos.y < ((target.y * MapPiece.TILESIZE) + MapPiece.TILESIZE)
								&& (p.pos.x + (15)) < (target.x * MapPiece.TILESIZE + map.offset) + MapPiece.TILESIZE
								&& (p.pos.x + (15)) > (target.x * MapPiece.TILESIZE + map.offset))
						{
							p.pos.y = ((target.y * 50) + MapPiece.TILESIZE);
							p.jumping = false;
						}
					}
					case '2':
					{
						if (p.pos.y < ((target.y * MapPiece.TILESIZE) + MapPiece.TILESIZE)
								&& p.pos.y > ((target.y * MapPiece.TILESIZE) + MapPiece.TILESIZE - 15)
								&& (p.pos.x) < (target.x * MapPiece.TILESIZE + map.offset) + MapPiece.TILESIZE
								&& (p.pos.x + (MapPiece.TILESIZE)) > (target.x * MapPiece.TILESIZE + map.offset))
						{
							p.pos.y = ((target.y * 50) + MapPiece.TILESIZE);
							p.jumping = false;
						}
						else if (p.pos.y + MapPiece.TILESIZE > ((target.y * MapPiece.TILESIZE))
								&& p.pos.y + MapPiece.TILESIZE < ((target.y * MapPiece.TILESIZE) + 15)
								&& (p.pos.x) < (target.x * MapPiece.TILESIZE + map.offset) + MapPiece.TILESIZE
								&& (p.pos.x + (MapPiece.TILESIZE)) > (target.x * MapPiece.TILESIZE + map.offset))
						{
							p.pos.y = ((target.y * 50) - MapPiece.TILESIZE);
						}
						if (p.pos.x + MapPiece.TILESIZE > ((target.x * MapPiece.TILESIZE) + map.offset)
								&& p.pos.x + MapPiece.TILESIZE < ((target.x * MapPiece.TILESIZE) + 15 + map.offset)
								&& (p.pos.y + (15)) < (target.y * MapPiece.TILESIZE) + MapPiece.TILESIZE
								&& (p.pos.y + (15)) > (target.y * MapPiece.TILESIZE))
						{
							p.pos.x = (target.x * MapPiece.TILESIZE) - MapPiece.TILESIZE + map.offset;
						}
						else if (p.pos.x < ((target.x * MapPiece.TILESIZE) + map.offset + MapPiece.TILESIZE)
								&& p.pos.x > ((target.x * MapPiece.TILESIZE) + map.offset + MapPiece.TILESIZE - 15)
								&& (p.pos.y + (15)) < (target.y * MapPiece.TILESIZE) + MapPiece.TILESIZE
								&& (p.pos.y + (15)) > (target.y * MapPiece.TILESIZE))
						{
							p.pos.x = ((target.x * MapPiece.TILESIZE) + MapPiece.TILESIZE) + map.offset;
						}
					}
				}
				
			}
		}
	}
	
	public void gameRestart()
	{
		map = new Map("map1");
		Vector2 pos = new Vector2(50, 200);
		Vector2 direction = new Vector2(0, 0);
		p = new Player(pos, direction);
		em = new EntityManager(p);
		boss = false;
	}
	
	public void checkInputs()
	{
		// Horizontal Movement
		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A))
		{
			p.accel.x = -2;
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))
		{
			p.accel.x = 2;
		}
		else
		{
			p.accel.x = 0;
		}
		// Jumping Controls
		if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)  || Gdx.input.isKeyPressed(Keys.SPACE))
		{
			if (!p.jumping)
			{
				p.jumping = true;
				p.accel.y = 20;
			}
		}
		// Shooting Controls
		p.setDirection(Gdx.input.getX(), 600 - Gdx.input.getY());
		if (Gdx.input.justTouched())
			p.shoot();
		//Misc
		if (Gdx.input.isKeyJustPressed(Keys.ENTER))
		{
			if(state == 's')
			{
				state = 'g';
                                music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
                                music.play();
			}
			else if(state == 'g')
			{
                                 music.dispose();
				state = 's';
			}
		}
	}
}
