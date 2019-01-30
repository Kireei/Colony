package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer {
	public static Matrix4f pMatrix;
	
	public static final float FOV = 90;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000f;

	
	public static Map<TexturedModel, List<Entity>> map = new HashMap<TexturedModel, List<Entity>>();
	public static Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	public static List<Light> lights = new ArrayList<Light>();
	
	
	public Renderer(StaticShader shader){
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		createPMatrix();
		shader.start();
		shader.loadPMatrix(pMatrix);
	}
	
	public void prepare(){
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); 
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	public static void render(Entity entity, StaticShader shader){
		TexturedModel model = entity.getModel();
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		Matrix4f tMatrix = Maths.createTMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTMatrix(tMatrix);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		//GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	private void createPMatrix(){
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale  / aspectRatio;
		float frustrum_length = FAR_PLANE - NEAR_PLANE;
		
		pMatrix = new Matrix4f();
		pMatrix.m00 = x_scale;
		pMatrix.m11 = y_scale;
		pMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustrum_length);
		pMatrix.m23 = -1;
		pMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustrum_length);
		pMatrix.m33 = 0;
		
	}

	
	public static void render(Map<TexturedModel, List<Entity>> entities, StaticShader shader, Camera camera){
		for(TexturedModel model:entities.keySet()){
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity mapTile:batch){
				if(mapTile.getPosition().x < camera.getPosition().x + camera.getPosition().z + 10 
						&& mapTile.getPosition().x > camera.getPosition().x - camera.getPosition().z -10
						&& mapTile.getPosition().y < camera.getPosition().y + (camera.getPosition().z + 10) * Math.tan(Math.toRadians(29.5)) 
						&& mapTile.getPosition().y > camera.getPosition().y - (camera.getPosition().z + 10) * Math.tan(Math.toRadians(29.5))){
				mapTile.setRotZ(270);
				prepareInstance(mapTile, shader);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				}else continue;
			}
			unbindTexturedModel();
		}
	}
	public static void prepareTexturedModel(TexturedModel model){
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
	}
	
	public static void unbindTexturedModel(){
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	public static void prepareInstance(Entity entity, StaticShader shader){
		Matrix4f transformationMatrix = Maths.createTMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTMatrix(transformationMatrix);
	}
	
	public static void processEntity(Entity entity, Map<TexturedModel, List<Entity>> entities){
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null){
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
}
