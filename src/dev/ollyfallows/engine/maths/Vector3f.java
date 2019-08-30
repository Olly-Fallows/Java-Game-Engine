package dev.ollyfallows.engine.maths;

public class Vector3f {
	public float x,y,z;
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public Vector3f invert() {
		return new Vector3f(-x,-y,-z);
	}
}
