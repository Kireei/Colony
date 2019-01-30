package ui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;

public class Icon {
	
	private RawModel rawModel = UIHandler.rawModel;
	private TexturedModel texModel;
	private Entity en;
	private Vector4f color;

	private Vector2f position;
	private Vector2f scale;
	private String image;
	private String id;
	
	private boolean clicked = false;
	
	public Icon(Vector2f position, Vector2f scale, String image, String id){
		this.position = position;
		this.scale = scale;
		this.image = image;
		this.id = id;
		this.texModel = new TexturedModel(this.rawModel, new ModelTexture(UIHandler.loader.loadTexture(image)));
		this.en = new Entity(this.texModel, new Vector3f(position.x, position.y, 1), 0,0,0, new Vector3f(scale.x, scale.y, 1));
		this.color = new Vector4f(1,1,1,0.0f);
	}
	
	public void checkClick(){
		
		double mX = (Mouse.getX() / (double)Display.getWidth()) * 2 -1;
		double mY = -(((-Mouse.getY() + Display.getHeight()) / (double)Display.getHeight()) * 2 -1);
		float scaleX = this.scale.x;
		float scaleY = this.scale.y;
		if(Mouse.isButtonDown(0) && mX >= this.position.x - (scaleX * 0.5626) && mX <= this.position.x + (scaleX * 0.5626) && mY >= this.position.y - scaleY && mY <= this.position.y + scaleY){
			this.color = new Vector4f(1,1,1,0.5f);
			clicked = true;
		} else if(mX >= this.position.x - (scaleX * 0.5626) && mX <= this.position.x + (scaleX * 0.5626) && mY >= this.position.y - scaleY && mY <= this.position.y + scaleY){
			this.color = new Vector4f(1,1,1,0.35f);
		} else{
			clicked = false;
			this.color = new Vector4f(1,1,1,0.0f);
		}
		
	}

	public Entity getEn() {
		return en;
	}

	public void setEn(Entity en) {
		this.en = en;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getScale() {
		return scale;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	public Vector4f getColor() {
		return color;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}
}
