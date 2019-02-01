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

	private Vector3f position;
	private Vector2f scale;
	private String id;
	
	private boolean clicked = false;
	
	public Icon(Vector3f iconPosition, Vector2f scale, String image, String id){
		this.position = iconPosition;
		this.scale = scale;
		this.id = id;
		this.texModel = new TexturedModel(this.rawModel, new ModelTexture(UIHandler.loader.loadTexture(image)));
		this.en = new Entity(this.texModel, new Vector3f(iconPosition.x, iconPosition.y, 1), 0,0,0, new Vector3f(scale.x, scale.y, 1));
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
		} else if(Mouse.isButtonDown(0) && mX >= this.position.x - (scaleX * 0.5626) && mX <= this.position.x + (scaleX * 0.5626) && mY >= this.position.y - scaleY && mY <= this.position.y + scaleY){
			this.color = new Vector4f(1,1,1,0.35f);
		} else{
			clicked = false;
			this.color = new Vector4f(1,1,1,0.0f);
		}
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public Entity getEn() {
		return en;
	}

	public void setEn(Entity en) {
		this.en = en;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
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
