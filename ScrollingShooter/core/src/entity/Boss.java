package entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Boss extends Entity
{
	public int health = 100;
	private Texture texture1 = new Texture(Gdx.files.internal("sprites/bossBirdClosed.jpg"));
	private Texture texture2 = new Texture(Gdx.files.internal("sprites/bossBirdOpen.jpg"));
	private int textureNum = 1;
	public Player player;
	public ArrayList<BirdBullet> bullets;
	ArrayList<Bullet> toRemove = new ArrayList<Bullet>();
	public static final int MAX_BULLETS = 20;
	private final int WIDTH = 400;
	private final int HEIGHT = 500;
	
	private int shotCounter = 0;
	
	public Boss(Vector2 pos, Vector2 direction)
	{
		super(pos, direction);
		bullets = new ArrayList<BirdBullet>();
	}

	@Override
	public void update()
	{
		if(player != null)
		{
			Vector2 t = player.pos.cpy().sub(this.pos.cpy());
			this.direction = t.nor();
			
			if(shotCounter % 50 == 0)
			{
				if (bullets.size() < MAX_BULLETS)
				{
					bullets.add(new BirdBullet(this.pos.cpy().add(25, 25), this.direction.cpy().scl(20, 20)));
				}
				textureNum = 1;
			}
			else if(shotCounter % 25 == 0 && (shotCounter % 50 != 0))
			{
				textureNum = 0;
			}
			shotCounter++;
			
			
			// Bullets tick
			for (Bullet b : bullets)
			{
				b.update();
	                        
				if (b.pos.x > 1300 || b.pos.x < 0 || b.pos.y < 0 || b.pos.y > 600)
				{
					toRemove.add(b);
				}
			}
			bullets.removeAll(toRemove);
	                toRemove.clear();
		}
	}

	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(pos.x, pos.y, WIDTH, HEIGHT);
	}
	
	public void draw(SpriteBatch sb)
	{
		if(textureNum == 1)
		{
			if (texture1 != null)
				sb.draw(texture1, super.pos.x, super.pos.y, WIDTH, HEIGHT);
		}
		else
		{
			if (texture2 != null)
				sb.draw(texture2, super.pos.x, super.pos.y, WIDTH, HEIGHT);
		}

		for (BirdBullet b : bullets)
		{
			b.draw(sb);
		}
	}
}
