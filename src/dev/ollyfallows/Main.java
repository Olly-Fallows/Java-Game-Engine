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
		engine.addGameElement(new Entity(new Vector3f(0f, -0.75f, -4.0f), MeshLoader.loadMeshs("/resources/models/eyeball1/testeye.obj")));
		Mesh[] m = MeshLoader.loadOBJ("/resources/models/eyeball1/testeye.obj", "/resources/models/eyeball1/eyeball.png");
		engine.addGameElement(new Entity(new Vector3f(0f,-0.75f,0f), m));
		engine.loop();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}

