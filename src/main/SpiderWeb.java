package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class SpiderWeb {
	public static final String FILENAME = "spiderMaze.txt";
	public int n;
	public HashSet<Bug> web1;

	public String[][] directions;

	public Queue<Integer> Q;

	public Stack<Integer> solution;

	// web that contains every 3 bug in straight line as a node
	public HashSet<Bug> web2;
	private HashSet<Integer> hasAdded;
	private HashSet<Integer> hasAdded2;

	private Bug getBug2(int id) {
		for (Bug b : web2)
			if (b.id == id)
				return b;
		return null;
	}

	private HashSet<Bug> getBugSetFromIntegerSet(HashSet<Integer> in) {
		HashSet result = new HashSet<Bug>();
		for (int i : in)
			for (Bug b : web1)
				if (b.id == i)
					result.add(b);
		return result;

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
		web2 = new HashSet<Bug>();
		hasAdded2 = new HashSet<Integer>();
		for (Bug a : web1)
			for (Bug b : getBugSetFromIntegerSet(a.neighbors))
				for (Bug c : getBugSetFromIntegerSet(b.neighbors))
					for (Bug d : getBugSetFromIntegerSet(c.neighbors))
						if (directions[a.id][b.id]
								.equals(directions[b.id][c.id])
								&& directions[b.id][c.id]
										.equals(directions[c.id][d.id])) {
							if (!hasAdded2.contains(a.id)) {
								Bug x = new Bug(a.id);
								x.addNeighbor(d.id);
								web2.add(x);
								hasAdded2.add(a.id);
							} else {
								for (Bug x : web2)
									if (x.id == a.id)
										x.addNeighbor(d.id);
							}
							if (!hasAdded2.contains(d.id)) {
								Bug x = new Bug(d.id);
								x.addNeighbor(a.id);
								web2.add(x);
								hasAdded2.add(d.id);
							} else {
								for (Bug x : web2)
									if (x.id == d.id)
										x.addNeighbor(a.id);
							}
						}
	}

	public void printDirections() {
		for (int row = 0; row < n + 2; row++) {
			for (int col = 0; col < n + 2; col++)
				System.out.print(directions[row][col] + " ");
			System.out.println();
		}
	}

	public void BFS() {
		Q = new LinkedList<Integer>();
		solution = new Stack<Integer>();

		Q.add(0);
		while (Q.size() != 0) {
			int u = Q.remove();
			for (int v : getBug2(u).neighbors)
				if (!getBug2(v).visited) {
					getBug2(v).visited = true;
					getBug2(v).parent = u;
					Q.add(v);
				}
		}
	}

	public void traceBack() {
		int i = n + 1;
		solution.push(i);
		while (i != 0) {
			solution.push(getBug2(i).parent);
			i = getBug2(i).parent;
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
