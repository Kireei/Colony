package ui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import entities.Entity;
import fontMeshCreator.GUIText;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class Button {
	private static RawModel rawModel = UIHandler.rawModel;
	private static TexturedModel texModel = new TexturedModel(rawModel, new ModelTexture(new Loader().loadTexture("GUI/Button")));
	private static TexturedModel clickedTexModel = new TexturedModel(rawModel, new ModelTexture(new Loader().loadTexture("GUI/Highlighted Button")));
	private TexturedModel activeTexModel;
	private Entity en;
	private GUIText title;
	private Vector3f position;
	private Vector2f scale;
	private boolean clicked = false;
	
	public Button(UIElement master, Vector3f buttonPos, float scale, String title, Vector3f buttonPos2){
		//this.rawModel = OBJLoader.loadObjModel("plane2",new Loader());
		this.position = buttonPos;
		this.scale = new Vector2f((scale),scale);
		this.activeTexModel = texModel;
		//his.texModel = new TexturedModel(this.rawModel, new ModelTexture(new Loader().loadTexture("sprites/cube")));
		this.en = new Entity(Button.clickedTexModel, new Vector3f(buttonPos.x, buttonPos.y, 1), 0, 0, 0, new Vector3f(this.scale.x, this.scale.y, 1f));
		this.title = new GUIText(title, 4 * scale , UIHandler.font, new Vector2f(buttonPos.x, -buttonPos.y-(this.scale.y/8)), 1f, true, false);
		this.title.setColour(0.8555f,0.7031f,0.1445f);
	}
	
	
	public void checkClick(){
		
		double mX = (Mouse.getX() / (double)Display.getWidth()) * 2 -1;
		double mY = -(((-Mouse.getY() + Display.getHeight()) / (double)Display.getHeight()) * 2 -1);
		float scaleX = this.scale.x;
		float scaleY = this.scale.y * 0.1488f;
		if(Mouse.isButtonDown(0) && mX >= this.position.x - (scaleX * 0.5626) && mX <= this.position.x + (scaleX * 0.5626) && mY >= this.position.y - scaleY && mY <= this.position.y + scaleY){
			this.title.setColour(1, 1, 1);
			this.activeTexModel = clickedTexModel;
			clicked = true;
		} else if(mX >= this.position.x - (scaleX * 0.5626) && mX <= this.position.x + (scaleX * 0.5626) && mY >= this.position.y - scaleY && mY <= this.position.y + scaleY){
			this.title.setColour(0.8555f,0.7031f,0.1445f);
			this.activeTexModel = clickedTexModel;
		} else{
			this.title.setColour(0.8555f,0.7031f,0.1445f);
			this.activeTexModel = texModel;
			clicked = false;
		}
		
		
	}
	

	public Entity getEn() {
		return en;
	}

	public GUIText getTitle() {
		return title;
	}

	public void setTitle(GUIText title) {
		this.title = title;
	}

	public TexturedModel getActiveModel() {
		return activeTexModel;
	}

	public Vector2f getScale() {
		return scale;
	}

	public Vector3f getPosition() {
		return position;
	}


	public boolean isClicked() {
		return clicked;
	}
}
