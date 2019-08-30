package dev.ollyfallows.engine.io;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import dev.ollyfallows.engine.maths.Matrix4f;
import dev.ollyfallows.engine.maths.Vector3f;

public class Window {
	
	private int width, height;
	private String title;
	private long window;
	private Vector3f bgc = new Vector3f(0f,0f,0f);
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] mouse = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	public static Matrix4f projection;
	
	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		projection = Matrix4f.projection(70.0f, (float)width/(float)height, 0.1f, 1000f);
	}
	
	public void create() {
		if (!GLFW.glfwInit()) {
			System.err.println("***ERROR*** GLFW needs to be initialised before calling Window.create()");
		}
		
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		
		if (window == 0) {
			System.err.println("***ERROR*** Window couldn't be created");
			System.exit(-1);
		}
		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		GL20.glEnable(GL20.GL_DEPTH_TEST);
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (videoMode.width()-width)/2, (videoMode.height() - height)/2);
		GLFW.glfwShowWindow(window);
	}
	
	public void inputs() {
		for (int a=0; a<GLFW.GLFW_KEY_LAST; a++) keys[a] = isKeyDown(a);
		for (int a=0; a<GLFW.GLFW_MOUSE_BUTTON_LAST; a++) mouse[a] = isMouseDown(a);
		GLFW.glfwPollEvents();
	}
	
	public void render() {
		GLFW.glfwSwapBuffers(window);
		GL11.glClearColor(bgc.x, bgc.y, bgc.y, 1f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public boolean isKeyDown(int keyCode) {
		return GLFW.glfwGetKey(window, keyCode) == 1;
	}
	
	public boolean isKeyPressed(int keyCode) {
		return isKeyDown(keyCode) && !keys[keyCode];
	}
	
	public boolean isKeyReleased(int keyCode) {
		return !isKeyDown(keyCode) && keys[keyCode];
	}
	
	public boolean isMouseDown(int mouseButton) {
		return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
	}
	
	public boolean isMousePressed(int mouseButton) {
		return isKeyDown(mouseButton) && !keys[mouseButton];
	}
	
	public boolean isMouseReleased(int mouseButton) {
		return !isKeyDown(mouseButton) && keys[mouseButton];
	}
	
	public double getMouseX() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, buffer, null);
		return buffer.get(0);
	}
	
	public double getMouseY() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, null, buffer);
		return buffer.get(0);
	}
	
	public void setBackgroundColour(float r, float g, float b) {
		bgc = new Vector3f(r, g, b);
	}
}
