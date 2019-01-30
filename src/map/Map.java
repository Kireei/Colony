package map;


import java.util.HashMap;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import textures.ModelTexture;

public class Map {
	
	private Image mapImage;
	private java.util.Map<String, TexturedModel> mapTextures = new HashMap<String, TexturedModel>();
	
	private static Entity[] mapTiles;
	private MapTile ocean;
	private MapTile grass;
	private MapTile sand;
	
	public java.util.Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	public java.util.Map<TexturedModel, List<Entity>> test = new HashMap<TexturedModel, List<Entity>>();
	
	private Biomes[] map;

	private Loader loader;
	private RawModel model;
	
	
	public Map(Image image, Loader loader){

		this.mapImage = image;
		this.ocean = new MapTile(Biomes.OCEAN);
		this.grass = new MapTile(Biomes.GRASS);
		this.sand = new MapTile(Biomes.SAND);

		this.loader = loader;
		this.model = OBJLoader.loadObjModel("plane2", this.loader);
		createMap();

	}
	
	public void readMap(Image image){
		mapTiles = new Entity[image.getH() * image.getW()];
		map = new Biomes[image.getH() * image.getW()];
		for(int y = 0; y < image.getH(); y++){
			for(int x = 0; x < image.getW(); x++){
				
				if(image.getP()[x + y * image.getH()] == -16776961){
					mapTiles[x + y * image.getH()] = this.ocean.createTile(new Vector3f(x * 2+1, -y * 2 -1, -5));
					map[x + y * image.getH()] = Biomes.OCEAN;
				} 
				if (image.getP()[x + y * image.getH()] == -256){
					mapTiles[x + y * image.getH()] = this.sand.createTile(new Vector3f(x * 2+1, -y * 2-1, -5));
					map[x + y * image.getH()] = Biomes.SAND;
				} 
				if (image.getP()[x + y * image.getH()] == -16711936){
					mapTiles[x + y * image.getH()] = this.grass.createTile(new Vector3f(x * 2+1, -y * 2-1, -5));
					map[x + y * image.getH()] = Biomes.GRASS;
				}
				
			}
		}
		
	}
	
	public void createMap(){
		System.out.println("Loading map...");
		readMap(this.mapImage);
		System.out.println("Map has been read!");
		loadMapTextures();
		System.out.println("Textures loaded!");
		checkSurroundingTiles();
		System.out.println("Surroundings checked!");
		for(Entity mapTile: mapTiles) Renderer.processEntity(mapTile, Renderer.map);
		System.out.println("Tiles Processed!");
		System.out.println("Map loaded!");
	}

