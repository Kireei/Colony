package shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private static final int MAX_LIGHT = 51;
	
	private int location_tMatrix;
	private int location_vMatrix;
	private int location_pMatrix;
	private int location_Sampler;
	private int location_enviromentalLighting;
	private int location_lightPos[];
	private int location_lightColor[];
	private int location_lightPower[];
	
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		
	}

	@Override
	protected void getAllUniformLocations() {
		location_tMatrix = super.getUniformLocation("tMatrix");
		location_vMatrix = super.getUniformLocation("vMatrix");
		location_pMatrix = super.getUniformLocation("pMatrix");
		location_Sampler = super.getUniformLocation("sampler");
		location_enviromentalLighting = super.getUniformLocation("enviromentalLighting");
		location_lightPos = new int[MAX_LIGHT];
		location_lightColor = new int[MAX_LIGHT];
		location_lightPower = new int[MAX_LIGHT];
		for(int i = 0; i< MAX_LIGHT; i++){
			location_lightPos[i] = super.getUniformLocation("lightPos[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_lightPower[i] = super.getUniformLocation("lightPower[" + i + "]");
		}
		
	}
	
	public void loadTMatrix(Matrix4f matrix){
		super.loadMatrix(location_tMatrix, matrix);
	}
	public void loadVMatrix(Camera camera){
		Matrix4f vMatrix = Maths.createVMatrix(camera);
		super.loadMatrix(location_vMatrix, vMatrix);
	}
	public void loadPMatrix(Matrix4f matrix){
		super.loadMatrix(location_pMatrix, matrix);
	}
	public void loadSampler(float value){
		super.loadFloat(location_Sampler, value);
	}
	public void loadEnviromentalLighting(Vector4f vector){
		super.loadVector4(location_enviromentalLighting, vector);
	}
	
	public void loadLights(List<Light> light, Camera camera){
		for(int i = 0; i < light.size(); i++){
			float range = (float) Math.pow(light.get(i).getPower(), 2);
			if(i<light.size() && light.get(i).getPosition().x < camera.getPosition().x + camera.getPosition().z + range 
					&& light.get(i).getPosition().x > camera.getPosition().x - camera.getPosition().z -range
					&& light.get(i).getPosition().y < camera.getPosition().y + (camera.getPosition().z + range) * Math.tan(Math.toRadians(29.5)) 
					&& light.get(i).getPosition().y > camera.getPosition().y - (camera.getPosition().z + range) * Math.tan(Math.toRadians(29.5))){
				super.loadVector3(location_lightPos[i], light.get(i).getPosition());
				super.loadVector3(location_lightColor[i], light.get(i).getColor());
				super.loadFloat(location_lightPower[i], light.get(i).getPower());
				
				
			} else {
				super.loadVector3(location_lightPos[i], new Vector3f(0,0,1));
				super.loadVector3(location_lightColor[i], new Vector3f(0,0,0));
				super.loadFloat(location_lightPower[i], 0f);
			}
		}
	}
	
}
