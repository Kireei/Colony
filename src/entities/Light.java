package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	private Vector3f position;
	private Vector3f color;
	private float power;
	public Light(Vector3f position, Vector3f color, float power){
		this.position = position;
		this.color = color;
		this.power = power;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public Vector3f getColor() {
		return color;
	}
	public void setColor(Vector3f color) {
		this.color = color;
	}
	public float getPower() {
		return power;
	}
	public void setPower(float power) {
		this.power = power;
	}
}
