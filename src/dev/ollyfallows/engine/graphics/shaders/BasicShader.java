package dev.ollyfallows.engine.graphics.shaders;

import dev.ollyfallows.engine.entities.Camera;
import dev.ollyfallows.engine.entities.Entity;
import dev.ollyfallows.engine.io.Utils;
import dev.ollyfallows.engine.io.Window;
import dev.ollyfallows.engine.maths.Matrix4f;

public class BasicShader extends ShaderProgram{

	
	public BasicShader() throws Exception{
		super(Utils.getInstance().loadResourceString("/resources/shaders/basic-vert-shader.glsl"), Utils.getInstance().loadResourceString("/resources/shaders/basic-frag-shader.glsl"));
	}
	public BasicShader(boolean flag) throws Exception {
		super(Utils.getInstance().loadResourceString("/resources/shaders/basic-vert-shader.glsl"), Utils.getInstance().loadResourceString("/resources/shaders/basic-frag-shader.glsl"), flag);
	}
	
	@Override
	public void bind(Camera cam, Entity e) {
		super.bind(cam, e);
		setUniform("model", Matrix4f.transform(e.getPos(), e.getRot(), e.getSca()));
		setUniform("view", Matrix4f.view(cam.getPos(), cam.getRot()));
		setUniform("projection", Window.projection);
		setUniform("tex", 0);
	}
}
