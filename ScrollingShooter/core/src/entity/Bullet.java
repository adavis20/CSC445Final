package entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity
{
	public Vector2 pos;
	public Vector2 vel;
	public static final int RADIUS = 5;
	
	public Bullet(Vector2 pos, Vector2 vel)
	{
		super(pos.cpy(), vel.cpy().nor());
		this.pos = pos.cpy();
		this.vel = vel.cpy();
	}
	
	public void draw(ShapeRenderer sr)
	{
		sr.setColor(Color.RED);
		sr.circle(pos.x, pos.y, RADIUS);
	}

	@Override
	public void update()
	{
		this.pos.add(this.vel);
	}
}
