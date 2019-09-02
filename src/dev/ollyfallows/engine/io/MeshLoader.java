package dev.ollyfallows.engine.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dev.ollyfallows.engine.graphics.Mesh;
import dev.ollyfallows.engine.graphics.MeshData;
import dev.ollyfallows.engine.graphics.Texture;
import dev.ollyfallows.engine.graphics.Vertex;
import dev.ollyfallows.engine.graphics.obj.Obj;
import dev.ollyfallows.engine.graphics.obj.ObjReader;
import dev.ollyfallows.engine.maths.Vector2f;
import dev.ollyfallows.engine.maths.Vector3f;

public class MeshLoader {

	public static Mesh[] loadMeshs(String path) {
		try {
			String root = path.substring(0, path.length()-new File(MeshLoader.class.getResource(path).toURI()).getName().length());
			ArrayList<Mesh> meshs = new ArrayList<Mesh>();
			Obj obj = ObjReader.read(MeshLoader.class.getResourceAsStream(path));
			
			/*ArrayList<Mtl> mtls = new ArrayList<Mtl>();
			for (String mtlFileName : obj.getMtlFileNames()) {
				System.out.println(root+"/"+mtlFileName);
				mtls.addAll(MtlReader.read(MeshLoader.class.getResourceAsStream(root+"/"+mtlFileName)));
			}
			
			Map<String, Obj> materialGroups = ObjSplitting.splitByMaterialGroups(obj);
			for (Entry<String, Obj> entry : materialGroups.entrySet()) {
				String materialName = entry.getKey();
				Obj materialGroup = entry.getValue();
				
				Mtl mtl = null;
				
				for (Mtl m : mtls) {
		            if (m.getName().equals(materialName)) {
		                mtl = m;
		            }
		        }
				if (mtl != null) {
					System.out.println(mtl.getMapD());
					if (mtl.getMapD() != null) {
						meshs.add(new Mesh(materialGroup, TextureLoader.getInstance().loadTexture(root+"/"+mtl.getMapD())).create());
					}
				}
			}*/
			
			meshs.add(new Mesh(obj, TextureLoader.getInstance().loadTexture(root+"/eyeball.png")).create());
			
			Mesh[] m = meshs.toArray(new Mesh[meshs.size()]);
			return m;
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}
	
    public static Mesh[] loadOBJ(String path, String textPath) {
    	MeshData[] data;
		ArrayList<MeshData> dataList = new ArrayList<MeshData>();
    	BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(MeshLoader.class.getResourceAsStream(path)));
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
                if (line.startsWith("v ")) {
                    String[] currentLine = line.split(" ");
                    Vector3f vertex = new Vector3f((float) Float.valueOf(currentLine[1]),
                            (float) Float.valueOf(currentLine[2]),
                            (float) Float.valueOf(currentLine[3]));
                    Vertex newVertex = new Vertex(vertex);
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
                }
            }
            reader.close();

        	removeUnusedVertices(vertices);
	        Vertex[] verticesArray = new Vertex[vertices.size()];
	        float[] texturesArray = new float[vertices.size() * 2];
	        float[] normalsArray = new float[vertices.size() * 3];
	        float furthest = convertDataToArrays(vertices, textures, normals, verticesArray,
	                texturesArray, normalsArray);
	        int[] indicesArray = convertIndicesListToArray(indices);
	        dataList.add(new MeshData(verticesArray, texturesArray, normalsArray, indicesArray, furthest));
            
        } catch (IOException e) {
            System.err.println("Error reading the file");
        }
        
        data = dataList.toArray(new MeshData[dataList.size()]);
           	
		Texture text = TextureLoader.getInstance().loadTexture(textPath);
		ArrayList<Mesh> m = new ArrayList<Mesh>();
		
		for (MeshData md : data) {
			m.add(new Mesh(md.getVertices(), md.getIndices(), md.getNormals(), md.getTextureCoords(), text).create());
		}
		return null;
	}
    private static void processVertex(String[] vertex, List<Vertex> vertices, List<Integer> indices) {
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
    private static int[] convertIndicesListToArray(List<Integer> indices) {
        int[] indicesArray = new int[indices.size()];
        for (int i = 0; i < indicesArray.length; i++) {
            indicesArray[i] = indices.get(i);
        }
        return indicesArray;
    }
    private static float convertDataToArrays(List<Vertex> vertices, List<Vector2f> textures,
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
    private static void dealWithAlreadyProcessedVertex(Vertex previousVertex, int newTextureIndex,
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
    private static void removeUnusedVertices(List<Vertex> vertices){
        for(Vertex vertex:vertices){
            if(!vertex.isSet()){
                vertex.setTextureIndex(0);
                vertex.setNormalIndex(0);
            }
        }
    }
}
