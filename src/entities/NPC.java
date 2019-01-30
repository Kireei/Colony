package entities;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;

public class NPC {
	
	private RawModel rawModel;
	private TexturedModel NPC;
	private Loader loader = new Loader();
	private String path;
	private Vector3f position;
	private Vector3f scale;
	private Vector3f target;
	public Entity entity;
	private float speed;
	public boolean atTarget = false;
	
	public NPC(Vector3f position, Vector3f scale, String path){
		this.path = path;
		this.scale = scale;
		this.position = position;
		this.rawModel = OBJLoader.loadObjModel("plane2", loader);
		this.NPC = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture(this.path)));
		this.entity = new Entity(this.NPC, position, 0, 0, 0, this.scale);
		target = new Vector3f((float) Math.random() * 256, (float) -Math.random() * 256, -5);

		
		
	}
	public void calculateDirection(){
		if(atTarget){
			target = new Vector3f((float) Math.random() * 256, (float) -Math.random() * 256, -5); 
			atTarget = false;
			//System.out.println("New target at: [" + target.x + ", " + target.y + "]" );
			}
		int tolerance = 2;

		float diffX = Math.round(target.x - position.x);
		float diffY = Math.round(target.y - position.y);
		
		
		if(Math.abs(diffX) < tolerance * 2 && Math.abs(diffY) < tolerance * 2){speed = 0.02f;} else {speed = 0.1f;}
		if(position.x <= target.x + tolerance && position.x >= target.x - tolerance && position.y <= target.y + tolerance && position.y >= target.y - tolerance){ atTarget = true; return;}
		if(Math.abs(diffY) <= tolerance / 2 && diffX < tolerance){move(7);return;}
		if(Math.abs(diffY) <= tolerance / 2 && diffX > tolerance){move(3);;return;}
		if(Math.abs(diffX) <= tolerance / 2 && diffY < tolerance){move(5);return;}
		if(Math.abs(diffX) <= tolerance / 2 && diffY > tolerance){move(1);return;}
		
		
		if(diffX <= tolerance / 2 && diffY <= tolerance / 2){move(6);return;}
		if(diffX >= tolerance / 2 && diffY <= tolerance / 2){move(4);return;}
		if(diffX >= tolerance / 2 && diffY >= tolerance / 2){move(2);return;}
		if(diffX <= tolerance / 2 && diffY >= tolerance / 2){move(0);return;}

		
		
		
		
		
	}
	
	public void move(int direction){

		
		switch(direction){
		case 0:
			//Upp och vänster
			this.entity.setRotZ(135);
			this.entity.increasePosition(-speed, speed, 0);
			break;
		case 1:
			//Upp
			this.entity.setRotZ(90);
			this.entity.increasePosition(0, speed, 0);
			break;
		case 2:
			//Upp och höger
			this.entity.setRotZ(45);
			this.entity.increasePosition(speed, speed, 0);
			break;
		case 3:
			//Höger
			this.entity.setRotZ(0);
			this.entity.increasePosition(speed, 0, 0);
			break;
		case 4:
			//Ner och höger
			this.entity.setRotZ(315);
			this.entity.increasePosition(speed, -speed, 0);
			break;
		case 5:
			//Ner
			this.entity.setRotZ(270);
			this.entity.increasePosition(0, -speed, 0);
			break;
		case 6:
			//Ner och vänster
			this.entity.setRotZ(225);
			this.entity.increasePosition(-speed, -speed, 0);
			break;
		case 7:
			//Vänster
			this.entity.setRotZ(180);
			this.entity.increasePosition(-speed, 0, 0);
			break;
		}
		
	}
}
