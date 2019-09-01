package dev.ollyfallows.engine.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Utils {
	
	private static Utils instance = null;
	
	private HashMap<String, String> resources = new HashMap<String, String>();
	
	public String loadResourceString(String path) {
		if (resources.containsKey(path)) {
			return resources.get(path);
		}
		return loadResourceString(path, false);
	}
	public String loadResourceString(String path, boolean flag) {
		BufferedReader reader;
		StringBuilder string = new StringBuilder();
		try { 
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
			String line;
			while ((line = reader.readLine()) != null) {
				string.append(line).append("\n");
			}
		}catch(Exception e) {
			System.err.println(path);
			e.printStackTrace();
		}
		return string.toString();
	}
	
	
	
	public static Utils getInstance() {
		if (instance == null) {
			instance = new Utils();
		}
		return instance;
	}
}
