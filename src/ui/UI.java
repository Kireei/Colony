package ui;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import shaders.StaticShader;

public abstract class UI {
	private Vector2f position;
	private Vector3f scale;
	
	public UI(Vector2f position, Vector3f scale, StaticShader shader){
		this.position = position;
		this.scale = scale;
	}
	
	abstract void toggle();
}
