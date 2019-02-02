package blocks;

import org.lwjgl.util.vector.Vector2f;

import map.Map;
import map.MapInteraction;
import map.Tiles;
import models.TexturedModel;
import textures.ModelTexture;

public class WoodenWall extends Block{
	
	private String[] surrounding = new String[8];
	private Vector2f[] surroundingTiles = new Vector2f[8];
	private boolean[] isSurroundingSame = new boolean[8];
	
	public WoodenWall(Vector2f tile) {
		super(tile);
		super.setTexModel(new TexturedModel(super.getRawModel(), new ModelTexture(Block.loader.loadTexture("sprites/Wood STL"))));
		super.setId("Wooden Wall");
		Tiles.woodenWall.put(tile, this);
		surroundingTiles[0] = new Vector2f((tile.x - 1),(tile.y - 1));
		surroundingTiles[1] = new Vector2f((tile.x), (tile.y - 1));
		surroundingTiles[2] = new Vector2f((tile.x + 1),(tile.y - 1));
		surroundingTiles[3] = new Vector2f((tile.x - 1), (tile.y));
		surroundingTiles[4] = new Vector2f((tile.x + 1), (tile.y));
		surroundingTiles[5] = new Vector2f((tile.x - 1), (tile.y + 1));
		surroundingTiles[6] = new Vector2f((tile.x), (tile.y + 1));
		surroundingTiles[7] = new Vector2f((tile.x + 1), (tile.y + 1));
		
	}

	@Override
	public void placeBlock(Vector2f tile) {
		checkSurroundings(tile);
		for(int i = 0; i < 8; i++){
			if(isSurroundingSame[i] && Tiles.woodenWall.get(surroundingTiles[i]) != null){
				System.out.println(Tiles.woodenWall.get(surroundingTiles[i]));
				Tiles.woodenWall.get(surroundingTiles[i]).checkSurroundings(tile);
			}
		}
		super.addBlock();
		
	}
	public void printSurroundings(Vector2f tile) {
		Vector2f centerTile = tile;
		surrounding[0] = MapInteraction.tilesOnMap[(int) ((centerTile.y - 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x - 1))];
		surrounding[1] = MapInteraction.tilesOnMap[(int) ((centerTile.y - 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x))];
		surrounding[2] = MapInteraction.tilesOnMap[(int) ((centerTile.y - 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x + 1))];
		surrounding[3] = MapInteraction.tilesOnMap[(int) ((centerTile.y) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x - 1))];
		surrounding[4] = MapInteraction.tilesOnMap[(int) ((centerTile.y) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x + 1))];
		surrounding[5] = MapInteraction.tilesOnMap[(int) ((centerTile.y + 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x - 1))];
		surrounding[6] = MapInteraction.tilesOnMap[(int) ((centerTile.y + 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x))];
		surrounding[7] = MapInteraction.tilesOnMap[(int) ((centerTile.y + 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x + 1))];
		
		System.out.println(surrounding[0] + ", " + surrounding[1] + ", " + surrounding[2]);
		System.out.println(surrounding[3] + ", Middle, " + surrounding[4]);
		System.out.println(surrounding[5] + ", " + surrounding[6] + ", " + surrounding[7]);
		
	}
	
	private void checkSurroundings(Vector2f tile){
		Vector2f centerTile = tile;
		surrounding[0] = MapInteraction.tilesOnMap[(int) ((centerTile.y - 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x - 1))];
		surrounding[1] = MapInteraction.tilesOnMap[(int) ((centerTile.y - 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x))];
		surrounding[2] = MapInteraction.tilesOnMap[(int) ((centerTile.y - 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x + 1))];
		surrounding[3] = MapInteraction.tilesOnMap[(int) ((centerTile.y) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x - 1))];
		surrounding[4] = MapInteraction.tilesOnMap[(int) ((centerTile.y) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x + 1))];
		surrounding[5] = MapInteraction.tilesOnMap[(int) ((centerTile.y + 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x - 1))];
		surrounding[6] = MapInteraction.tilesOnMap[(int) ((centerTile.y + 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x))];
		surrounding[7] = MapInteraction.tilesOnMap[(int) ((centerTile.y + 1) * Math.sqrt(Map.getMapTiles().length) + (centerTile.x + 1))];
		
		for(int i = 0; i < 8; i++){
			if(surrounding[i] == super.getId()) isSurroundingSame[i] = true;
			else isSurroundingSame[i] = false;
		}
		if(isSurroundingSame[1] && isSurroundingSame[3] && isSurroundingSame[4] && isSurroundingSame[6]){ setTexModel(Tiles.textures.get("Wooden Walls").get(0));return;}
		if(isSurroundingSame[1] && isSurroundingSame[4] && isSurroundingSame[6] && !isSurroundingSame[3]){ setTexModel(Tiles.textures.get("Wooden Walls").get(1));return;}
		if(!isSurroundingSame[1] && isSurroundingSame[4] && isSurroundingSame[6] && isSurroundingSame[3]){ setTexModel(Tiles.textures.get("Wooden Walls").get(2));return;}
		if(isSurroundingSame[1] && !isSurroundingSame[4] && isSurroundingSame[6] && isSurroundingSame[3]){ setTexModel(Tiles.textures.get("Wooden Walls").get(3));return;}
		if(isSurroundingSame[1] && isSurroundingSame[4] && !isSurroundingSame[6] && isSurroundingSame[3]){ setTexModel(Tiles.textures.get("Wooden Walls").get(4));return;}
		if(!isSurroundingSame[1] && isSurroundingSame[4] && isSurroundingSame[6] && !isSurroundingSame[3]){ setTexModel(Tiles.textures.get("Wooden Walls").get(5));return;}
		if(!isSurroundingSame[1] && !isSurroundingSame[4] && isSurroundingSame[6] && isSurroundingSame[3]){ setTexModel(Tiles.textures.get("Wooden Walls").get(6));return;}
		if(isSurroundingSame[1] && !isSurroundingSame[4] && !isSurroundingSame[6] && isSurroundingSame[3]){ setTexModel(Tiles.textures.get("Wooden Walls").get(7));return;}
		if(isSurroundingSame[1] && isSurroundingSame[4] && !isSurroundingSame[6] && !isSurroundingSame[3]){ setTexModel(Tiles.textures.get("Wooden Walls").get(8));return;}
		if(!isSurroundingSame[1] && (isSurroundingSame[3] || isSurroundingSame[4]) && !isSurroundingSame[6]){ setTexModel(Tiles.textures.get("Wooden Walls").get(9));return;}
		if((isSurroundingSame[1] || isSurroundingSame[6]) && !isSurroundingSame[4]  && !isSurroundingSame[3]){ setTexModel(Tiles.textures.get("Wooden Walls").get(10));return;}
		return;
		
		/*System.out.println(surrounding[0]);
		System.out.println(surrounding[1]);
		System.out.println(surrounding[2]);
		System.out.println(surrounding[3]);
		System.out.println(surrounding[4]);
		System.out.println(surrounding[5]);
		System.out.println(surrounding[6]);
		System.out.println(surrounding[7]);*/
	}
	
 

}
