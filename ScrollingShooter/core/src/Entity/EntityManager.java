/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.Random;

/**
 *
 * @author Akeem Davis
 */
public class EntityManager {

    private final int MAX_ENEMIES = 10;
    private final Array<Bird> entities = new Array<Bird>();
    Random rand;
    private final Player player;
    private final int spawnCap;

    public EntityManager(Player p) {
        player = p;
        rand = new Random();
        spawnCap = 5;
    }

    public void update() {
        createBirds();
        updateBirds();
    }

    private void createBirds() {
        if (entities.size < MAX_ENEMIES) {
            int spawnNum = rand.nextInt(spawnCap);
            for (int i = 0; i < spawnNum; i++) {
                int CASE = rand.nextInt(2);
                switch (CASE) {
                    case 0:
                        {
                            Vector2 pos = new Vector2(-10, rand.nextInt(Gdx.graphics.getHeight()));
                            Bird b = new Bird(pos, new Vector2(), player);
                            entities.add(b);
                            break;
                        }
                    case 1:
                        {
                            Vector2 pos = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), Gdx.graphics.getHeight()+10);
                            Bird b = new Bird(pos, new Vector2(), player);
                            entities.add(b);
                            break;
                        }
                    default:
                        {
                            Vector2 pos = new Vector2(Gdx.graphics.getWidth()+10, rand.nextInt(Gdx.graphics.getHeight()));
                            Bird b = new Bird(pos, new Vector2(), player);
                            entities.add(b);
                            break;
                        }
                }
            }
        }
    }
    private void updateBirds(){
        for(Entity e: entities){
            e.update();
        }
    }
    public void renderBirds(ShapeRenderer sr){
          for(Bird e: entities){
            e.draw(sr);
        }
    }
}
