package ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import fontRendering.TextMaster;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class UIElement {
	private List<Button> buttons = new ArrayList<Button>();
	private List<Icon> icons = new ArrayList<Icon>();
	private Vector3f position;
	private Vector2f scale;
	private RawModel rawModel = UIHandler.rawModel;
	private Loader loader = UIHandler.loader;
	private TexturedModel texModel;
	private Entity en;
	
	private boolean toggled = false;
	
	public UIElement(Vector3f position, Vector2f scale, String path){
		this.position = position;
		this.scale = scale;
		this.texModel = new TexturedModel(this.rawModel, new ModelTexture(this.loader.loadTexture("GUI/" + path)));
		this.en = new Entity(texModel, new Vector3f(position.x, position.y, position.z), 0,0,10, new Vector3f(scale.x, scale.y, 1));
	}
	
	public void addButton(Vector2f position, float scale, String title, Vector2f textPosition){
		Vector3f buttonPos = new Vector3f();
		Vector3f.add(this.position, new Vector3f(position.x, position.y, 1), buttonPos);
		Vector3f textPos = new Vector3f();
		Vector3f.add(buttonPos, new Vector3f(textPosition.x, textPosition.y, 1), textPos);
		
		buttons.add(new Button(this, buttonPos, scale, title, buttonPos));
	}
	
	public void addIcon(Vector2f position, Vector2f scale, String image, String id){
		Vector3f iconPosition = new Vector3f();
		Vector3f.add(this.position, new Vector3f(position.x, position.y, 1), iconPosition);
		
		icons.add(new Icon(iconPosition, scale, image, id));
		
	}
	
	public void addText(Vector2f position, float scale, String text) {
		
		
	}
	
	

	public TexturedModel getTexModel() {
		return texModel;
	}

	public void setTexModel(TexturedModel texModel) {
		this.texModel = texModel;
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

	public Vector2f getScale() {
		return scale;
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		if(toggled){
			for(Button button:buttons){
				TextMaster.loadText(button.getTitle());
				
			}
		}
		if(!toggled){
			for(Button button:buttons){
				TextMaster.removeText(button.getTitle());
				
			}
		}
		this.toggled = toggled;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public List<Icon> getIcons() {
		return icons;
	}

	public void setIcons(List<Icon> icons) {
		this.icons = icons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}
}
