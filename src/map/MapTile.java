package map;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;


public class MapTile {
	public Entity entity;
	public Biomes biome;
	private ModelTexture texture; 
	TexturedModel staticModel;

	
	
	public MapTile(Biomes biome){
		Loader loader = new Loader();
		
		
		RawModel model = OBJLoader.loadObjModel("plane2", loader);
		switch(biome){
		case OCEAN:
			this.texture = new ModelTexture(loader.loadTexture("sprites/Water"));
			break;
		case GRASS:
			this.texture = new ModelTexture(loader.loadTexture("sprites/Grass"));
			break;
		case SAND:
			this.texture = new ModelTexture(loader.loadTexture("sprites/Sand"));
			break;
		default:
			this.texture = new ModelTexture(loader.loadTexture("cube"));
			break;
		}
		
		this.staticModel = new TexturedModel(model, texture);
		this.entity = new Entity(staticModel, new Vector3f(1,0,-10),0,0,0,new Vector3f(1,1,1));
		this.biome = biome;
	}
	
	public Entity createTile(Vector3f position){
		Entity entity = new Entity(this.staticModel, position, 0, 0, 0, new Vector3f(1,1,1));
		entity.setRotX(this.entity.getRotX());
		entity.setRotY(this.entity.getRotY());
		entity.setRotZ(this.entity.getRotZ());
		return entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Biomes getBiome() {
		return biome;
	}

	public void setBiome(Biomes biome) {
		this.biome = biome;
	}

	public ModelTexture getTexture() {
		return texture;
	}

	public void setTexture(ModelTexture texture) {
		this.texture = texture;
	}
	
	

}
