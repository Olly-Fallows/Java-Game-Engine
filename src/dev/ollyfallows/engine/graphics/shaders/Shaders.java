package dev.ollyfallows.engine.graphics.shaders;

import java.util.HashMap;

import dev.ollyfallows.engine.Utils;

public class Shaders {
	
	private static Shaders instance = null;
	private HashMap<String, ShaderProgram> shaders = new HashMap<String, ShaderProgram>();
	
	private Shaders() {
		try {
			ShaderProgram shader = new BasicShader(false);
			addShader("default", shader);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public boolean addShader(String name, ShaderProgram shader) {
		if (shaders.containsKey(name)) {
			return false;
		}
		shaders.put(name, shader);
		return true;
	}
	public ShaderProgram getShader(String name) {
		if (!shaders.containsKey(name)) {
			return null;
		}
		return shaders.get(name);
	}
	
	public static Shaders getInstance() {
		if (instance == null) {
			instance = new Shaders();
		}
		return instance;
	}
}
