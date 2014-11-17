package main;

import java.util.HashSet;

public class Bug {
	public HashSet<Integer> neighbors;
	
	public int id;
	
	public int parent;
	
	boolean visited = false;
	
	public Bug(int id) {
		neighbors = new HashSet<Integer>();
		this.id = id;
	}
	
	public void addNeighbor(int neighbor) {
		neighbors.add(neighbor);
	}
}