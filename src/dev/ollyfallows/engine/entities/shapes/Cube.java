package dev.ollyfallows.engine.entities.shapes;

import dev.ollyfallows.engine.entities.Entity;
import dev.ollyfallows.engine.graphics.Mesh;
import dev.ollyfallows.engine.graphics.Vertex;
import dev.ollyfallows.engine.maths.Matrix4f;
import dev.ollyfallows.engine.maths.Vector3f;

public class Cube extends Entity{

	public Cube(Vector3f pos, Vector3f rot, Vector3f sca) {
		super(pos, rot, sca, new Mesh(new Vertex[] {
			new Vertex(-0.5f,  0.5f,  0.5f), // 0 - front top left
			new Vertex( 0.5f,  0.5f,  0.5f), // 1 - front top right
			new Vertex( 0.5f, -0.5f,  0.5f), // 2 - front bottom right
			new Vertex(-0.5f, -0.5f,  0.5f), // 3 - front bottom left
			new Vertex(-0.5f,  0.5f, -0.5f), // 4 - back top left
			new Vertex( 0.5f,  0.5f, -0.5f), // 5 - back top right
			new Vertex( 0.5f, -0.5f, -0.5f), // 6 - back bottom right
			new Vertex(-0.5f, -0.5f, -0.5f)  // 7 - back bottom left
		}, new int[] {
				 0, 1, 3, // front
				 3, 2, 1,
				 4, 0, 3, // left
				 3, 7, 4,
				 5, 1, 2, // right
				 2, 6, 5,
				 4, 5, 7, // back
				 7, 6, 5,
				 0, 1, 5, // top
				 5, 4, 0,
				 2, 3, 7, // bottom
				 7, 6, 2
		}).create());
	}
	public Cube(Vector3f pos, Vector3f rot) {
		this(pos, rot, new Vector3f(1,1,1));
	}
	public Cube(Vector3f pos) {
		this(pos, new Vector3f(0,0,30));
	}
	
	
	@Override
	public void midFrame(long delta) {
		rot.y -= 45*((float)delta)/1000;
	}
}
