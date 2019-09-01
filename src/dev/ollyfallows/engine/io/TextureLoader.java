package dev.ollyfallows.engine.io;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.system.MemoryUtil;

import dev.ollyfallows.engine.graphics.Texture;

public class TextureLoader {
	
	private static TextureLoader instance = null;
	
	private HashMap<String, Texture> textures = new HashMap<String, Texture>();

	private TextureLoader() {}
	
    public Texture createTexture(int width, int height, IntBuffer data) {
        Texture texture = new Texture();
        texture.setWidth(width);
        texture.setHeight(height);

        texture.bind();

        texture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

        return texture;
    }

    public Texture loadTexture(String path) {
    	if (textures.containsKey(path)) {
    		if (textures.get(path).loaded()) {
    			return textures.get(path);
    		}else {
    			textures.remove(path);
    		}
    	}
        try {
	        BufferedImage img=null;
        	img = ImageIO.read(getClass().getResource(path));
        	
        	int width = img.getWidth();
        	int height = img.getHeight();
        	
        	IntBuffer buffer = MemoryUtil.memAllocInt(width*height);
    		for (int a=0; a<height; a++) {
    			for (int b=0; b<width; b++) {
    				Color c = new Color(img.getRGB(b, a), true);
	        		buffer.put(c.getRed()|c.getGreen()<<8|c.getBlue()<<16|c.getAlpha()<<24);
        		}
        	}
        	buffer.flip();
            
        	Texture texture = createTexture(img.getWidth(), img.getHeight(), buffer);
        	textures.put(path, texture);
        	
            return texture;
        }catch(Exception e) {
        	e.printStackTrace();
        	System.exit(-1);
        }
        return null;
    }
    
    public static TextureLoader getInstance() {
    	if (instance == null) {
    		instance = new TextureLoader();
    	}
    	return instance;
    }
}
