package dev.ollyfallows.engine;

import java.util.ArrayList;

import dev.ollyfallows.engine.entities.Camera;
import dev.ollyfallows.engine.entities.Entity;
import dev.ollyfallows.engine.entities.GameElement;
import dev.ollyfallows.engine.graphics.Renderer;
import dev.ollyfallows.engine.io.Window;
import dev.ollyfallows.engine.maths.Vector3f;

public class GameEngine {
	
	private Window window;
	private ArrayList<GameElement> gameElements = new ArrayList<GameElement>();
	private static Renderer renderer = new Renderer();
	
	public Camera cam = new Camera(new Vector3f(0,0,3), new Vector3f(0,0,0));
	
	public GameEngine(Window window) {
		this.window = window;
	}
	
	public void loop() {
		long lastLoop = System.currentTimeMillis();
		
		while (!window.shouldClose()) {
			try {
				long delta = System.currentTimeMillis()-lastLoop;
				lastLoop = System.currentTimeMillis();
				//System.out.println(1000/delta);
				// Update pre frame
				for (GameElement ele : gameElements) {
					ele.preFrame(delta);
				}
				// Get inputs
				window.inputs();
				// Update mid frame
				for (GameElement ele : gameElements) {
					ele.midFrame(delta);
				}
				// Render changes
				for (GameElement ele : gameElements) {
					if (ele instanceof Entity) {
						((Entity)ele).render(cam, renderer);
					}
				}
				window.render();
				// Update post frame
				for (GameElement ele : gameElements) {
					ele.postFrame(delta);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addGameElement(GameElement ele) {
		gameElements.add(ele);
	}
}
