package game.entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import game.ai.AIPlayer;
import game.ai.PathEdge;
import game.main.GameState;

public class CityMap {
	public static HashMap<String, Integer> CITYINDEX;
	public static HashMap<Integer, String> INDEX_TO_CITY;
	public static HashMap<Integer, Track> allTracks;

	private List<ArrayList<Track>> FULLMAP;
	private ArrayList<Integer> dp;
	private HashSet<Track> lpVisited;
	private List<ArrayList<Track>> map;
	private HashMap<Integer, ArrayList<Track>> loopAlert;
	private int loopCounter;

	public CityMap() {
		CITYINDEX = new HashMap<String, Integer>();
		map = new ArrayList<ArrayList<Track>>();
		allTracks = new HashMap<>();
		INDEX_TO_CITY = new HashMap<>();

		FULLMAP = new ArrayList<ArrayList<Track>>();
		loopAlert = new HashMap<Integer, ArrayList<Track>>();
		dp = new ArrayList<Integer>();

		try {
			Scanner in = new Scanner(new File("resources/gamedata/cities.txt"));
			int n = in.nextInt();
			for (int i = 0; i < n; i++) {
				int number = in.nextInt();
				String city = in.next().replace('_', ' ');
				CITYINDEX.put(city, number);
				INDEX_TO_CITY.put(number, city);
				map.add(new ArrayList<Track>());
				FULLMAP.add(new ArrayList<Track>());
			}
		} catch (Exception e) {
			System.out.println("Error reading resources/gamedata/cities.txt");
		}

		try {
			Scanner in = new Scanner(new File("resources/gamedata/tracks.txt"));
			int n = in.nextInt();
			in.nextLine();
			for (int i = 0; i < n; i++) {
				String[] args = in.nextLine().split(" ");

				Track add = new Track(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
						Integer.parseInt(args[3]), args[5].toLowerCase());

				if (Boolean.parseBoolean(args[4]))
					add.setTrackColor2(args[6].toLowerCase());

				allTracks.put(Integer.parseInt(args[0]), add);

				FULLMAP.get(add.getCityOne()).add(add);
				FULLMAP.get(add.getCityTwo()).add(add);
			}
		} catch (IOException e) {
			System.out.println("Error reading resources/gamedata/tracks.txt");
		}

	}    

	public boolean addTrack(Track work, String player, String colChoice, int side) {
		System.out.println(player + "  fsds");
		int c1 = work.getCityOne();
		int c2 = work.getCityTwo();
		/*
		 * ArrayList<Track> i = FULLMAP.get(c1); for(int j = 0; j < i.size(); j++) {
		 * if(i.get(j).getOtherCity(c1) == c2) { work = i.get(j); break; } }
		 * 
		 * if(work == null) return false;
		 */

		if (!work.isDoubleTrack()) {
			FULLMAP.get(c1).remove(work);
			FULLMAP.get(c2).remove(work);
			boolean out = work.occupyTrack(player, "", side);
			map.get(c1).add(work);
			map.get(c2).add(work);
			return out;
		} else {
			boolean out = work.occupyTrack(player, colChoice, side);
			if (work.isFilled()) {
				FULLMAP.get(c1).remove(work);
				FULLMAP.get(c2).remove(work);
			} else {
				map.get(c1).add(work);
				map.get(c2).add(work);
			}
			return out;
		}
	}

	public boolean completedContract(String color, ContractCard check) {
//		System.out.println(check.getCityOne() + " " + check.getCityTwo());
		return dfs(CITYINDEX.get(check.getCityTwo()), color, CITYINDEX.get(check.getCityOne()), new ArrayList<Track>());

	}

	private boolean dfs(int goal, String color, int curr, List<Track> visited) {
		for (Track i : map.get(curr)) {
			if (!visited.contains(i) && i.containsPlayerCol(color)) {
				if (i.getOtherCity(curr) == goal)
					return true;
				visited.add(i);
				if (dfs(goal, color, i.getOtherCity(curr), visited))
					return true;
			}

		}
		return false;
	}

	public Track getTrack(int id) {
		for (ArrayList<Track> tracks : FULLMAP) {
			for (Track track : tracks) {
				if (track.getID() == id)
					return track;
			}
		}
		return null;
	}

