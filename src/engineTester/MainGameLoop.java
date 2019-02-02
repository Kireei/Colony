package engineTester;


import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Character;
import entities.Light;
import entities.NPC;
import fontRendering.TextMaster;
import input.MouseController;
import map.Image;
import map.Map;
import map.MapInteraction;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import ui.UIHandler;
import ui.UIMaster;

public class MainGameLoop {
	public static float time = 0;
	
	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();

		System.out.println("x");
		TextMaster.init(loader);
		
		
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		Camera camera = new Camera();
		Image mapImage = new Image("/Map.png");
		Character character = new Character(camera);
		//character.prepareCharacter();
		
		
		Map map = new Map(mapImage, loader);
		camera.setPosition(new Vector3f(0, 0, 20));
		NPC NPC[] = new NPC[50];
		for(int i = 0; i < NPC.length; i++){
			NPC[i]= new NPC(new Vector3f(0, 0,-5), new Vector3f(1, 2, 1), "sprites/Guy");
			System.out.println("NPC #" + (i + 1) + " loaded!");
		}
		UIHandler ui = new UIHandler(camera,shader);
		MouseController mouseController = new MouseController();
		List<Light> light = new ArrayList<Light>();
		
		
		
		time = 0;
		while(!Display.isCloseRequested()){
			mouseController.isUI(ui.isUIActive());
			if(!ui.isUIActive()){
				camera.move();
				mouseController.mouseMovement(camera, NPC[1].entity);
				character.move();
				for(NPC npc: NPC){
					npc.calculateDirection();
				}
				time += 0.001f;
			}
			
			renderer.prepare();
			shader.start();
			shader.loadEnviromentalLighting(new Vector4f((float)(0.5f * Math.sin(time + Math.PI / 2) + 0.5f),(float)(0.5f * Math.sin(time + Math.PI / 2) + 0.5f),(float)(0.5f * Math.sin(time + Math.PI / 2) + 0.5f),1f));
			shader.loadVMatrix(camera);
			shader.loadLights(Renderer.lights, camera);
			Renderer.render(Renderer.map, shader, camera);
			Renderer.render(Renderer.entities, shader, camera);
			Renderer.render(MapInteraction.interactedTiles, shader, camera);
			Renderer.render(character.entity, shader);
			
			for(NPC npc: NPC){
				Renderer.render(npc.entity, shader);
			}
			ui.uiCheck();
			shader.stop();
			UIMaster.render();
			TextMaster.render();
			DisplayManager.updateDisplay();
			

		}
		UIMaster.cleanUp();
		TextMaster.cleanUp();
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
