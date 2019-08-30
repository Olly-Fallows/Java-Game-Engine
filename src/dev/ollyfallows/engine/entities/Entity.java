package dev.ollyfallows.engine.entities;

import dev.ollyfallows.engine.graphics.Mesh;
import dev.ollyfallows.engine.graphics.Renderer;
import dev.ollyfallows.engine.graphics.shaders.ShaderProgram;
import dev.ollyfallows.engine.graphics.shaders.Shaders;
import dev.ollyfallows.engine.maths.Vector3f;

public class Entity extends GameElement{
	
	protected Vector3f pos, rot, sca;
	protected float dx=0,dy=0,dz=0;
	protected ShaderProgram shader = Shaders.getInstance().getShader("default");
	protected Mesh mesh;
	
	public Entity(Vector3f pos, Mesh mesh) {
		this.pos = pos;
		this.mesh = mesh;
	}
	public Entity(Vector3f pos, Vector3f rot, Mesh mesh) {
		this.pos = pos;
		this.rot = rot;
		this.mesh = mesh;
	}
	public Entity(Vector3f pos, Vector3f rot, Vector3f sca, Mesh mesh) {
		this.pos = pos;
		this.rot = rot;
		this.sca = sca;
		this.mesh = mesh;
	}
	
	public void render(Camera cam, Renderer renderer) {
		shader.bind(cam, this);
		renderer.renderMesh(mesh);
		shader.unbind();
	}
	public Vector3f getPos() {
		return pos;
	}
	public Vector3f getRot() {
		return rot;
	}
	public Vector3f getSca() {
		return sca;
	}
	
	
}
