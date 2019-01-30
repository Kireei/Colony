package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import textures.ModelTexture;

public class Character {

	private TexturedModel still;
	private TexturedModel walking1;
	private TexturedModel walking2;
	public Entity entity;
	private Entity eStill;
	private Entity eWalking1;
	private Entity eWalking2;
	private RawModel rawModel;
	private Camera camera;
	private Entity[] walkingStages = new Entity[4];
	public Character(Camera camera){
		Loader loader = new Loader();
		
		this.camera = camera;
		
		this.rawModel = OBJLoader.loadObjModel("plane2", loader);
		this.still = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Guy")));
		this.walking1 = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Guy Walking 1")));
		this.walking2 = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Guy Walking 2")));
		this.eStill = new Entity(this.still, new Vector3f(-camera.getPosition().x, -camera.getPosition().y, -5), 0, 0, 0, new Vector3f(1,2,1));
		this.eWalking1 = new Entity(this.walking1, new Vector3f(-camera.getPosition().x, -camera.getPosition().y, -5), 0, 0, 0, new Vector3f(1,2,1));
		this.eWalking2 = new Entity(this.walking2, new Vector3f(-camera.getPosition().x , -camera.getPosition().y, -5), 0, 0, 0, new Vector3f(1,2,1));
		this.entity = this.eStill;
		
		this.walkingStages[0] = this.eStill;
		this.walkingStages[1] = this.eWalking1;
		this.walkingStages[2] = this.eStill;
		this.walkingStages[3] = this.eWalking2;
		this.entity.setRotZ(270);
	}
	
	public void prepareCharacter(){
		Renderer.processEntity(this.eStill, Renderer.entities);
		Renderer.processEntity(this.eWalking1, Renderer.entities);
		Renderer.processEntity(this.eWalking2, Renderer.entities);

	}
	
	private int walkingStage = 0;
	public void move(){
		if(walkingStage == 64) walkingStage = 0;
		this.entity.setPosition(new Vector3f(camera.getPosition().x, camera.getPosition().y, -5));
		this.eStill.setPosition(new Vector3f(camera.getPosition().x, camera.getPosition().y, -5));
		this.eWalking1.setPosition(new Vector3f(camera.getPosition().x, camera.getPosition().y, -5));
		this.eWalking2.setPosition(new Vector3f(camera.getPosition().x, camera.getPosition().y, -5));
		float speed = camera.getCurrrentSpeed();
		
		
		if(speed  > 0){ 
			this.entity = this.walkingStages[walkingStage / 16];
		}else {
			this.entity = this.eStill;
		}
		this.entity.setRotZ(270);
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) this.entity.setRotZ(90);
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) this.entity.setRotZ(270);
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) this.entity.setRotZ(180);
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) this.entity.setRotZ(0);
		if(Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_A)) this.entity.setRotZ(135);
		if(Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_D)) this.entity.setRotZ(45);
		if(Keyboard.isKeyDown(Keyboard.KEY_D) && Keyboard.isKeyDown(Keyboard.KEY_S)) this.entity.setRotZ(315);
		if(Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_A)) this.entity.setRotZ(225);
		walkingStage++;

	}
	
}
