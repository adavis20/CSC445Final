/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
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
        private final Array<Bird> adjust = new Array<Bird>();
        private final Array<Bird> birdsToRemove = new Array<Bird>();
        private final Array<Bullet> bulletsToRemove = new Array<Bullet>();
	Random rand;
	private final Player player;
	private int spawnCap;
        private int MAX_ALLOWED_BIRDS = 5;
        private int score = 0;
        private int increaseDifficulty = 5000;
	
	public EntityManager(Player p)
	{
		player = p;
		rand = new Random();
		spawnCap = 3;
	}
	
	public void update()
	{
		createBirds();
		updateBirds();
	}
	
	private void createBirds()
	{
            if(score == increaseDifficulty){
                increaseDifficulty = increaseDifficulty + 5000;
                spawnCap = spawnCap + 2;
                MAX_ALLOWED_BIRDS = MAX_ALLOWED_BIRDS + 2;
            }
		if (entities.size < MAX_ENEMIES && entities.size < MAX_ALLOWED_BIRDS)
		{
			int spawnNum = rand.nextInt(spawnCap);
			for (int i = 0; i < spawnNum && entities.size < MAX_ALLOWED_BIRDS ; i++)
			{
				int CASE = rand.nextInt(3);
				switch (CASE)
				{
					case 0:
					{
						Vector2 pos = new Vector2(-10, rand.nextInt(Gdx.graphics.getHeight()/2)+Gdx.graphics.getHeight()/2);
						Bird b = new Bird(pos, new Vector2(), player,CASE);
						entities.add(b);
						break;
					}
					case 1:
					{
						Vector2 pos = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), Gdx.graphics.getHeight() + 10);
						Bird b = new Bird(pos, new Vector2(), player,CASE);
						entities.add(b);
						break;
					}
					default:
					{
						Vector2 pos = new Vector2(Gdx.graphics.getWidth() + 10, rand.nextInt(Gdx.graphics.getHeight()/2)+Gdx.graphics.getHeight()/2);
						Bird b = new Bird(pos, new Vector2(), player,CASE);
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
                        for(Bullet b: player.bullets){
                            if(e.getBounds().overlaps(b.getBounds())){
                                birdsToRemove.add(e);
                                bulletsToRemove.add(b);
                               
                            }
                        }
                        if(e.getBounds().overlaps(player.getBounds()) && e.dealDMG == 0){
                            e.dealDMG++;
                            player.health = player.health - 1;
                        }
		}
                removeBirds();
               // adjust.addAll(entities);
              //  adjustBirds();
	}
	private void adjustBirds(){
            for(Bird e: entities){
                for(Bird f: adjust){
                    if(!f.equals(e)){
//                        if(e.getBounds().overlaps(f.getBounds())){
//                           //Adjust bird out of the the way
//                           f.pos.add(rand., 0);
//                            }
                    }
                }
            }
            adjust.clear();
        }
	public void renderBirds(ShapeRenderer sr)
	{
		for (Bird e : entities)
		{
			e.draw(sr);
		}
	}
        private void removeBirds(){
            score = score + (birdsToRemove.size * 100);
            entities.removeAll(birdsToRemove,false);
            player.bullets.removeAll(bulletsToRemove,false);
            birdsToRemove.clear();
        }
        public int getScore(){
            return score;
        }
}
