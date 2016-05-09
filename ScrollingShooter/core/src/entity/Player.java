package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.Random;

public class Player extends Entity implements Steerable<Vector2>
{
	// Necessary
	public Vector2 pos;
	public Vector2 vel;
	public Vector2 accel;
	public int health;
	public boolean jumping;
	Random rand = new Random();
	public Array<Bullet> bullets;
	Array<Bullet> toRemove = new Array<Bullet>();
	// Junk
	float orientation;
	float angularVelocity;
	boolean tagged;
	float boundingRadius;
	float maxLinearSpeed;
	float maxLinearAcceleration;
	float maxAngularSpeed;
	float maxAngularAcceleration;
	double DEGREES_TO_RADIANS = (double) (Math.PI / 180);
	boolean independentFacing;
	
	// Constants
	public static final int MAX_ACCEL = 10;
	public static final int MAX_VEL = 10;
	public static final int MAX_BULLETS = 20;
	public static final Vector2 GRAVITY = new Vector2(0, -2);
        Texture t;
	
	public Player(Vector2 pos, Vector2 direction)
	{
		super(pos, direction);
		this.pos = pos;
		vel = new Vector2(0, 0);
		accel = new Vector2(0, 0);
		direction = new Vector2(0, 0);
		jumping = false;
		bullets = new Array<Bullet>();
		health = 100;
                t = new Texture(Gdx.files.internal("sprites/player.png"));
	}
	public Player(Vector2 pos, Vector2 direction, boolean t){
                super(pos, direction);
		this.pos = pos;
        }
	@Override
	public void update()
	{
		// Slow character after moving
		if (accel.x == 0 && vel.x != 0)
		{
			if (vel.x > 0.4f)
				vel.x -= 0.8f;
			else if (vel.x < -0.4f)
				vel.x += 0.8f;
			else
				vel.x = 0;
		}
		if (accel.y > 0.4f)
			accel.y -= 0.8f;
		else if (accel.y < -0.4f)
			accel.y += 0.8f;
		else
			accel.y = 0;
		Vector2 tempAccel = accel.cpy().add(GRAVITY);
		vel.add(tempAccel);
		truncate(vel, MAX_VEL);
		pos.add(vel);
		
		// Bullets tick
		for (Bullet b : bullets)
		{
			b.update();
			if (b.pos.x > 1300 || b.pos.x < 0 || b.pos.y < 0 || b.pos.y > 600)
			{
				toRemove.add(b);
			}
		}
		bullets.removeAll(toRemove, false);
		toRemove.clear();
	}
	
	public void truncate(Vector2 v, int i)
	{
		if (v.y < -i)
			v.y = -i;
		if (v.y > i)
			v.y = i;
		if (v.x < -i)
			v.x = -i;
		if (v.x > i)
			v.x = i;
	}
	
	public void draw(ShapeRenderer sr)
	{
//		sr.setColor(Color.BLUE);
//		sr.rect(pos.x, pos.y, 50, 50);
//		
		sr.setColor(Color.YELLOW);
		sr.rectLine((this.pos.x + 25), (this.pos.y + 25), (this.pos.x + 25) + (direction.x * 40),
				(this.pos.y + 25) + (direction.y * 40), 2);
		for (Bullet b : bullets)
		{
			b.draw(sr);
		}
	}
	
	public void draw(SpriteBatch sb)
	{
		sb.draw(t, super.pos.x, super.pos.y, 50, 50);
	}
	
	public void setDirection(int x, int y)
	{
		Vector2 mouse = new Vector2(x, y);
		Vector2 player = new Vector2(this.pos.x + 25, this.pos.y + 25);
		direction = (mouse.sub(player).nor());
	}
	
	public void shoot()
	{
		if (bullets.size < MAX_BULLETS)
		{
			bullets.add(new Bullet(this.pos.cpy().add(25, 25), this.direction.cpy().scl(20, 20)));
		}
	}
	
	public Vector2 getPos()
	{
		return pos;
	}
	
	@Override
	public Vector2 getLinearVelocity()
	{
		return vel;
	}
	
	@Override
	public float getAngularVelocity()
	{
		return angularVelocity;
	}
	
	@Override
	public float getBoundingRadius()
	{
		return boundingRadius;
	}
	
	@Override
	public boolean isTagged()
	{
		return false;
	}
	
	@Override
	public void setTagged(boolean tagged)
	{
		this.tagged = tagged;
	}
	
	@Override
	public Vector2 getPosition()
	{
		return pos;
	}
	
	@Override
	public float getOrientation()
	{
		return pos.angle();
	}
	
	@Override
	public void setOrientation(float orientation)
	{
		this.orientation = orientation;
	}
	
	@Override
	public float vectorToAngle(Vector2 vector)
	{
		return (float) Math.atan2(-vector.x, vector.y);
	}
	
	@Override
	public Vector2 angleToVector(Vector2 outVector, float angle)
	{
		outVector.x = -(float) Math.sin(angle);
		outVector.y = (float) Math.cos(angle);
		return outVector;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Location newLocation()
	{
		Vector2 copy = pos.cpy();
		return new Player(copy.add((int) (Math.random() * 50), (int) (Math.random() * 50)), new Vector2(),false);
	}
	
	@Override
	public float getZeroLinearSpeedThreshold()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public void setZeroLinearSpeedThreshold(float value)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public float getMaxLinearSpeed()
	{
		return maxLinearSpeed;
	}
	
	@Override
	public void setMaxLinearSpeed(float maxLinearSpeed)
	{
		this.maxLinearSpeed = maxLinearSpeed;
	}
	
	@Override
	public float getMaxLinearAcceleration()
	{
		return this.maxLinearAcceleration;
	}
	
	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration)
	{
		this.maxLinearAcceleration = maxLinearAcceleration;
	}
	
	@Override
	public float getMaxAngularSpeed()
	{
		return this.maxAngularSpeed;
	}
	
	@Override
	public void setMaxAngularSpeed(float maxAngularSpeed)
	{
		this.maxAngularSpeed = maxAngularSpeed;
	}
	
	@Override
	public float getMaxAngularAcceleration()
	{
		return this.maxAngularAcceleration;
	}
	
	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration)
	{
		this.maxAngularAcceleration = maxAngularAcceleration;
	}
	
	public static float calculateOrientationFromLinearVelocity(Steerable<Vector2> character)
	{
		if (character.getLinearVelocity().isZero(MathUtils.FLOAT_ROUNDING_ERROR))
		{
			return character.getOrientation();
		}
		return character.vectorToAngle(character.getLinearVelocity());
	}
	
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(pos.x, pos.y, 50, 50);
	}
}
