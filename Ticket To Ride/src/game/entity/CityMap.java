package game.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class CityMap {
	private List<ArrayList<Track>> map;
	public static HashMap<String, Integer> CITYINDEX;
	private List<ArrayList<Track>> fullMap;
	private ArrayList<Integer> dp;
	private HashSet<Track> lpVisited;

	public CityMap() {
		// TreeSet<String> temp = new TreeSet<String>();
		CITYINDEX = new HashMap<String, Integer>();
		map = new ArrayList<ArrayList<Track>>();
		fullMap = new ArrayList<ArrayList<Track>>();
		dp = new ArrayList<Integer>();
		lpVisited = new HashSet<Track>();
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
	
	public boolean addTrack(String city1, String city2, String player, String colChoice)
	{
		int c1 = CITYINDEX.get(city1);
		int c2 = CITYINDEX.get(city2);
		Track work = null;
		
		ArrayList<Track> i = fullMap.get(c1);
		for(int j = 0; j < i.size(); j++)
		{
			if(i.get(j).getOtherCity(c1) == c2)
			{
				work = i.get(j);
				break;
			}
		}
		
		if(work == null)
			return false;
		
		if(!work.isDoubleTrack())
		{
			fullMap.get(c1).remove(work);
			fullMap.get(c2).remove(work);
			work.occupyTrack(player, null);
			map.get(c1).add(work);
			map.get(c2).add(work);
		}
		else
		{
			work.occupyTrack(player, colChoice);
			if(work.isFilled())
			{
				fullMap.get(c1).remove(work);
				fullMap.get(c2).remove(work);
			}
			else
			{
				map.get(c1).add(work);
				map.get(c2).add(work);
			}
		}
		
		
		return true;
	}
	
	public boolean completedContract(String color, ContractCard check)
	{
		return dfs(CITYINDEX.get(check.getCityTwo()), color, CITYINDEX.get(check.getCityOne()), new ArrayList<Track>());
	}
	
	private boolean dfs(int goal, String color, int curr, List<Track> visited)
	{
		for(Track i: map.get(curr))
		{
			if(!visited.contains(i) && i.containsPlayerCol(color))
			{
				if(i.getOtherCity(curr) == goal)
					return true;
				visited.add(i);
				if(dfs(goal, color, i.getOtherCity(curr), visited))
					return true;
				break;
			}

		}
		return false;
	}
	
	public Track getTrack(String city1, String city2)
	{
		int c1 = CITYINDEX.get(city1);
		int c2 = CITYINDEX.get(city2);
		
		for(Track i: map.get(c1))
		{
			if(i.getOtherCity(c1) == c2)
				return i;
		}
		return null;
	}
	
	
	public Track getTrack(int city1, int city2)
	{	
		for(Track i: map.get(city1))
		{
			if(i.getOtherCity(city1) == city2)
				return i;
		}
		return null;
	}
	
	private int longestPath(int currCity, List<Track> visited, String player)
	{
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		for(Track i : map.get(currCity))
		{
			if(!visited.contains(i) && i.containsPlayerCol(player))
			{
				visited.add(getTrack(currCity, i.getOtherCity(currCity)));
				int length = i.getLength();
				lengths.add(length + longestPath(i.getOtherCity(currCity), new ArrayList<Track>(visited), player));
			}
		}
		if(lengths.isEmpty())
			return -1;
		
		Collections.sort(lengths);
		if(lengths.size() >= 2)
			dp.add(lengths.get(lengths.size() - 1) + lengths.get(lengths.size() - 2));
		return lengths.get(lengths.size() - 1);
	}
	
	/*public String getPlayerLongest()
	{
		
	}*/

}
	