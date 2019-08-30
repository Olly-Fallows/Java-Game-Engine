package dev.ollyfallows;

import dev.ollyfallows.engine.GameEngine;
import dev.ollyfallows.engine.entities.shapes.Cube;
import dev.ollyfallows.engine.io.Window;
import dev.ollyfallows.engine.maths.Vector3f;

public class Main {
	
	private Window window;
	private GameEngine engine;
	
	public Main() {
		window = new Window(800, 600, "LWJGL test window");
		window.create();
		engine = new GameEngine(window);
		engine.addGameElement(new Cube(new Vector3f(0f,0f,0f)));
		engine.loop();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
