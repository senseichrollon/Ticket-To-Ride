package game.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class CityMap {
	private List<ArrayList<Track>> map;
	public static HashMap<String, Integer> INDEX;

	public CityMap() {
		// TreeSet<String> temp = new TreeSet<String>();
		INDEX = new HashMap<String, Integer>();
		map = new ArrayList<ArrayList<Track>>();
		try {
			Scanner in = new Scanner(new File("tickets.txt"));
			while (in.hasNextLine()) {

			}
		} catch (Exception e) {
		}
	}

}
