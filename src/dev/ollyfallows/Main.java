package dev.ollyfallows;

import dev.ollyfallows.engine.GameEngine;
import dev.ollyfallows.engine.entities.Entity;
import dev.ollyfallows.engine.graphics.Mesh;
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
		engine.addGameElement(new Entity(new Vector3f(3f, -0.75f, -8.0f), MeshLoader.loadMeshs("/resources/models/stress test doll/test_stressdoll1.obj")));
		Mesh[] m = MeshLoader.loadOBJ("/resources/models/stress test doll/test_stressdoll1.obj", "/resources/models/eyeball1/eyeball.png");
		engine.addGameElement(new Entity(new Vector3f(-3f,-0.75f,-8f), m));
		engine.loop();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}

