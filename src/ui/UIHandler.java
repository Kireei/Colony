package ui;

import java.io.File;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import engineTester.MainGameLoop;
import entities.Camera;
import fontMeshCreator.FontType;
import models.RawModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import shaders.StaticShader;

public class UIHandler {
	private UIElement escapeMenu;
	private boolean isEscapeMenu;
	
	private UIElement inventory;
	private boolean isInventory;
	
	public static FontType font = new FontType(new Loader().loadFont("pixelated"), new File("res/Fonts/pixelated.fnt"));
	public static Loader loader = new Loader();
	public static RawModel rawModel = OBJLoader.loadObjModel("plane2", loader);
	
	
	public UIHandler(Camera camera, StaticShader shader){
		//escapeMenu = new EscapeMenu(new Vector2f(camera.getPosition().x, camera.getPosition().y), new Vector3f(1,1,1), shader, camera);
		createEscapeMenu();
		createInventory();
	}
	
	
	public void uiCheck(){
		checkInventory();
		checkEscapeMenu();
		
		
		
	}
	
	private void checkEscapeMenu(){	
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !isEscapeMenu && !inventory.isToggled()){
			
			escapeMenu.setToggled(!escapeMenu.isToggled());
			if(escapeMenu.isToggled()){
				UIMaster.loadUI(escapeMenu);
				isInventory = false;
				
			}
			
			if(!escapeMenu.isToggled()){
				UIMaster.removeUI(escapeMenu);
			}
			isEscapeMenu = true;
		}
		if(escapeMenu.isToggled()){
			for(Button b: escapeMenu.getButtons()){
				b.checkClick();
				
				if(b.isClicked()){
					switch(b.getTitle().getTextString()){
					case "Quit":
						System.exit(0);
						break;
					case "Set Day":
						MainGameLoop.time = 0;
						break;
					case "Set Night":
						MainGameLoop.time = (float)Math.PI;
						break;
					}
				}
			}
			
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){isEscapeMenu = false;}
		//System.out.println(UIMaster.ui.get(0));
	}
	
	public void checkInventory() {

		if(Keyboard.isKeyDown(Keyboard.KEY_E) && !isInventory && !escapeMenu.isToggled()){
			inventory.setToggled(!inventory.isToggled());
			if(inventory.isToggled()){
				UIMaster.loadUI(inventory);
			}
			if(!inventory.isToggled()){
				UIMaster.removeUI(inventory);
			}
			isInventory = true;
		}
		
		
		
		if(inventory.isToggled()){
			for(Button b: inventory.getButtons()){
				b.checkClick();
				if(b.isClicked()){
					switch(b.getTitle().getTextString()){
					case "Tree":
						System.exit(0);
						break;
					case "Set Day":
						MainGameLoop.time = 0;
						break;
					}
				}
			}
			for(Icon i : inventory.getIcons()){
				i.checkClick();
				if(i.isClicked()) {
					System.out.println(i.getId());
					
				}
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && isInventory){
			inventory.setToggled(false);
			System.out.println("debug");
			UIMaster.removeUI(inventory);
			
			isInventory = false;
			return;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_E)){isInventory = false;}
	}
	
	public boolean isUIActive(){
		if(escapeMenu.isToggled()) return true;
		if(inventory.isToggled()) return true;
		else return false;
	}
	
	private void createEscapeMenu(){
		escapeMenu = new UIElement(new Vector3f(0,0,1), new Vector2f(1,1), "Escape Menu");
		escapeMenu.addButton(new Vector2f(0,0.5f), 0.4f, "Set Day", new Vector2f(0,0));
		escapeMenu.addButton(new Vector2f(0,0.0f), 0.4f, "Set Night", new Vector2f(0,0));
		escapeMenu.addButton(new Vector2f(0,-0.5f), 0.4f, "Quit", new Vector2f(0,0));
	}
	
	private void createInventory(){
		inventory = new UIElement(new Vector3f(0, 0, 0.9f), new Vector2f(1,1), "Inventory");
		inventory.addButton(new Vector2f(-0.4f,0.5f), 0.2f, "Wood", new Vector2f(0,0));
		inventory.addButton(new Vector2f(-0.4f,0.43f), 0.2f, "Tree", new Vector2f(0,0));
		inventory.addIcon(new Vector2f(-0.475f,-0.11f), new Vector2f(0.1f,0.1f), "sprites/Water", "inventoryIconWater");
		inventory.addIcon(new Vector2f(-0.36f,-0.11f), new Vector2f(0.1f,0.1f), "sprites/Sand", "inventoryIconSand");
		inventory.addIcon(new Vector2f(-0.245f,-0.11f), new Vector2f(0.1f,0.1f), "sprites/Grass", "inventoryIconGrass");
		inventory.addIcon(new Vector2f(-0.13f,-0.11f), new Vector2f(0.1f,0.1f), "sprites/Wooden Flooring", "inventoryIconWoodenFloor");
		inventory.addIcon(new Vector2f(-0.015f,-0.11f), new Vector2f(0.1f,0.1f), "sprites/Wood STL", "inventoryIconWoodenWall");
		inventory.addIcon(new Vector2f(0.1f,-0.11f), new Vector2f(0.1f,0.1f), "sprites/Tree", "inventoryIconTree");
		inventory.addIcon(new Vector2f(0.215f,-0.11f), new Vector2f(0.1f,0.1f), "sprites/Bush", "inventoryIconBush");
	}
}
