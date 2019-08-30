package dev.ollyfallows.engine.graphics;

import dev.ollyfallows.engine.maths.Vector3f;

public class Vertex {
	
	public static final int NO_INDEX=-1;
	
	private Vector3f position;
	private int textureIndex = NO_INDEX;
	private int normalIndex = NO_INDEX;
	private Vertex duplicateVertex = null;
	private int index;
	private float length;
	
	public Vertex(Vector3f position) {
		this.position = position;
	}
	public Vertex(float x, float y, float z) {
		this.position = new Vector3f(x, y, z);
	}
	
	public Vector3f getPosition() {
		return position;
	}
}