	public Track getTrack(String city1, String city2) {
		int c1 = CITYINDEX.get(city1);
		int c2 = CITYINDEX.get(city2);
		for (Track i : FULLMAP.get(c1))
			if (i.getOtherCity(c1) == c2)
				return i;
		return null;
	}

	public int longestPath(int currCity, List<Track> visited, List<Integer> loopCheck, String player, boolean bfMode) {
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		loopCheck.add(currCity);
		for (Track i : map.get(currCity)) {
			if (loopAlert.containsKey(currCity)) {
				System.out.println("fixing loop for city " + currCity);
				visited.addAll(loopAlert.get(currCity));
				loopAlert.remove(currCity);
			}
			if (!visited.contains(i) && i.containsPlayerCol(player)) {
				visited.add(i);
				// int length = i.getLength();
				if (!bfMode)
					lpVisited.remove(i);
				System.out.println(currCity + " " + i.getOtherCity(currCity));
				if (loopCheck.contains(i.getOtherCity(currCity))) {
					loopCounter++;
					if (loopAlert.containsKey(i.getOtherCity(currCity)))
						loopAlert.get(i.getOtherCity(currCity)).add(i);
					else {
						ArrayList<Track> addIn = new ArrayList<Track>();
						addIn.add(i);
						loopAlert.put(i.getOtherCity(currCity), addIn);
					}
					System.out.println("LOOOOP with track " + i);
					lengths.add(i.getLength());
					continue;
				} else
					lengths.add(i.getLength() + longestPath(i.getOtherCity(currCity), new ArrayList<Track>(visited),
							loopCheck, player, bfMode));
			}
		}

		if (lengths.isEmpty())
			return 0;

		Collections.sort(lengths);
		System.out.println("lengths for city " + currCity + " :" + lengths);
		if (lengths.size() >= 2)
			dp.add(lengths.get(lengths.size() - 1) + lengths.get(lengths.size() - 2));
		return lengths.get(lengths.size() - 1);
	}

	public ArrayList<String> getPlayersLongest() {
		// GAMESTATE NEEDS A GLOBAL COLOR LIST!!!! PLZ ADD ASAP!!!
		// get players
		// HashMap<String, Integer> log = new HashMap<String, Integer>();
		// String[] players = GameState.PLAYER_COLS;
		String[] players = GameState.PLAYER_COLS;
		int[] playerLength = new int[players.length];
		int plc = 0;
		for (String i : players) {
			lpVisited = getPlayerTracks(i);
			ArrayList<Integer> vals = new ArrayList<Integer>();
			while (!lpVisited.isEmpty()) {
				dp.clear();
				Iterator<Track> iter = lpVisited.iterator();

				Track start = iter.next();
				int temp = longestPath(start.getCityOne(), new ArrayList<Track>(), new ArrayList<Integer>(), i, false);
				int segLongest = -1;

				if (!dp.isEmpty())
					segLongest = Math.max(Collections.max(dp), temp);
				else
					segLongest = temp;

				if (loopCounter >= 2) {
					
					vals.add(bruteForce(i, start, segLongest));
					loopCounter = 0;
					loopAlert.clear();
					break;
				} else
					vals.add(segLongest);
			}
			if (!vals.isEmpty())
				playerLength[plc++] = Collections.max(vals);
			else
				playerLength[plc++] = 0;
		}

		ArrayList<String> out = new ArrayList<String>();
		ArrayList<Integer> indices = getLargestPos(playerLength);

		for (int i : indices) {
			out.add(players[i]);
		}

		// out.add(String.valueOf(playerLength[indices.get(0)]));
		return out;
	}

	private int bruteForce(String player, Track firstStart, int firstLength) {
		System.out.println("Start brute force\n");
		lpVisited = getPlayerTracks(player);
		lpVisited.remove(firstStart);
		HashSet<Integer> bfCities = new HashSet<Integer>();
		for (Track i : lpVisited) {
			bfCities.add(i.getCityOne());
			bfCities.add(i.getCityTwo());
		}
		lpVisited.clear();

		int max = firstLength;
		for (int i : bfCities) {
			dp.clear();
			System.out.println("BRUTE FORCE:" + i + "\n");
			int temp = Math.max(longestPath(i, new ArrayList<Track>(), new ArrayList<Integer>(), player, true),
					Collections.max(dp));
			if (temp > max)
				max = temp;
		}
		return max;
	}

