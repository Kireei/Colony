package blocks;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import map.Map;
import map.MapInteraction;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;

public abstract class Block {
	
	private RawModel rawModel;
	private TexturedModel texModel;
	private Entity entity;
	private Vector2f tile;
	private Vector3f position;
	private String id;
	private java.util.Map<String, TexturedModel> textureVariants;
	public static Loader loader = new Loader();
	
	public Block(Vector2f tile){
		this.rawModel = OBJLoader.loadObjModel("plane2", new Loader());
		this.tile = tile;
		this.position = Map.getMapTiles()[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)].getPosition();
		this.textureVariants = new HashMap<String, TexturedModel>();
		
	}
	public abstract void placeBlock(Vector2f tile);
	public void addBlock(){
		this.entity = new Entity(texModel, this.position, 0,0,270, new Vector3f(1,1,1));
		
		this.textureVariants.put("Main", texModel);
		MapInteraction.tilesOnMap[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)] = id;
		Renderer.processEntity(this.entity, Renderer.map);
	}
	
	
	public Vector2f tile(){
		return this.tile;
	}
	public Vector3f getPosition(){
		return this.position;
	}
	public TexturedModel getModel(){
		return this.texModel;
	}
	public Entity getEntity(){
		return this.entity;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public java.util.Map<String, TexturedModel> getTextureVariants() {
		return textureVariants;
	}
	public void setTextureVariants(java.util.Map<String, TexturedModel> textureVariants) {
		this.textureVariants = textureVariants;
	}
	public TexturedModel getTexModel() {
		return texModel;
	}
	public void setTexModel(TexturedModel texModel) {
		this.texModel = texModel;
	}
	public RawModel getRawModel() {
		return rawModel;
	}
	public void setRawModel(RawModel rawModel) {
		this.rawModel = rawModel;
	}
	
}
