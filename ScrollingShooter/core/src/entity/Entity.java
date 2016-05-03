/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Akeem Davis
 */
public abstract class Entity
{
	protected Texture texture;
	protected Vector2 pos, direction;
	protected Boolean onScreen;
	
	public Entity(/* Texture texture */ Vector2 pos, Vector2 direction)
	{
		// this.texture = texture;
		this.pos = pos;
		this.direction = direction;
		onScreen = false;
	}
	
	public abstract void update();
	
	public void render(SpriteBatch sb)
	{
		sb.draw(texture, pos.x, pos.y);
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(pos.x, pos.y, 85, 210);// y 212
	}
	
	public void setDirection(float x, float y)
	{
		direction.set(x, y);
		direction.scl(Gdx.graphics.getDeltaTime());
	}
}
