package dev.ollyfallows.engine.graphics.shaders;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import dev.ollyfallows.engine.entities.Camera;
import dev.ollyfallows.engine.entities.Entity;
import dev.ollyfallows.engine.maths.Matrix4f;
import dev.ollyfallows.engine.maths.Vector2f;
import dev.ollyfallows.engine.maths.Vector3f;

public class ShaderProgram {

    protected final int programId;
    protected int vertexShaderId;
    protected int fragmentShaderId;

    public ShaderProgram() throws Exception {
        programId = GL20.glCreateProgram();
        if (programId == 0) {
            throw new Exception("Could not create Shader");
        }
    }
    
    public ShaderProgram(String vertCode, String fragCode) throws Exception {
        programId = GL20.glCreateProgram();
        if (programId == 0) {
            throw new Exception("Could not create Shader");
        }
        createVertexShader(vertCode);
        createFragmentShader(fragCode);
    }

    public ShaderProgram(String vertCode, String fragCode, boolean flag) throws Exception {
        programId = GL20.glCreateProgram();
        if (programId == 0) {
            throw new Exception("Could not create Shader");
        }
        createVertexShader(vertCode);
        createFragmentShader(fragCode);
        link();
    }

    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderId = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
    }

    protected int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = GL20.glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        GL20.glShaderSource(shaderId, shaderCode);
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + GL20.glGetShaderInfoLog(shaderId, 1024));
        }

        GL20.glAttachShader(programId, shaderId);

        return shaderId;
    }

    public void link() throws Exception {
    	GL20.glLinkProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + GL20.glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0) {
        	GL20.glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
        	GL20.glDetachShader(programId, fragmentShaderId);
        }

        GL20.glValidateProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(programId, 1024));
        }

    }
    
    public int getUniformLocation(String name) {
    	return GL20.glGetUniformLocation(programId, name);
    }
    
    public void setUniform(String name, float value) {
    	GL20.glUniform1f(getUniformLocation(name), value);
    }
    public void setUniform(String name, int value) {
    	GL20.glUniform1i(getUniformLocation(name), value);
    }
    public void setUniform(String name, boolean value) {
    	GL20.glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }
    public void setUniform(String name, Vector2f value) {
    	GL20.glUniform2f(getUniformLocation(name), value.x, value.y);
    }
    public void setUniform(String name, Vector3f value) {
    	GL20.glUniform3f(getUniformLocation(name), value.x, value.y, value.z);
    }
    public void setUniform(String name, Matrix4f value) {
    	FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE*Matrix4f.SIZE);
    	matrix.put(value.getALL());
    	matrix.flip();
    	GL20.glUniformMatrix4fv(getUniformLocation(name), true, matrix);
    }

    public void bind(Camera cam, Entity e) {
    	GL20.glUseProgram(programId);
    }

    public void unbind() {
    	GL20.glUseProgram(0);
    }
    
    public void cleanup() {
        unbind();
        if (programId != 0) {
        	GL20.glDetachShader(programId, vertexShaderId);
        	GL20.glDetachShader(programId, fragmentShaderId);
        	GL20.glDeleteProgram(programId);
        }
    }
}