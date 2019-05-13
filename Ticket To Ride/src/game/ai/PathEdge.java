package game.ai;

import game.entity.CityMap;
import game.entity.Player;
import game.entity.Track;

public class PathEdge implements Comparable<PathEdge>{
	private Track track;
	private int weight;
	private int city;
	
	private PathEdge previous;
	
	public PathEdge(Track track, int weight, boolean claimed) {
		this.track = track;
		this.weight = weight;
		if(!claimed)
			this.weight += track.getLength();
		previous = null;
	}
	
	public Track getTrack() { 
		return track;
	}
	
	public int getWeight() {
		return weight;
	}
	
	
	public PathEdge getPrevious() {
		return previous;
	}
	
	public void setPrevious(PathEdge path) {
		this.previous = path;
		setCity(track.getOtherCity(path.getCity()));
	}
	
	public int getCity() {
		return city;
	}
	
	public void setCity(int city) {
		this.city = city;
	}
	
	@Override
	public int compareTo(PathEdge e) {
		return Integer.compare(weight, e.getWeight());
	}
	
	public String toString() {
		return CityMap.INDEX_TO_CITY.get(city) + " " + weight;
	}
}
