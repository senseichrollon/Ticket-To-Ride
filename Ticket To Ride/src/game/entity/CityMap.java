package game.entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CityMap {
	private List<ArrayList<Track>> map;
	public static HashMap<String, Integer> CITYINDEX;
	public static HashMap<Integer,Track> allTracks;
	private List<ArrayList<Track>> fullMap;
	private ArrayList<Integer> dp;
	private HashSet<Track> lpVisited;

	public CityMap() {
		// TreeSet<String> temp = new TreeSet<String>();
		CITYINDEX = new HashMap<String, Integer>();
		map = new ArrayList<ArrayList<Track>>();
		fullMap = new ArrayList<ArrayList<Track>>();
		allTracks = new HashMap<>();;
		try {
			Scanner in = new Scanner(new File("resources/gamedata/cities.txt"));
			int n = in.nextInt();
			for(int i = 0; i < n; i++){
				int number = in.nextInt();
				CITYINDEX.put(in.next(), number);
				map.add(new ArrayList<Track>());
				fullMap.add(new ArrayList<Track>());
			}
		} catch (Exception e) {
			System.out.println("Error reading resources/gamedata/cities.txt");
		}
		
		
		try {
			Scanner in = new Scanner(new File("resources/gamedata/tracks.txt"));
			int n = in.nextInt();
			in.nextLine();
			for(int i = 0; i < n; i++){
				String[] args = in.nextLine().split(" ");
				
				 Track add = new Track(Integer.parseInt(args[0]), 
							  Integer.parseInt(args[1]), 
							  Integer.parseInt(args[2]),
							  Integer.parseInt(args[3]), 
							  args[5].toLowerCase());
		
				if(Boolean.parseBoolean(args[4]))
					add.setTrackColor2(args[6].toLowerCase());
					 
				fullMap.get(add.getCityOne()).add(add);
				fullMap.get(add.getCityTwo()).add(add);
				allTracks.put(Integer.parseInt(args[0]),add);
			}
		} catch (IOException e) {
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
				visited.add(i);
				int length = i.getLength();
				lpVisited.remove(i);
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
	
	public ArrayList<String> getPlayersLongest()
	{
		//GAMESTATE NEEDS A GLOBAL COLOR LIST!!!! PLZ ADD ASAP!!! 
		//get players
		HashMap<String, Integer> log = new HashMap<String, Integer>();
		//String[] players = GameState.PLAYER_COLS;
		//for(String i: players)
		{
			lpVisited = getPlayerTracks("INSERT i");
			ArrayList<Integer> vals = new ArrayList<Integer>();
			while(!lpVisited.isEmpty())
			{
				dp = new ArrayList<Integer>();
				Iterator<Track> iter = lpVisited.iterator();
				Track start = iter.next();
				int temp = longestPath(start.getCityOne(), new ArrayList<Track>(), "INSERT (i)");
				vals.add(Math.max(Collections.max(dp), temp));
			}
			log.put("INSERT i", Collections.max(vals));
		}
		return longestColorFinder(log);
	}
	
	private ArrayList<String> longestColorFinder(HashMap<String, Integer> log)
	{
		int max = Collections.max(log.values());
		Iterator<Integer> iter = log.values().iterator();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		int c = 0;
		while(iter.hasNext())
		{
			if(iter.next() == max)
				indices.add(c);
			c++;
		}
		
		ArrayList<String> out = new ArrayList<String>();
		Iterator<String> iter2 = log.keySet().iterator();
		c= 0;
		while(iter2.hasNext())
		{
			if(indices.contains(c++))
				out.add(iter2.next());
		}
		return out;
	}
	
	public HashSet<Track> getPlayerTracks(String col)
	{
		HashSet<Track> out = new HashSet<Track>();
		for(ArrayList<Track> i : map)
		{
			for(Track j : i)
			{
				if(j.containsPlayerCol(col))
					out.add(j);
			}
		}
		return out;
	}

}
	