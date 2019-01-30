package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch;
	private float yaw;
	private float roll;
	private float scale = 1;
	private float speed = 0.2f;
	private float inertia = speed;
	private float currrentSpeed;
	
	public void move(){
		currrentSpeed = speed - inertia;
		if((Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_D)) && inertia >= 0.0){
			inertia -= 0.01f;
		}
		if((Keyboard.isKeyDown(Keyboard.KEY_D )&& (Keyboard.isKeyDown(Keyboard.KEY_S)|| Keyboard.isKeyDown(Keyboard.KEY_W))) ||(Keyboard.isKeyDown(Keyboard.KEY_A )&& (Keyboard.isKeyDown(Keyboard.KEY_S)|| Keyboard.isKeyDown(Keyboard.KEY_W)))) speed = (float) Math.sqrt(0.2 * 0.2 * 0.5);
		else speed = 0.2f;
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) position.y += currrentSpeed;
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) position.y -= currrentSpeed;
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) position.x -= currrentSpeed;
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) position.x += currrentSpeed;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) position.z += 2.0f;
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && position.z > 20) position.z -= 2.0f;
		//if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) System.exit(0);
		if(!(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_D)) && inertia <= speed){
			inertia += 0.01f;
		}
		currrentSpeed = speed - inertia;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}
	public float getScale(){
		return scale;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getCurrrentSpeed() {
		return currrentSpeed;
	}
	
}
