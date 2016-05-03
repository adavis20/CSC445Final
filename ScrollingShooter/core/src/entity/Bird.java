/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Akeem Davis
 */
public class Bird extends Entity implements Steerable<Vector2>
{
	SteeringAcceleration<Vector2> SA = new SteeringAcceleration<Vector2>(new Vector2());
	Vector2 position;
	Vector2 linearVelocity;
	Player p;
	Player startSpot;
	int roationSpeed = 1;
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
	Seek<Vector2> SB;
	
	public Bird(Vector2 pos, Vector2 direction, Player p)
	{
		super(pos, direction);
		maxLinearSpeed = 0.3f;
		maxLinearAcceleration = 1;
		maxAngularSpeed = 1;
		maxAngularAcceleration = 1;
		linearVelocity = direction;
		position = pos;
		this.p = p;
		SB = new Seek<Vector2>(this, p);
		independentFacing = false;
	}
	
	@Override
	public void update()
	{
		SB.calculateSteering(SA);
		applySteering(SA, 10);
	}
	
	public void draw(ShapeRenderer sr)
	{
		sr.setColor(Color.GOLD);
		sr.rect(pos.x, pos.y, 20, 20);
	}
	
	private void applySteering(SteeringAcceleration<Vector2> steering, float time)
	{
		// Update position and linear velocity. Velocity is trimmed to maximum
		// speed
		pos.mulAdd(linearVelocity, time);
		this.linearVelocity.mulAdd(steering.linear, time).limit(this.getMaxLinearSpeed());
		// Update orientation and angular velocity
		if (independentFacing)
		{
			this.orientation += angularVelocity * time;
			this.angularVelocity += steering.angular * time;
		}
		else
		{
			// For non-independent facing we have to align orientation to linear
			// velocity
			float newOrientation = calculateOrientationFromLinearVelocity(this);
			if (newOrientation != this.orientation)
			{
				this.angularVelocity = (newOrientation - this.orientation) * time;
				this.orientation = newOrientation;
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public Vector2 getLinearVelocity()
	{
		return linearVelocity;
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
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}
	
	@Override
	public float getZeroLinearSpeedThreshold()
	{
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}
	
	@Override
	public void setZeroLinearSpeedThreshold(float value)
	{
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
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
}
