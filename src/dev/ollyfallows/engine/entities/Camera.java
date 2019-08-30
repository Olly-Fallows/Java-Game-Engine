package dev.ollyfallows.engine.entities;

import dev.ollyfallows.engine.maths.Vector3f;

public class Camera {
	
	private Vector3f pos, rot;
	
	public Camera(Vector3f pos, Vector3f rot) {
		this.pos = pos;
		this.rot = rot;
	}
	
	public Vector3f getPos() {
		return pos;
	}
	public Vector3f getRot() {
		return rot;
	}
}
