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
				CITYINDEX.put(in.next(), in.nextInt());
				map.add(new ArrayList<Track>());
				fullMap.add(new ArrayList<Track>());
			}
		} catch (Exception e) {
			System.out.println("Error reading resources/gamedata/cities.txt");
		}
		
		
		try {
			Scanner in = new Scanner(new File("resources/gamedata/tracks.txt"));
			for(int i = 0; i < in.nextInt(); i++){
				String[] args = in.nextLine().split(" ");
				
				 Track add = new Track(Integer.parseInt(args[0]), 
							  Integer.parseInt(args[1]), 
							  Integer.parseInt(args[2]),
							  Integer.parseInt(args[3]), 
							  args[5]);
		
				if(Boolean.parseBoolean(args[4]))
					add.setTrackColor2(args[6]);
					 
				fullMap.get(add.getCityOne()).add(add);
				fullMap.get(add.getCityTwo()).add(add);
			}
		} catch (Exception e) {
			System.out.println("Error reading resources/gamedata/tracks.txt");
		}
	}
	
	public boolean addTrack(String city1, String city2, String player)
	{
		int c1 = CITYINDEX.get(city1);
		int c2 = CITYINDEX.get(city2);
		Track work = null;
		
		ArrayList<Track> i = fullMap.get(c1);
		for(int j = 0; j < i.size(); j++)
		{
			if(i.get(j).getOtherCity(c1) == c2)
			{
				work = i.remove(j);
				break;
			}
		}
		
		if(work == null)
			return false;
		
		work.setPlayerColor1(player);
		
		i = fullMap.get(c2);
		for(int j = 0; j < i.size(); j++)
		{
			if(i.get(j).equals(work))
			{
				i.remove(j);
				break;
			}
		}
		map.get(c1).add(work);
		map.get(c2).add(work);
		
		return true;
	}
	
	/*public boolean completedContract(String color, ContractCard check)
	{
		Scanner temp = new Scanner(new File("resources/gamedata/tracks.txt"));
		int max = 
		while(gcVisited.size() != )
	}*/
	
	private boolean dfs(int goal, int curr, List<Track> visited)
	{
		for(Track i: map.get(curr))
		{
			for(Track j: visited)
			{
				if(!i.equals(j))
				{
					if(i.getOtherCity(curr) == goal)
						return true;
					visited.add(i);
					gcVisited.add(i);
					if(dfs(goal, i.getOtherCity(curr), visited))
						return true;
					break;
				}
			}
		}
		return false;
	}

}
	