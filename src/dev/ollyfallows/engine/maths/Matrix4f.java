package dev.ollyfallows.engine.maths;

public class Matrix4f {
	
	public static final int SIZE = 4;
	private float[] elements = new float[SIZE*SIZE];
	
	public static Matrix4f identity() {
		Matrix4f result = new Matrix4f();
		for (int a=0; a<SIZE; a++) {
			for (int b=0; b<SIZE; b++) {
				result.setElement(a, b, 0f);
			}
		}
		result.setElement(0, 0, 1.0f);
		result.setElement(1, 1, 1.0f);
		result.setElement(2, 2, 1.0f);
		result.setElement(3, 3, 1.0f);
		return result;
	}
	
	public static Matrix4f translation(Vector3f trans) {
		Matrix4f result = identity();
		result.setElement(3, 0, trans.x);
		result.setElement(3, 1, trans.y);
		result.setElement(3, 2, trans.z);
		return result;
	}
	
	public static Matrix4f rotationX(float angle) {
		Matrix4f result = identity();

		float sin = (float)Math.sin(Math.toRadians(angle));
		float cos = (float)Math.cos(Math.toRadians(angle));
		
		result.setElement(1, 1, cos);
		result.setElement(1, 2, -sin);
		result.setElement(2, 1, sin);
		result.setElement(2, 2, cos);
		
		return result;
	}
	
	public static Matrix4f rotationY(float angle) {
		Matrix4f result = identity();

		float sin = (float)Math.sin(Math.toRadians(angle));
		float cos = (float)Math.cos(Math.toRadians(angle));
		
		result.setElement(0, 0, cos);
		result.setElement(0, 2, sin);
		result.setElement(2, 0, -sin);
		result.setElement(2, 2, cos);
		
		return result;
	}
	
	public static Matrix4f rotationZ(float angle) {
		Matrix4f result = identity();

		float sin = (float)Math.sin(Math.toRadians(angle));
		float cos = (float)Math.cos(Math.toRadians(angle));
		
		result.setElement(0, 0, cos);
		result.setElement(0, 1, -sin);
		result.setElement(1, 0, sin);
		result.setElement(1, 1, cos);
		
		return result;
	}
	
	public static Matrix4f scalar(Vector3f scale) {
		Matrix4f result = identity();
		result.setElement(0, 0, scale.x);
		result.setElement(1, 1, scale.y);
		result.setElement(2, 2, scale.z);
		return result;
	}
	
	public static Matrix4f transform(Vector3f pos, Vector3f rot, Vector3f sca) {
		Matrix4f rotation = multiply(rotationX(rot.x), multiply(rotationY(rot.y), rotationZ(rot.z)));
		return multiply(rotation, multiply(translation(pos), scalar(sca)));
	}
	
	public static Matrix4f projection(float fov, float aspect, float near, float far) {
		Matrix4f result = Matrix4f.identity();
		
		float tanFov = (float)Math.tan(Math.toRadians(fov/2));
		float range = far - near;
		
		result.setElement(0, 0, 1.0f/(aspect*tanFov));
		result.setElement(1, 1, 1.0f/tanFov);
		result.setElement(2, 2, -((far+near)/range));
		result.setElement(2, 3, -1.0f);
		result.setElement(3, 2, -((2.0f*far*near)/range));
		result.setElement(3, 3, 0.0f);
		
		return result;
	}
	
	public static Matrix4f view(Vector3f pos, Vector3f rot) {
		Matrix4f rotation = multiply(rotationX(rot.x), multiply(rotationY(rot.y), rotationZ(rot.z)));
		return multiply(translation(pos.invert()), rotation);
	}
	
	public static Matrix4f multiply(Matrix4f m1, Matrix4f m2) {
		Matrix4f result = Matrix4f.identity();
		
		for (int a=0; a<SIZE; a++) {
			for (int b=0; b<SIZE; b++) {
				result.setElement(a, b, m1.getElement(a, 0)*m2.getElement(0, b) +
										m1.getElement(a, 1)*m2.getElement(1, b) +
										m1.getElement(a, 2)*m2.getElement(2, b) +
										m1.getElement(a, 3)*m2.getElement(3, b));
			}
		}
		
		return result;
	}
	
	public float getElement(int x, int y) {
		return elements[y*SIZE+x];
	}
	
	public void setElement(int x, int y, float value) {
		elements[y*SIZE+x] = value;
	}
	
	public float[] getALL() {
		return elements;
	}
}