	private ArrayList<Integer> getLargestPos(int[] test) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < test.length; i++) {

			if (test[i] > max)
				max = test[i];
		}

		ArrayList<Integer> out = new ArrayList<Integer>();
		for (int i = 0; i < test.length; i++) {
			if (test[i] == max)
				out.add(i);
		}
		return out;
	}

	public HashMap<Track, boolean[]> getPlaceableTracks(Player player) {
		HashMap<Track, boolean[]> ret = new HashMap<>();
		for (ArrayList<Track> tracks : FULLMAP) {
			for (Track track : tracks) {
				boolean[] b = new boolean[2];

				if (player.getCards().hasEnough(track.getTrackColor1(), track.getLength())
						&& track.getPlayerColor1() == null && !track.containsPlayerCol(player.getTrainColor())
						&& player.getTrains() >= track.getLength()) {
					b[0] = true;
				}

				if (track.isDoubleTrack() && player.getCards().hasEnough(track.getTrackColor2(), track.getLength())
						&& track.getPlayerColor2() == null && !track.containsPlayerCol(player.getTrainColor())
						&& player.getTrains() >= track.getLength()) {
					b[1] = true;
				}

				if (b[0] || b[1])
					ret.put(track, b);
			}
		}
		return ret;
	}

	public HashSet<Track> getPlayerTracks(String col) {
		HashSet<Track> out = new HashSet<Track>();
		for (ArrayList<Track> i : map) {
			for (Track j : i) {
				if (j.containsPlayerCol(col))
					out.add(j);
			}
		}
		return out;
	}

	public ArrayList<PathEdge> shortestPath(String city1, String city2, String player) {
		ArrayList<PathEdge> ret = new ArrayList<>();

		int c1 = CITYINDEX.get(city1);
		int c2 = CITYINDEX.get(city2);
		Set<Integer> visited = new HashSet<Integer>();

		PriorityQueue<PathEdge> pq = new PriorityQueue<>();

		for (Track track : map.get(c1)) {
			if (track.containsPlayerCol(player))
				pq.add(new PathEdge(track, 0, true));
		}

		for (Track track : FULLMAP.get(c1)) {
			if (track.containsPlayerCol(player))
				pq.add(new PathEdge(track, 0, true));
			else
				pq.add(new PathEdge(track, 0, false));
		}
		visited.add(c1);
		PathEdge dest = null;
		while (!pq.isEmpty()) {
			PathEdge at = pq.poll();
			int city;
			if (at.getPrevious() == null) {
				city = at.getTrack().getOtherCity(c1);
				at.setCity(city);
			} else {
				city = at.getCity();
			}

			visited.add(city);
			if (city == c2) {
				dest = at;
				break;
			}
			for (Track track : map.get(city)) {
				if (!visited.contains(track.getOtherCity(city)) && track.containsPlayerCol(player)) {
					PathEdge edge = new PathEdge(track, at.getWeight(), true);
					edge.setPrevious(at);
					pq.add(edge);
				}
			}

//			System.out.println(FULLMAP.get(city));
			for (Track track : FULLMAP.get(city)) {
				if (!visited.contains(track.getOtherCity(city))) {
					if (track.containsPlayerCol(player)) {
						PathEdge edge = new PathEdge(track, at.getWeight(), true);
						edge.setPrevious(at);
						pq.add(edge);
					} else {
						PathEdge edge = new PathEdge(track, at.getWeight(), false);
						edge.setPrevious(at);
						pq.add(edge);
					}
				}
			}
		}
		while (dest != null) {
			Track track = dest.getTrack();
			if (!track.containsPlayerCol(player)) {
				ret.add(dest);
			}
			dest = dest.getPrevious();
		}
		Collections.reverse(ret);
		return ret;
	}

	public String printFULLMAP() {
		return FULLMAP.toString();
	}

	public String printOGMap() {

		return map.toString();
	}

	// TESTING ONLY, REMOVE ONCE DONE!!!
	public ArrayList<Integer> testDP() {
		return dp;
	}

	public List<ArrayList<Track>> getFullMap() {
		return FULLMAP;
	}

	public List<ArrayList<Track>> getMap() {
		return map;
	}

}