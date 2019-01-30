package input;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import blocks.Campfire;
import blocks.WoodenWall;
import entities.Camera;
import entities.Entity;
import map.Map;
import map.MapInteraction;
import map.Tiles;
import renderEngine.Renderer;

public class MouseController {
	private static boolean mouse1Clicked = false;
	private static boolean mouse2Clicked = false;
	private static boolean activeUI = false;
	public void mouseMovement(Camera camera, Entity entity){
		if(!activeUI){
			if(Mouse.isButtonDown(0) && mouse1Clicked == false){
				mouse1Clicked = true;
				Vector2f tileClicked = clickTile(camera);
				System.out.println(tileClicked);
				Entity positionInMapArray = Map.getMapTiles()[(int) (tileClicked.y * Math.sqrt(Map.getMapTiles().length) + tileClicked.x)];
				positionInMapArray.setRotY(180);
				//MapInteraction.addBlock(tileClicked, Tiles.checkSurroundingsWood(tileClicked));
				//Tiles.checkSurroundingsWood(tileClicked);
				WoodenWall test = new WoodenWall(tileClicked);
				test.placeBlock(tileClicked);
				test.printSurroundings(tileClicked);
			}
			if(!Mouse.isButtonDown(0)){
				mouse1Clicked = false;
			}
			if(Mouse.isButtonDown(1) && mouse2Clicked == false){
				mouse2Clicked = true;
				Campfire fire = new Campfire(clickTile(camera));
				//fire.placeBlock();
				Vector2f tileClicked = clickTile(camera);
				fire.placeBlock(tileClicked);
				
				int x = 0;
				int y = 0;
				for(int i = 0; i < MapInteraction.tilesOnMap.length; i++){
					if(x== Math.sqrt(MapInteraction.tilesOnMap.length)){
						x=0;
						y++;
					}
					if(MapInteraction.tilesOnMap[i] == "Wooden Walls"){
							Tiles.woodSurroundingTiles(new Vector2f(x,y));
					}
					x++;
				}
				
				System.out.println(MapInteraction.tilesOnMap[(int) (tileClicked.y * Math.sqrt(Map.getMapTiles().length) + tileClicked.x)]);
			}
			if(!Mouse.isButtonDown(1)){
				mouse2Clicked = false;
			}
		}
		
	}
	
	public Vector2f clickTile(Camera camera){
		float camX = camera.getPosition().x;
		float camY = camera.getPosition().y;
		float camZ = camera.getPosition().z + 5;
		float fov = (float) Math.toRadians(Renderer.FOV);
		float vFov = (float) Math.toRadians(59);
		
		float leftBound = (float) ((camX - camZ * Math.tan(fov / 2)) / 2); 
		float rightBound  =  (float) ((camX + camZ * Math.tan(fov / 2)) / 2); 
		float topBound  = (float) -((camY + camZ * Math.tan(vFov / 2)) / 2); 
		float bottomBound = (float) -((camY - camZ * Math.tan(vFov / 2))/ 2);
		float width = (rightBound - leftBound);
		float height = (bottomBound - topBound);
		float mX = (float)Math.floor((width * (double)Mouse.getX() / Display.getWidth() + (leftBound)));
		float mY = (float)Math.floor(height * (double)(-Mouse.getY() + Display.getHeight()) / Display.getHeight() + (topBound));
		if(mY >= 0 && mX >= 0){
			return new Vector2f(mX, mY);
		} else return new Vector2f(0,0);
		
	}
	
	public boolean isUI(boolean b){
		return activeUI = b;
	}
	

}
