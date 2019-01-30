package ui;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Renderer;
import toolbox.Maths;

public class UIRenderer {
	private static Matrix4f pMatrix;
	private static UIShader shader = new UIShader();

	public static void render(Map<UIElement, List<Button>> elements){
		prepare();
		for(UIElement element: elements.keySet()){
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, element.getEn().getModel().getTexture().getID());
			shader.loadColor(new Vector4f(0,0,0,0));
			renderUI(element);
			
			for(Button b:elements.get(element)){
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, b.getActiveModel().getTexture().getID());
				shader.loadColor(new Vector4f(0,0,0,0));
				renderButton(b);
			}
			for(Icon i: element.getIcons()){
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, i.getEn().getModel().getTexture().getID());
				renderIcon(i);
			}
		}
		shader.stop();
	}
	public static void renderUI(UIElement ui){
		
		TexturedModel model = ui.getEn().getModel();
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
    	GL20.glEnableVertexAttribArray(0);
    	GL20.glEnableVertexAttribArray(1);
    	Matrix4f tMatrix = Maths.createTMatrix(ui.getEn().getPosition(), 0, 0, 270, new Vector3f(ui.getScale().x , ui.getScale().y * (0.5625f), 1));
    	shader.loadTMatrix(tMatrix);
    	shader.loadTranslation(new Vector2f(ui.getPosition().x, ui.getPosition().y));
    	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    	
    	GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    	GL20.glDisableVertexAttribArray(0);
    	GL20.glDisableVertexAttribArray(1);
    	GL30.glBindVertexArray(0);
	}
	
	public static void renderButton(Button b){
		TexturedModel model = b.getEn().getModel();
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
    	GL20.glEnableVertexAttribArray(0);
    	GL20.glEnableVertexAttribArray(1);
    	Matrix4f tMatrix = Maths.createTMatrix(b.getEn().getPosition(), 0, 0, 270, new Vector3f(b.getScale().x , b.getScale().y * (0.5625f), 1));
    	shader.loadTMatrix(tMatrix);
    	shader.loadTranslation(new Vector2f(b.getPosition().x, b.getPosition().y));
    	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    	GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    	GL20.glDisableVertexAttribArray(0);
    	GL20.glDisableVertexAttribArray(1);
    	GL30.glBindVertexArray(0);
	}
	public static void renderIcon(Icon i){
		TexturedModel model = i.getEn().getModel();
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
    	GL20.glEnableVertexAttribArray(0);
    	GL20.glEnableVertexAttribArray(1);
    	Matrix4f tMatrix = Maths.createTMatrix(i.getEn().getPosition(), 0, 0, 270, new Vector3f(i.getScale().x , i.getScale().y * (0.5625f), 1));
    	shader.loadTMatrix(tMatrix);
    	shader.loadTranslation(new Vector2f(i.getPosition().x, i.getPosition().y));
    	shader.loadColor(i.getColor());
    	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    	GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    	GL20.glDisableVertexAttribArray(0);
    	GL20.glDisableVertexAttribArray(1);
    	GL30.glBindVertexArray(0);
	}
	
	public static void cleanUp(){
		shader.cleanUp();
	}
	private static void prepare(){
		GL11.glEnable(GL11.GL_BLEND);
		
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glDisable(GL11.GL_DEPTH_TEST);
    	shader.start();
	}

}
