package map;

import java.util.HashMap;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import blocks.Block;
import entities.Entity;
import models.TexturedModel;
import renderEngine.Renderer;

public class MapInteraction {
	public static java.util.Map<TexturedModel, List<Entity>> interactedTiles = new HashMap<TexturedModel, List<Entity>>();
	
	public static String[] tilesOnMap = new String[Map.getMapTiles().length];
	
	public static void addBlock(Vector2f tile, Entity newTile){
		newTile.setPosition(Map.getMapTiles()[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)].getPosition());
		//System.out.println(tilesOnMap[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)]);
		Renderer.processEntity(newTile, interactedTiles);
	}
	public static void changeBlock(Vector2f tile, Entity newTile){
		
	}
}
