package dev.ollyfallows;

import dev.ollyfallows.engine.GameEngine;
import dev.ollyfallows.engine.entities.Entity;
import dev.ollyfallows.engine.io.MeshLoader;
import dev.ollyfallows.engine.io.Window;
import dev.ollyfallows.engine.maths.Vector3f;

public class Main {
	
	private Window window;
	private GameEngine engine;
	
	public Main() {
		window = new Window(800, 600, "LWJGL test window");
		window.create();
		engine = new GameEngine(window);
		engine.addGameElement(new Entity(new Vector3f(0f, -0.75f, -4.0f), MeshLoader.loadMeshs("/resources/models/eyeball1/testeye2.obj")));
		engine.loop();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}

