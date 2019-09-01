package dev.ollyfallows.engine.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import dev.ollyfallows.engine.graphics.Mesh;
import dev.ollyfallows.engine.graphics.obj.Mtl;
import dev.ollyfallows.engine.graphics.obj.MtlReader;
import dev.ollyfallows.engine.graphics.obj.Obj;
import dev.ollyfallows.engine.graphics.obj.ObjReader;
import dev.ollyfallows.engine.graphics.obj.ObjSplitting;

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
}
