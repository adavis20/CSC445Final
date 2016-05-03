package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Player extends Entity implements Steerable<Vector2>
{
    Vector2 position;
    Vector2 linearVelocity;
    float orientation;
    float angularVelocity;
    boolean tagged;
    float boundingRadius;
    float maxLinearSpeed;
    float maxLinearAcceleration;
    float maxAngularSpeed;
    float maxAngularAcceleration;
    int health;
    double DEGREES_TO_RADIANS = (double)(Math.PI/180);
    boolean independentFacing;
	public Player(Vector2 pos, Vector2 direction)
	{
		super(pos,direction);
                linearVelocity = direction;
                health = 100;
	}
	
        
	public void draw(ShapeRenderer sr)
	{
		sr.setColor(Color.BLUE);
		sr.rect(pos.x, pos.y, 50, 50);
	}
        
    @Override
    public void update() {
        	if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			pos.x -= 10;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			pos.x += 10;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			pos.y -= 10;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			pos.y += 10;
		}
    }
    public Vector2 getPos(){
        return pos;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }
    
    @Override
    public float getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return false;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }

    @Override
    public float getOrientation() {
        return pos.angle();
    }

    @Override
    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }

    @Override
    public Location newLocation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
       return this.maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
       this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return this.maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return this.maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    public static float calculateOrientationFromLinearVelocity(Steerable<Vector2> character){
    if(character.getLinearVelocity().isZero(MathUtils.FLOAT_ROUNDING_ERROR)){
            return character.getOrientation();
    }
            return character.vectorToAngle(character.getLinearVelocity());
    }
}
