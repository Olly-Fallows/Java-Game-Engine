package dev.ollyfallows.engine.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.ollyfallows.engine.graphics.Mesh;
import dev.ollyfallows.engine.graphics.MeshData;
import dev.ollyfallows.engine.graphics.Texture;
import dev.ollyfallows.engine.graphics.Vertex;
import dev.ollyfallows.engine.maths.Vector2f;
import dev.ollyfallows.engine.maths.Vector3f;

public class MeshLoader {
	
	private static MeshLoader instance = null;
	
	private HashMap<String, MeshData[]> meshData = new HashMap<String, MeshData[]>();
	private HashMap<String, HashMap<String, Mesh[]>> meshs = new HashMap<String, HashMap<String, Mesh[]>>();
	
	private MeshLoader() {}
	
    public Mesh[] loadOBJ(String path, String textPath) {
    	if (meshs.containsKey(path)) {
    		if (meshs.get(path).containsKey(textPath)) {
    			boolean loaded = true;
    			for (Mesh m : meshs.get(path).get(textPath)) {
	    			if (!m.getTexture().loaded()) {
	    				loaded = false;
	    			}
    			}
    			if (loaded) {
    				return meshs.get(path).get(textPath);
    			}
    		}
    	}
    	MeshData[] data;
    	if (meshData.containsKey(path)) {
    		data = meshData.get(path);
    	}else {
    		ArrayList<MeshData> dataList = new ArrayList<MeshData>();
	    	BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
	        } catch (Exception e) {
	            System.err.println("Error: File not found");
	        }
	        String line;
	        List<Vertex> vertices = new ArrayList<Vertex>();
	        List<Vector2f> textures = new ArrayList<Vector2f>();
	        List<Vector3f> normals = new ArrayList<Vector3f>();
	        List<Integer> indices = new ArrayList<Integer>();
	        try {
	        	String material = null;
	            while ((line=reader.readLine()) != null) {
	                if (line.startsWith("mtllib")) {
	                	
	                } else if (line.startsWith("v ")) {
	                    String[] currentLine = line.split(" ");
	                    Vector3f vertex = new Vector3f((float) Float.valueOf(currentLine[1]),
	                            (float) Float.valueOf(currentLine[2]),
	                            (float) Float.valueOf(currentLine[3]));
	                    Vertex newVertex = new Vertex(vertices.size(), vertex);
	                    vertices.add(newVertex);
	 
	                } else if (line.startsWith("vt ")) {
	                    String[] currentLine = line.split(" ");
	                    Vector2f texture = new Vector2f((float) Float.valueOf(currentLine[1]),
	                            (float) Float.valueOf(currentLine[2]));
	                    textures.add(texture);
	                } else if (line.startsWith("vn ")) {
	                    String[] currentLine = line.split(" ");
	                    Vector3f normal = new Vector3f((float) Float.valueOf(currentLine[1]),
	                            (float) Float.valueOf(currentLine[2]),
	                            (float) Float.valueOf(currentLine[3]));
	                    normals.add(normal);
	                } else if (line.startsWith("f ")) {
		                String[] currentLine = line.split(" ");
		                String[] vertex1 = currentLine[1].split("/");
		                String[] vertex2 = currentLine[2].split("/");
		                String[] vertex3 = currentLine[3].split("/");
		                processVertex(vertex1, vertices, indices);
		                processVertex(vertex2, vertices, indices);
		                processVertex(vertex3, vertices, indices);
	                } else if (line.startsWith("usemtl")) {
	                	if (material != null) {
		                	System.out.println(line);
		                	removeUnusedVertices(vertices);
		        	        Vertex[] verticesArray = new Vertex[vertices.size()];
		        	        float[] texturesArray = new float[vertices.size() * 2];
		        	        float[] normalsArray = new float[vertices.size() * 3];
		        	        float furthest = convertDataToArrays(vertices, textures, normals, verticesArray,
		        	                texturesArray, normalsArray);
		        	        int[] indicesArray = convertIndicesListToArray(indices);
		        	        dataList.add(new MeshData(verticesArray, texturesArray, normalsArray, indicesArray, furthest));
	                	}
	                	material = line.split(" ")[1];
	                } else if (line.startsWith("o")) {
	                	//vertices.removeAll(vertices);
	                	//textures.removeAll(textures);
	                	//normals.removeAll(normals);
	                	//indices.removeAll(indices);
	                	if (material != null) break;
	                	material = null;
	                	System.out.println(line);
	                }
	            }
	            reader.close();
	        } catch (IOException e) {
	            System.err.println("Error reading the file");
	        }
	        
	        data = dataList.toArray(new MeshData[dataList.size()]);
	        
	        meshData.put(path, data);
    	}
    	
