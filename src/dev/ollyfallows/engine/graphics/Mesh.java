package dev.ollyfallows.engine.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Mesh implements Cloneable{
	
	private Vertex[] vertices;
	private int[] indices;
	private float[] normals;
	private float[] textCoords;
	private Texture texture;
	private int vao, pbo, ibo, tbo;
	
	public Mesh(Vertex[] vertices, int[] indices, float[] normals, float[] textCoords, Texture texture) {
		this.vertices = vertices;
		this.indices = indices;
		this.normals = normals;
		this.textCoords = textCoords;
		this.texture = texture;
	}
	
	public Mesh create() {
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length*3);
		float[] positionData = new float[vertices.length*3];
		for (int a=0; a<vertices.length; a++) {
			positionData[a*3] = vertices[a].getPosition().x;
			positionData[a*3+1] = vertices[a].getPosition().y;
			positionData[a*3+2] = vertices[a].getPosition().z;
		}
		positionBuffer.put(positionData);
		positionBuffer.flip();
		
		pbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, pbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		
		FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(textCoords.length);
		textureBuffer.put(textCoords);
		textureBuffer.flip();
		
		tbo = storeData(textureBuffer, 1, 2);
		
		IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
		indicesBuffer.put(indices);
		indicesBuffer.flip();
		
		ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		return this;
	}
	
	private int storeData(FloatBuffer buffer, int index, int size) {
		int bufferID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return bufferID;
	}
	
	public void destroy() {
		GL15.glDeleteBuffers(pbo);
		GL15.glDeleteBuffers(ibo);
		GL15.glDeleteBuffers(tbo);
		
		GL30.glDeleteVertexArrays(vao);
		
		texture.destroy();
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public int getVao() {
		return vao;
	}

	public int getPbo() {
		return pbo;
	}

	public int getIbo() {
		return ibo;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public Mesh clone() throws CloneNotSupportedException{
		return (Mesh)super.clone();
	}
}
