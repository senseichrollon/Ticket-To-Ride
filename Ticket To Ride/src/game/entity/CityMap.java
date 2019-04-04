package game.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class CityMap {
	private List<ArrayList<Track>> map;
	public static HashMap<String, Integer> CITYINDEX;
	private List<ArrayList<Track>> fullMap;
	private ArrayList<Integer> dp;
	private ArrayList<Track> lpVisited;
	private ArrayList<Track> gcVisited;

	public CityMap() {
		// TreeSet<String> temp = new TreeSet<String>();
		CITYINDEX = new HashMap<String, Integer>();
		map = new ArrayList<ArrayList<Track>>();
		fullMap = new ArrayList<ArrayList<Track>>();
		try {
			Scanner in = new Scanner(new File("resources/gamedata/cities.txt"));
			for(int i = 0; i < in.nextInt(); i++){
				String city = in.next();
				int num = in.nextInt();
				CITYINDEX.put(num, city);
			}
		} catch (Exception e) {
			System.out.println("Error reading resources/gamedata/cities.txt");
		}
		
		try {
			Scanner in = new Scanner(new File("resources/gamedata/tracks.txt"));
			for(int i = 0; i < in.nextInt(); i++){
				String[] args = in.nextLine().split(" ");
				
				//Track add = new Track(Integer.parseInt(args[0]), CITYINDEX.get(Integer.parseInt(args[1])), CITYINDEX.get(Integer.parseInt(args[2])), Integer.parseInt(args[3]), args[5]);
				if(Boolean.parseBoolean(args[4]))
					add.setTrackColor2(args[6]);
				
			}
		} catch (Exception e) {
			System.out.println("Error reading resources/gamedata/tracks.txt");
		}
	}

}
	