	private void checkSurroundingTiles(){
		
		for(int i = 0; i < map.length; i++){
			mapTiles[i].setScale(new Vector3f(1.01f, 1.01f, 1.01f));
			if(map[i] == Biomes.SAND){
				Biomes[] surrounding = new Biomes[8];
				surrounding[0] = map[i - 1 - (int) Math.sqrt(map.length)];
				surrounding[1] = map[i - (int) Math.sqrt(map.length)];
				surrounding[2] = map[i + 1 - (int) Math.sqrt(map.length)];
				surrounding[3] = map[i + 1];
				surrounding[4] = map[i + 1 + (int) Math.sqrt(map.length)];
				surrounding[5] = map[i + (int) Math.sqrt(map.length)];
				surrounding[6] = map[i - 1 + (int) Math.sqrt(map.length)];
				surrounding[7] = map[i - 1];
				int sandCount = 0;
				for(Biomes surr:surrounding) if(surr == Biomes.SAND || surr ==Biomes.GRASS) sandCount++;
				
				if(sandCount == 8)continue;
				
				if(surrounding[1] == Biomes.OCEAN && ( surrounding[2] == Biomes.OCEAN || surrounding[0] == Biomes.OCEAN))/*Vatten på hela övre sida*/ mapTiles[i].setModel(mapTextures.get("Water_Sand LC"));
				if(surrounding[3] == Biomes.OCEAN && ( surrounding[2] == Biomes.OCEAN || surrounding[4] == Biomes.OCEAN))/*Vatten på hela Högra sida */ mapTiles[i].setModel(mapTextures.get("Water_Sand ML"));
				if(surrounding[5] == Biomes.OCEAN && ( surrounding[6] == Biomes.OCEAN || surrounding[4] == Biomes.OCEAN))/*Vatten på hela botten sida*/ mapTiles[i].setModel(mapTextures.get("Water_Sand TC"));
				if(surrounding[7] == Biomes.OCEAN && ( surrounding[0] == Biomes.OCEAN || surrounding[6] == Biomes.OCEAN))/*Vatten på hela vänster sida*/ mapTiles[i].setModel(mapTextures.get("Water_Sand MR"));
				
				if(surrounding[0] == Biomes.OCEAN && surrounding[7] == Biomes.OCEAN && surrounding[1] == Biomes.OCEAN)/*Sand i nedre högra*/ mapTiles[i].setModel(mapTextures.get("Water_Sand LR"));
				if(surrounding[0] == Biomes.OCEAN && surrounding[7] == Biomes.SAND && surrounding[1] == Biomes.SAND && surrounding[4] == Biomes.SAND)/*Vatten uppe i högra*/ mapTiles[i].setModel(mapTextures.get("Sand_Water TL"));
				if(surrounding[6] == Biomes.OCEAN && surrounding[7] == Biomes.OCEAN && surrounding[5] == Biomes.OCEAN)/*Sand uppe i Högra*/ mapTiles[i].setModel(mapTextures.get("Water_Sand TR"));
				if(surrounding[6] == Biomes.OCEAN && surrounding[7] == Biomes.SAND && surrounding[5] == Biomes.SAND && surrounding[2] == Biomes.SAND)/*Vatten nere i Högra*/ mapTiles[i].setModel(mapTextures.get("Sand_Water LL"));
				if(surrounding[4] == Biomes.OCEAN && surrounding[5] == Biomes.OCEAN && surrounding[3] == Biomes.OCEAN) /*Sand uppe i vänstra*/ mapTiles[i].setModel(mapTextures.get("Water_Sand TL"));
				if(surrounding[4] == Biomes.OCEAN && surrounding[5] == Biomes.SAND && surrounding[3] == Biomes.SAND && surrounding[0] == Biomes.SAND) /*Vatten nere i vänstra*/mapTiles[i].setModel(mapTextures.get("Sand_Water LR"));
				if(surrounding[2] == Biomes.OCEAN && surrounding[1] == Biomes.OCEAN && surrounding[3] == Biomes.OCEAN) /*Sand nere i vänstra*/ mapTiles[i].setModel(mapTextures.get("Water_Sand LL"));
				if(surrounding[2] == Biomes.OCEAN && surrounding[1] == Biomes.SAND && surrounding[3] == Biomes.SAND && surrounding[6] == Biomes.SAND) /*Vatten uppe i vänstra*/ mapTiles[i].setModel(mapTextures.get("Sand_Water TR"));
				
				
			}
			if(map[i] == Biomes.GRASS){
				Biomes[] surrounding = new Biomes[8];
				surrounding[0] = map[i - 1 - (int) Math.sqrt(map.length)];
				surrounding[1] = map[i - (int) Math.sqrt(map.length)];
				surrounding[2] = map[i + 1 - (int) Math.sqrt(map.length)];
				surrounding[3] = map[i + 1];
				surrounding[4] = map[i + 1 + (int) Math.sqrt(map.length)];
				surrounding[5] = map[i + (int) Math.sqrt(map.length)];
				surrounding[6] = map[i - 1 + (int) Math.sqrt(map.length)];
				surrounding[7] = map[i - 1];
				int grassCount = 0;
				for(Biomes surr:surrounding) if(surr ==Biomes.GRASS) grassCount++;
				
				if(grassCount == 8)continue;
				
				if(surrounding[1] == Biomes.SAND && ( surrounding[2] == Biomes.SAND || surrounding[0] == Biomes.SAND))/*Sand på hela övre sida*/ mapTiles[i].setModel(mapTextures.get("Sand_Grass LC"));
				if(surrounding[3] == Biomes.SAND && ( surrounding[2] == Biomes.SAND || surrounding[4] == Biomes.SAND))/*Sand på hela Högra sida */ mapTiles[i].setModel(mapTextures.get("Sand_Grass ML"));
				if(surrounding[5] == Biomes.SAND && ( surrounding[6] == Biomes.SAND || surrounding[4] == Biomes.SAND))/*Sand på hela botten sida*/ mapTiles[i].setModel(mapTextures.get("Sand_Grass TC"));
				if(surrounding[7] == Biomes.SAND && ( surrounding[0] == Biomes.SAND || surrounding[6] == Biomes.SAND))/*Sand på hela vänster sida*/ mapTiles[i].setModel(mapTextures.get("Sand_Grass MR"));
				
				if(surrounding[0] == Biomes.SAND && surrounding[7] == Biomes.SAND && surrounding[1] == Biomes.SAND)/*Gräs i nedre högra*/ mapTiles[i].setModel(mapTextures.get("Sand_Grass LR"));
				if(surrounding[0] == Biomes.SAND && surrounding[7] == Biomes.GRASS && surrounding[1] == Biomes.GRASS && surrounding[4] == Biomes.GRASS)/*Sand uppe i högra*/ mapTiles[i].setModel(mapTextures.get("Grass_Sand TL"));
				if(surrounding[6] == Biomes.SAND && surrounding[7] == Biomes.SAND && surrounding[5] == Biomes.SAND)/*Gräs uppe i Högra*/ mapTiles[i].setModel(mapTextures.get("Sand_Grass TR"));
				if(surrounding[6] == Biomes.SAND && surrounding[7] == Biomes.GRASS && surrounding[5] == Biomes.GRASS && surrounding[2] == Biomes.GRASS)/*Sand nere i Högra*/ mapTiles[i].setModel(mapTextures.get("Grass_Sand LL"));
				if(surrounding[4] == Biomes.SAND && surrounding[5] == Biomes.SAND && surrounding[3] == Biomes.SAND) /*Gräs uppe i vänstra*/ mapTiles[i].setModel(mapTextures.get("Sand_Grass TL"));
				if(surrounding[4] == Biomes.SAND && surrounding[5] == Biomes.GRASS && surrounding[3] == Biomes.GRASS && surrounding[0] == Biomes.GRASS) /*Sand nere i vänstra*/mapTiles[i].setModel(mapTextures.get("Grass_Sand LR"));
				if(surrounding[2] == Biomes.SAND && surrounding[1] == Biomes.SAND && surrounding[3] == Biomes.SAND) /*Gräs nere i vänstra*/ mapTiles[i].setModel(mapTextures.get("Sand_Grass LL"));
				if(surrounding[2] == Biomes.SAND && surrounding[1] == Biomes.GRASS && surrounding[3] == Biomes.GRASS && surrounding[6] == Biomes.GRASS) /*Sand uppe i vänstra*/ mapTiles[i].setModel(mapTextures.get("Grass_Sand TR"));
				
				
			}
			
		}

		
	}
	
