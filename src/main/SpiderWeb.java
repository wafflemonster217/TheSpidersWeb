package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class SpiderWeb {
	public static final String FILENAME = "spiderMaze.txt";
	public int n;
	public HashSet<Bug> web1;

	public String[][] directions;

	// web that contains every 3 bug in straight line as a node
	public HashSet<Bug> web2;
	private HashSet<Integer> hasAdded;
	
	private boolean isAdjacent(int row, int col) {
		for (Bug b : web1)
			if (b.id == row)
				if (b.neighbors.contains(col))
					return true;
		return false;
	}
	
	private String oppositeCompassDirection(String direction) {
		String result = null;
		switch (direction) {
		case "N":
			result = "S";
			break;
		case "E":
			result = "W";
			break;
		case "S":
			result = "N";
			break;
		case "W":
			result = "E";
			break;
		case "NW":
			result = "SE";
			break;
		case "NE":
			result = "SW";
			break;
		case "SE":
			result = "NW";
			break;
		case "SW":
			result = "NE";
			break;
			default:
				System.out.println("Invalid input into Compass Function");
				break;
		}
		return result;
	}

	private void loadFile() throws FileNotFoundException {
		
		Scanner reader = new Scanner(new File(FILENAME));
		
		n = Integer.parseInt(reader.nextLine());
		
		directions = new String[n + 2][n + 2];
		
		for (int row = 0; row < n + 2; row++)
			for (int col = 0; col < n + 2; col++)
				directions[row][col] = "X";
		
		while (reader.hasNextLine()) {

			String[] line = reader.nextLine().split(" ");

			int currentBug = Integer.parseInt(line[0]);
			int adjacentBug = Integer.parseInt(line[1]);
			String dir = line[2];

			if (!hasAdded.contains(currentBug)) {
				Bug b = new Bug(currentBug);
				b.addNeighbor(adjacentBug);
				web1.add(b);
				directions[currentBug][adjacentBug] = dir;
				hasAdded.add(currentBug);
			} else {
				for (Bug b : web1)
					if (b.id == currentBug) {
						b.addNeighbor(adjacentBug);
						directions[currentBug][adjacentBug] = dir;
					}
			}
			if (!hasAdded.contains(adjacentBug)) {
				Bug b = new Bug(adjacentBug);
				b.addNeighbor(currentBug);
				web1.add(b);
				directions[adjacentBug][currentBug] = oppositeCompassDirection(dir);
				hasAdded.add(adjacentBug);
			} else {
				for (Bug b : web1)
					if (b.id == adjacentBug) {
						b.addNeighbor(currentBug);
						directions[adjacentBug][currentBug] = oppositeCompassDirection(dir);
					}
			}
		}
		reader.close();
	}

	public void buildWeb2() {
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 11; col++)
				if (row < n && col < n)
					if (directions[row][col] != "X")
						if (directions[row][col].equals(directions[row + 1][col + 1]) && directions[row + 1][col + 1].equals(directions[row + 2][col + 2]))
//							for (Bug b : web1)
//								if (b.id == row)
//									if (b.neighbors.contains((row + 3) % n))
										System.out.println("found possible path from " + row + " to " + row + 3);
		}
	}
	
	public void printDirections() {
		for (int row = 0; row < n + 2; row++) {
			for (int col = 0; col < n + 2; col++)
				System.out.print(directions[row][col] + " ");
			System.out.println();
		}
	}

	public SpiderWeb() {
		web1 = new HashSet<Bug>();
		hasAdded = new HashSet<Integer>();
		
		try {
			loadFile();
		} catch (FileNotFoundException e) {
			System.out.println("The file " + FILENAME + " could not be loaded.");
		}
	}
}
