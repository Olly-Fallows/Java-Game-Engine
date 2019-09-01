package dev.ollyfallows.engine.graphics;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.nio.IntBuffer;

public class Texture {

    private final int textureId;
    private int width;
    private int height;
    private boolean loaded=false;

    public Texture() {
        textureId = glGenTextures();
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void setParameter(int name, int value) {
        glTexParameteri(GL_TEXTURE_2D, name, value);
    }

    public void uploadData(int width, int height, IntBuffer data) {
        uploadData(GL_RGBA8, width, height, GL_RGBA, data);
    }

    public void uploadData(int internalFormat, int width, int height, int format, IntBuffer data) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
        loaded = true;
    }

    public void destroy() {
        glDeleteTextures(textureId);
        loaded = false;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
    }
    
    public int getTextureId() {
    	return textureId;
    }

	public boolean loaded() {
		return loaded;
	}

}