	private void loadMapTextures(){
		mapTextures.put("Ocean", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Water"))));
		mapTextures.put("Grass", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Grass"))));
		mapTextures.put("Sand", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand"))));
		
		mapTextures.put("Water_Sand MR", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Water _ Sand MR"))));
		mapTextures.put("Water_Sand TL", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Water _ Sand TL"))));
		mapTextures.put("Water_Sand LR", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Water _ Sand LR"))));
		mapTextures.put("Water_Sand LC", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Water _ Sand LC"))));
		mapTextures.put("Water_Sand ML", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Water _ Sand ML"))));
		mapTextures.put("Water_Sand TR", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Water _ Sand TR"))));
		mapTextures.put("Water_Sand TC", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Water _ Sand TC"))));
		mapTextures.put("Water_Sand LL", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Water _ Sand LL"))));
		
		mapTextures.put("Sand_Water TL", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Water TL"))));
		mapTextures.put("Sand_Water TR", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Water TR"))));
		mapTextures.put("Sand_Water LL", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Water LL"))));
		mapTextures.put("Sand_Water LR", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Water LR"))));
		
		mapTextures.put("Sand_Grass TL", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Grass TL"))));
		mapTextures.put("Sand_Grass TC", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Grass TC"))));
		mapTextures.put("Sand_Grass TR", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Grass TR"))));
		mapTextures.put("Sand_Grass MR", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Grass MR"))));
		mapTextures.put("Sand_Grass LR", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Grass LR"))));
		mapTextures.put("Sand_Grass LC", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Grass LC"))));
		mapTextures.put("Sand_Grass LL", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Grass LL"))));
		mapTextures.put("Sand_Grass ML", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Sand _ Grass ML"))));
		
		mapTextures.put("Grass_Sand TL", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Grass _ Sand TL"))));
		mapTextures.put("Grass_Sand TR", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Grass _ Sand TR"))));
		mapTextures.put("Grass_Sand LR", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Grass _ Sand LR"))));
		mapTextures.put("Grass_Sand LL", new TexturedModel(this.model, new ModelTexture(loader.loadTexture("sprites/Grass _ Sand LL"))));
		
		
	}
	

	public static Entity[] getMapTiles() {
		return mapTiles;
	}

	public void setMapTiles(Entity[] mapTiles) {
		this.mapTiles = mapTiles;
	}
	
	
}
