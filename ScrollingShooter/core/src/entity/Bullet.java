package entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Bullet
{
	public Vector2 pos;
	public Vector2 vel;
	public static final int RADIUS = 5;
	
	public Bullet(Vector2 pos, Vector2 vel)
	{
		this.pos = pos.cpy();
		this.vel = vel.cpy();
	}
	
	public void tick()
	{
		this.pos.add(this.vel);
	}
	
	public void draw(ShapeRenderer sr)
	{
		sr.setColor(Color.RED);
		sr.circle(pos.x, pos.y, RADIUS);
	}
}
