package ui;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import shaders.ShaderProgram;
import toolbox.Maths;

public class UIShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/ui/uiVertex.txt";
    private static final String FRAGMENT_FILE = "src/ui/uiFragment.txt";
    
    private int location_translation;
    private int location_tMatrix;
    private int location_color;

	
	public UIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_translation = super.getUniformLocation("translation");
		location_tMatrix = super.getUniformLocation("tMatrix");
		location_color = super.getUniformLocation("color");

		
	}

	@Override
	protected void bindAttributes() {
    	super.bindAttribute(0, "position");
    	super.bindAttribute(1, "textureCoords");
    }
	
	protected void loadTranslation(Vector2f translation){
		super.loadVector2(location_translation, translation);
	}
	public void loadTMatrix(Matrix4f matrix){
		super.loadMatrix(location_tMatrix, matrix);
	}
	public void loadColor(Vector4f vector){
		super.loadVector4(location_color, vector);
	}


}