		Texture text = TextureLoader.getInstance().loadTexture(textPath);
		ArrayList<Mesh> m = new ArrayList<Mesh>();
		
		for (MeshData md : data) {
			m.add(new Mesh(md.getVertices(), md.getIndices(), md.getNormals(), md.getTextureCoords(), text).create());
		}
		
        return m.toArray(new Mesh[m.size()]);
    }
 
    private void processVertex(String[] vertex, List<Vertex> vertices, List<Integer> indices) {
        int index = Integer.parseInt(vertex[0]) - 1;
        Vertex currentVertex = vertices.get(index);
        int textureIndex = Integer.parseInt(vertex[1]) - 1;
        int normalIndex = Integer.parseInt(vertex[2]) - 1;
        if (!currentVertex.isSet()) {
            currentVertex.setTextureIndex(textureIndex);
            currentVertex.setNormalIndex(normalIndex);
            indices.add(index);
        } else {
            dealWithAlreadyProcessedVertex(currentVertex, textureIndex, normalIndex, indices,
                    vertices);
        }
    }
 
    private int[] convertIndicesListToArray(List<Integer> indices) {
        int[] indicesArray = new int[indices.size()];
        for (int i = 0; i < indicesArray.length; i++) {
            indicesArray[i] = indices.get(i);
        }
        return indicesArray;
    }
 
    private float convertDataToArrays(List<Vertex> vertices, List<Vector2f> textures,
            List<Vector3f> normals, Vertex[] verticesArray, float[] texturesArray,
            float[] normalsArray) {
        float furthestPoint = 0;
        for (int i = 0; i < vertices.size(); i++) {
            Vertex currentVertex = vertices.get(i);
            if (currentVertex.getLength() > furthestPoint) {
                furthestPoint = currentVertex.getLength();
            }
            Vector3f position = currentVertex.getPosition();
            Vector2f textureCoord = textures.get(currentVertex.getTextureIndex());
            Vector3f normalVector = normals.get(currentVertex.getNormalIndex());
            verticesArray[i] = currentVertex;
            texturesArray[i * 2] = textureCoord.x;
            texturesArray[i * 2 + 1] = 1 - textureCoord.y;
            normalsArray[i * 3] = normalVector.x;
            normalsArray[i * 3 + 1] = normalVector.y;
            normalsArray[i * 3 + 2] = normalVector.z;
        }
        return furthestPoint;
    }
 
    private void dealWithAlreadyProcessedVertex(Vertex previousVertex, int newTextureIndex,
            int newNormalIndex, List<Integer> indices, List<Vertex> vertices) {
        if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)) {
            indices.add(previousVertex.getIndex());
        } else {
            Vertex anotherVertex = previousVertex.getDuplicateVertex();
            if (anotherVertex != null) {
                dealWithAlreadyProcessedVertex(anotherVertex, newTextureIndex, newNormalIndex,
                        indices, vertices);
            } else {
                Vertex duplicateVertex = new Vertex(vertices.size(), previousVertex.getPosition());
                duplicateVertex.setTextureIndex(newTextureIndex);
                duplicateVertex.setNormalIndex(newNormalIndex);
                previousVertex.setDuplicateVertex(duplicateVertex);
                vertices.add(duplicateVertex);
                indices.add(duplicateVertex.getIndex());
            }
 
        }
    }
     
    private void removeUnusedVertices(List<Vertex> vertices){
        for(Vertex vertex:vertices){
            if(!vertex.isSet()){
                vertex.setTextureIndex(0);
                vertex.setNormalIndex(0);
            }
        }
    }
    
    public static MeshLoader getInstance() {
    	if (instance == null) {
    		instance = new MeshLoader();
    	}
    	return instance;
    }
}
