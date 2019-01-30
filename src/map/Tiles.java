package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import blocks.Block;
import blocks.WoodenWall;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import textures.ModelTexture;

public class Tiles {
	private static Loader loader = new Loader();
	private static RawModel rawModel = OBJLoader.loadObjModel("plane2", loader);
	private static java.util.Map<String, TexturedModel> woodenWallsTexture = new HashMap<String, TexturedModel>();
	public static java.util.Map<String, List<TexturedModel>> textures = new HashMap<String, List<TexturedModel>>();
	public static java.util.Map<String, HashMap> blocks = new HashMap<String, HashMap>();
	public static java.util.Map<Vector2f, WoodenWall> woodenWall = new HashMap<Vector2f, WoodenWall>();
	
	public Tiles(){
		//blocks.put("Wooden Walls", new HashMap<Vector2f, WoodenWall>());
		loadTextures();
		loadWoodenWallsTexture();
	}
	
	public static Entity woodenFlooring(Vector3f position, Vector2f tile){
		TexturedModel texModel = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wooden Flooring")));
		Entity entity = new Entity(texModel, position, 0,0,270, new Vector3f(1,1,1));
		MapInteraction.tilesOnMap[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)] = "Wooden Flooring";
		return entity;
	}
	
	public static Entity woodenWalls(Vector3f position, Vector2f tile){
		TexturedModel texModel = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood STL")));
		//Entity entity = new Entity(texModel, position, 0,0,270, new Vector3f(1,1,1));
		//MapInteraction.tilesOnMap[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)] = "Wooden Walls";
		System.out.println("Marked tile at" + tile + " as Wooden Walls");
		
		return checkSurroundingsWood(tile);
	}
	public static Entity checkSurroundingsWood(Vector2f tile){
		Vector2f[] nearSurrounding = new Vector2f[8];
		int tileInArray = (int) ((tile.y - 1) * Math.sqrt(Map.getMapTiles().length) + (tile.x - 1));
		nearSurrounding[0] = new Vector2f((tile.x - 1),(tile.y - 1));
		nearSurrounding[1] = new Vector2f((tile.x), (tile.y - 1));
		nearSurrounding[2] = new Vector2f((tile.x + 1),(tile.y - 1));
		nearSurrounding[3] = new Vector2f((tile.x + 1), (tile.y));
		nearSurrounding[4] = new Vector2f((tile.x - 1), (tile.y + 1));
		nearSurrounding[5] = new Vector2f((tile.x), (tile.y + 1));
		nearSurrounding[6] = new Vector2f((tile.x + 1), (tile.y + 1));
		nearSurrounding[7] = new Vector2f((tile.x - 1), (tile.y));
		MapInteraction.addBlock(tile, woodSurroundingTiles(tile));
		
		for(int i = 0; i < nearSurrounding.length; i++){
			if(MapInteraction.tilesOnMap[(int) ((nearSurrounding[i].y * Math.sqrt(Map.getMapTiles().length)) + (nearSurrounding[i].x))] == "Wooden Walls"){
				MapInteraction.addBlock(nearSurrounding[i], woodSurroundingTiles(nearSurrounding[i]));
			}
		}
		return woodSurroundingTiles(tile);
		
	}
	
	public static Entity woodSurroundingTiles(Vector2f tile){
		Vector3f tilePosition = Map.getMapTiles()[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)].getPosition();
		String[] nearSurrounding = new String[8];
		nearSurrounding[0] = MapInteraction.tilesOnMap[(int) ((tile.y - 1) * Math.sqrt(Map.getMapTiles().length) + (tile.x - 1))];
		nearSurrounding[1] = MapInteraction.tilesOnMap[(int) ((tile.y - 1) * Math.sqrt(Map.getMapTiles().length) + (tile.x))];
		nearSurrounding[2] = MapInteraction.tilesOnMap[(int) ((tile.y - 1) * Math.sqrt(Map.getMapTiles().length) + (tile.x + 1))];
		nearSurrounding[3] = MapInteraction.tilesOnMap[(int) ((tile.y) * Math.sqrt(Map.getMapTiles().length) + (tile.x + 1))];
		nearSurrounding[4] = MapInteraction.tilesOnMap[(int) ((tile.y + 1) * Math.sqrt(Map.getMapTiles().length) + (tile.x - 1))];
		nearSurrounding[5] = MapInteraction.tilesOnMap[(int) ((tile.y + 1) * Math.sqrt(Map.getMapTiles().length) + (tile.x))];
		nearSurrounding[6] = MapInteraction.tilesOnMap[(int) ((tile.y + 1) * Math.sqrt(Map.getMapTiles().length) + (tile.x + 1))];
		nearSurrounding[7] = MapInteraction.tilesOnMap[(int) ((tile.y) * Math.sqrt(Map.getMapTiles().length) + (tile.x - 1))];
		MapInteraction.tilesOnMap[(int) (tile.y * Math.sqrt(Map.getMapTiles().length) + tile.x)] = "Wooden Walls";
		Entity en = new Entity(woodenWallsTexture.get("STL"), tilePosition, 0, 0, 270, new Vector3f(1,1,1));
		
		
		if(nearSurrounding[1] == "Wooden Walls" && nearSurrounding[3] == "Wooden Walls" && nearSurrounding[5] == "Wooden Walls" && nearSurrounding[7] == "Wooden Walls"){
			// ÅT alla fyra håll
			
			en.setModel(woodenWallsTexture.get("LTRL"));
			return en;
		}
		else if(nearSurrounding[1] == "Wooden Walls" && nearSurrounding[3] == "Wooden Walls" && nearSurrounding[5] != "Wooden Walls" && nearSurrounding[7] != "Wooden Walls"){

			en.setModel(woodenWallsTexture.get("TR"));
			return en;
		}
		else if(nearSurrounding[1] != "Wooden Walls" && nearSurrounding[3] == "Wooden Walls" && nearSurrounding[5] == "Wooden Walls" && nearSurrounding[7] != "Wooden Walls"){

			en.setModel(woodenWallsTexture.get("RL"));
			return en;
		}
		else if(nearSurrounding[1] == "Wooden Walls" && nearSurrounding[3] != "Wooden Walls" && nearSurrounding[5] != "Wooden Walls" && nearSurrounding[7] == "Wooden Walls"){

			en.setModel(woodenWallsTexture.get("TL"));
			return en;
		}
		else if(nearSurrounding[1] != "Wooden Walls" && nearSurrounding[3] != "Wooden Walls" && nearSurrounding[5] == "Wooden Walls" && nearSurrounding[7] == "Wooden Walls"){

			en.setModel(woodenWallsTexture.get("LL"));
			return en;
		}
		else if(nearSurrounding[1] != "Wooden Walls" && nearSurrounding[3] == "Wooden Walls" && nearSurrounding[5] == "Wooden Walls" && nearSurrounding[7] == "Wooden Walls"){
			// Inte uppåt
			en.setModel(woodenWallsTexture.get("LLR"));
			return en;
		}
		else if(nearSurrounding[1] == "Wooden Walls" && nearSurrounding[3] != "Wooden Walls" && nearSurrounding[5] == "Wooden Walls" && nearSurrounding[7] == "Wooden Walls"){
			// Inte Höger
			en.setModel(woodenWallsTexture.get("LTL"));
			return en;
		}
		else if(nearSurrounding[1] == "Wooden Walls" && nearSurrounding[3] == "Wooden Walls" && nearSurrounding[5] != "Wooden Walls" && nearSurrounding[7] == "Wooden Walls"){
			// Inte ner
			en.setModel(woodenWallsTexture.get("LTR"));
			return en;
		}
		else if(nearSurrounding[1] == "Wooden Walls" && nearSurrounding[3] == "Wooden Walls" && nearSurrounding[5] == "Wooden Walls" && nearSurrounding[7] != "Wooden Walls"){
			// Inte vänster
			en.setModel(woodenWallsTexture.get("TRL"));
			return en;
		}
		
		
		
		else if((nearSurrounding[1] != "Wooden Walls" && nearSurrounding[5] != "Wooden Walls") && (nearSurrounding[3] == "Wooden Walls" &&  nearSurrounding[7] == "Wooden Walls")){

			en.setModel(woodenWallsTexture.get("SLR"));
			return en;
		}
		else if((nearSurrounding[1] == "Wooden Walls" &&  nearSurrounding[5] == "Wooden Walls") && (nearSurrounding[3] != "Wooden Walls" && nearSurrounding[7] != "Wooden Walls")){

			en.setModel(woodenWallsTexture.get("STL"));
			return en;
		}
		System.out.println("Kommer hit");
		return en; //new Entity(woodenWallsTexture.get("STL"), tilePosition, 0, 0, 270, new Vector3f(1,1,1));
	}
	
	private void loadWoodenWallsTexture(){
		woodenWallsTexture.put("LTRL", new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood TRLL"))));
		woodenWallsTexture.put("TRL", new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood TRL"))));
		woodenWallsTexture.put("LLR", new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood LLR"))));
		woodenWallsTexture.put("LTL", new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood LTL"))));
		woodenWallsTexture.put("LTR", new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood LTR"))));
		
		woodenWallsTexture.put("RL", new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood RL"))));
		woodenWallsTexture.put("LL", new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood LL"))));
		woodenWallsTexture.put("TL", new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood TL"))));
		woodenWallsTexture.put("TR", new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood TR"))));
		
		woodenWallsTexture.put("SLR", new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood SLR"))));
		woodenWallsTexture.put("STL", new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood STL"))));
		
	}
	
	private void loadTextures(){
		textures.put("Wooden Walls", new ArrayList<TexturedModel>());
		List<TexturedModel> woodenWalls = textures.get("Wooden Walls");
		woodenWalls.add(new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood TRLL"))));
		woodenWalls.add(new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood TRL"))));
		woodenWalls.add(new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood LLR"))));
		woodenWalls.add(new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood LTL"))));
		woodenWalls.add(new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood LTR"))));
		woodenWalls.add(new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood RL"))));
		woodenWalls.add(new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood LL"))));
		woodenWalls.add(new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood TL"))));
		woodenWalls.add(new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood TR"))));
		woodenWalls.add(new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood SLR"))));
		woodenWalls.add(new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("sprites/Wood STL"))));
		
	}
}
