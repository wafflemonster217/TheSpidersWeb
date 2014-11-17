package main;

import java.util.HashMap;
import java.util.HashSet;

public class Bug {
	public HashSet<Integer> neighbors;
	
	public int id;
	
	public Bug(int id) {
		neighbors = new HashSet<Integer>();
		this.id = id;
	}
	
	public void addNeighbor(int neighbor) {
		neighbors.add(neighbor);
	}
}