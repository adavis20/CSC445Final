/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.Random;

/**
 *
 * @author Akeem Davis
 */
public class EntityManager
{
	
	private final int MAX_ENEMIES = 60;
	private final Array<Bird> entities = new Array<Bird>();
	private final Array<Bird> birdsToRemove = new Array<Bird>();
	private final Array<Bullet> bulletsToRemove = new Array<Bullet>();
	Random rand;
	private final Player player;
	private int spawnCap;
	private int MAX_ALLOWED_BIRDS = 5;
	private int score = 0;
	private int increaseDifficulty = 5000;
	public boolean bossSpawned = false;
	public boolean bossKilled = false;
	private Boss boss;
        int sendBird =1500;
	
	public EntityManager(Player p)
	{
		player = p;
		rand = new Random();
		spawnCap = 3;
		score = 0;
		bossKilled = false;
		bossSpawned = false;
	}
	
	public void update()
	{
		createBirds();
		updateBirds();
		if(player.health < 50 && score >= sendBird){
                    Vector2 pos = new Vector2(Gdx.graphics.getWidth() + 10,
								rand.nextInt(Gdx.graphics.getHeight() / 2) + Gdx.graphics.getHeight() / 2);
                    Bird b = new Bird(pos, new Vector2(),player,-1);
                    entities.add(b);
                    Texture t = new Texture(Gdx.files.internal("sprites/heal.png"));
                    b.setTexture(t);
                    sendBird += 1500;
                }
		if(bossSpawned)
		{
			boss.player = player;
			updateBoss();

			if(boss.health <= 0)
			{
				boss = null;
				bossSpawned = false;
				bossKilled = true;
			}
		}
	}
	private void updateBoss()
	{
		if(boss != null)
			boss.update();
		
		for (Bullet b : player.bullets)
		{
			if (boss != null && boss.getBounds().overlaps(b.getBounds()))
			{
				boss.health--;
				bulletsToRemove.add(b);
			}
		}
		if (boss != null && boss.getBounds().overlaps(player.getBounds()))
		{
			player.health--;
		}
		

		for (BirdBullet b : boss.bullets)
		{
			if (player.getBounds().overlaps(b.getBounds()))
			{
				player.health-=3;
				bulletsToRemove.add(b);
				
			}
		}
	}
	
	private void createBirds()
	{
		if(score >= 10000 && !bossSpawned)
		{
			boss = new Boss(new Vector2(10,10), new Vector2());
			bossSpawned = true;
                        spawnCap = 5;
                        MAX_ALLOWED_BIRDS = 5;
		}
		if (score >= increaseDifficulty && !bossSpawned)
		{
			increaseDifficulty = increaseDifficulty + 5000;
			spawnCap = spawnCap + 2;
			MAX_ALLOWED_BIRDS = MAX_ALLOWED_BIRDS + 2;
		}
		if (entities.size < MAX_ENEMIES && entities.size < MAX_ALLOWED_BIRDS)
		{
			int spawnNum = rand.nextInt(spawnCap);
			for (int i = 0; i < spawnNum && entities.size < MAX_ALLOWED_BIRDS; i++)
			{
				int CASE = (int)(Math.random()*4);
				switch (CASE)
				{
					case 0:
					{
						Vector2 pos = new Vector2(-10,
								rand.nextInt(Gdx.graphics.getHeight() / 2) + Gdx.graphics.getHeight() / 2);
						Bird b = new Bird(pos, new Vector2(), player, CASE);
						entities.add(b);
						break;
					}
					case 1:
					{
						Vector2 pos = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), Gdx.graphics.getHeight() + 10);
						Bird b = new Bird(pos, new Vector2(), player, CASE);
						entities.add(b);
						break;
					}
					default:
					{
						Vector2 pos = new Vector2(Gdx.graphics.getWidth() + 10,
								rand.nextInt(Gdx.graphics.getHeight() / 2) + Gdx.graphics.getHeight() / 2);
						Bird b = new Bird(pos, new Vector2(), player, CASE);
						entities.add(b);
						break;
					}
				}
			}
		}
	}
	

	private void updateBirds()
	{
		for (Bird e : entities)
		{
			e.update();
			for (Bullet b : player.bullets)
			{
				if (e.getBounds().overlaps(b.getBounds()))
				{
					birdsToRemove.add(e);
                                        e.sound.dispose();
					bulletsToRemove.add(b);
					
				}
			}
			if (e.getBounds().overlaps(player.getBounds()) && e.dealDMG == 0)
			{
                            if(e.displace == -1){
                                player.health = player.health + 25;
                                birdsToRemove.add(e);
                            }else{
				e.dealDMG++;
				player.health = player.health - 1;
                            }
			}
		}
		removeBirds();
	}
	
	public void renderBirds(SpriteBatch sb)
	{
		for (Bird e : entities)
		{
			e.draw(sb);
		}
		if(bossSpawned)
			boss.draw(sb);
	}
	
	private void removeBirds()
	{
		score = score + (birdsToRemove.size * 100);
		entities.removeAll(birdsToRemove, false);
		player.bullets.removeAll(bulletsToRemove, false);
		birdsToRemove.clear();
	}
	
	public int getScore()
	{
		return score;
	}

	public Boss getBoss()
	{
		return boss;
	}

	public void setBoss(Boss boss)
	{
		this.boss = boss;
	}
}
