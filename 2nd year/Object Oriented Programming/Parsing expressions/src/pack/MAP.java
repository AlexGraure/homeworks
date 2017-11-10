package pack;

import java.util.HashMap;
/**
 * 
 * 	@author Graure Marius-Alexandru
 * 	Clasa ce retine 4 HashMaps 
 */
public class MAP {
	/**
	 * HashMap de int
	 */
	private HashMap<String, Integer> intMap;
	/**
	 * HashMap de double
	 */
	private HashMap<String, Double> doubleMap;
	/**
	 * HashMap de string
	 */
	private HashMap<String, String> stringMap;
	/**
	 * HashMap de string
	 */
	private HashMap<String, String> Map;
	/**
	 * Metoda ce instantiaza cele 4 HashMaps
	 */
	public MAP(){
		this.intMap = new HashMap<>();
		this.doubleMap = new HashMap<>();
		this.stringMap = new HashMap<>();
		this.Map = new HashMap<>();
	}
	
	public HashMap<String, Integer> getIntMap() {
		return intMap;
	}

	public HashMap<String, Double> getDoubleMap() {
		return doubleMap;
	}

	public HashMap<String, String> getStringMap() {
		return stringMap;
	}

	public HashMap<String, String> getMap() {
		return Map;
	}
}
