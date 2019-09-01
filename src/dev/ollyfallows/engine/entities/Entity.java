package dev.ollyfallows.engine.entities;

import dev.ollyfallows.engine.graphics.Mesh;
import dev.ollyfallows.engine.graphics.Renderer;
import dev.ollyfallows.engine.graphics.Texture;
import dev.ollyfallows.engine.graphics.shaders.ShaderProgram;
import dev.ollyfallows.engine.graphics.shaders.Shaders;
import dev.ollyfallows.engine.maths.Vector3f;

public class Entity extends GameElement{
	
	protected Vector3f pos, rot, sca;
	protected float dx=0,dy=0,dz=0;
	protected ShaderProgram shader = Shaders.getInstance().getShader("default");
	protected Mesh[] meshs;
	
	public Entity(Vector3f pos, Mesh[] meshs) {
		this(pos, new Vector3f(0f,0f,0f), meshs);
	}
	public Entity(Vector3f pos, Vector3f rot, Mesh[] meshs) {
		this(pos, rot, new Vector3f(1f,1f,1f), meshs);
	}
	public Entity(Vector3f pos, Vector3f rot, Vector3f sca, Mesh[] meshs) {
		this.pos = pos;
		this.rot = rot;
		this.sca = sca;
		this.meshs = meshs;
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
	public Mesh[] getMeshs() {
		return meshs;
	}
	public ShaderProgram getShader() {
		return shader;
	}
	
	public void midFrame(long delta) {
		rot.y += 45*((float)delta/1000);
	}
}
