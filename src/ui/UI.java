package ui;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import shaders.StaticShader;

public abstract class UI {

	public UI(Vector2f position, Vector3f scale, StaticShader shader){}
	
	abstract void toggle();
}
