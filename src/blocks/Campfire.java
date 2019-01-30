package blocks;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.Light;
import map.Map;
import map.MapInteraction;
import models.TexturedModel;
import renderEngine.Renderer;
import textures.ModelTexture;

public class Campfire extends Block{
	
	private TexturedModel fire;
	private Entity enFire;
	private Entity enCampfire;

	public Campfire(Vector2f tile) {
		super(tile);
		super.setTexModel(new TexturedModel(super.getRawModel(), new ModelTexture(Block.loader.loadTexture("sprites/CampfireWhole"))));
		super.setId("Campfire");
		
	}

	@Override
	public void placeBlock(Vector2f tile) {
		MapInteraction.addBlock(tile, new Entity(super.getTexModel(), Map.getMapTiles()[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)].getPosition(), 0,0,270, new Vector3f(1,1,1)));
		MapInteraction.tilesOnMap[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)] = super.getId();
		Renderer.lights.add(new Light(Map.getMapTiles()[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)].getPosition(), new Vector3f(1, 0.5f, 0.0f), 7));
		Renderer.lights.get(Renderer.lights.size() - 1).setPosition(new Vector3f(Map.getMapTiles()[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)].getPosition().x, Map.getMapTiles()[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)].getPosition().y, -1));
		//Renderer.processEntity(this.enCampfire, Renderer.map);
		
	}

}
