package dev.ollyfallows.engine.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import dev.ollyfallows.engine.entities.Camera;
import dev.ollyfallows.engine.entities.Entity;

public class Renderer {
	
	private HashMap<Mesh, ArrayList<Entity>> entities = new HashMap<Mesh, ArrayList<Entity>>();
	
	/*public void renderMesh(Mesh mesh) {
		GL30.glBindVertexArray(mesh.getVao());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIbo());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL13.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getTexture().getTextureId());
		GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}*/
	
	public void addEntity(Entity e) {
		for (Mesh m : e.getMeshs()) {
			if (entities.containsKey(m)) {
				entities.get(m).add(e);
			}else {
				entities.put(m, new ArrayList<Entity>());
				entities.get(m).add(e);
			}
		}
	}
	
	public void render(Camera cam) {
		for (Entry<Mesh, ArrayList<Entity>> entry : entities.entrySet()) {
			Mesh mesh = entry.getKey();
			ArrayList<Entity> es = entry.getValue();
			GL30.glBindVertexArray(mesh.getVAO());
			GL30.glEnableVertexAttribArray(0);
			GL30.glEnableVertexAttribArray(1);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL13.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getTexture().getTextureId());
			for (Entity e : es) {
				e.getShader().bind(cam, e);
				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
				e.getShader().unbind();
			}
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
			GL30.glDisableVertexAttribArray(0);
			GL30.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
		entities.clear();
	}
}
