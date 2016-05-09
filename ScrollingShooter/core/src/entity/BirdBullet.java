package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BirdBullet extends Bullet
{
	private Texture texture;
	public BirdBullet(Vector2 pos, Vector2 vel)
	{
		super(pos, vel);
		texture = getSprite();
	}
	
	public void draw(SpriteBatch sb)
	{
		if (texture != null)
			sb.draw(texture, this.pos.x, this.pos.y, 25, 25);
	}
	
	@Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x,pos.y,25,25);
    }
	
	 private Texture getSprite()
	    {
			int CASE = (int)(Math.random()*4);
	    	switch(CASE)
	    	{
	    		case 1:
	    		{
	    	    	return (new Texture(Gdx.files.internal("sprites/bird1.png")));
	    		}
	    		case 2:
	    		{
	    	    	return (new Texture(Gdx.files.internal("sprites/bird2.png")));
	    		}
	    		case 3:
	    		{
	    	    	return (new Texture(Gdx.files.internal("sprites/bird3.png")));
	    		}
	    		case 4:
	    		{
	    	    	return (new Texture(Gdx.files.internal("sprites/bird4.png")));
	    		}

	    		default:
	    		{
	    	    	return (new Texture(Gdx.files.internal("sprites/bird1.png")));
	    		}
	    	}
	    }
}
