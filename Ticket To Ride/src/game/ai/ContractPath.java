package game.ai;

import java.util.ArrayList;

import game.entity.CityMap;
import game.entity.ContractCard;
import game.entity.Player;
import game.entity.Track;

public class ContractPath implements Comparable<ContractPath> {
	private ContractCard card;
	private ArrayList<PathEdge> path;	
	public ContractPath(ContractCard card) {
		this.card = card;
	}
	
	public void calculateShortestPath(CityMap map, String player) {
		this.path =  map.shortestPath(card.getCityOne(), card.getCityTwo(), player);
	}
	
	public ContractCard getCard() {
		return card;
	}
	
	public int sumPath() {
		if(path.isEmpty()) return Integer.MAX_VALUE;
		return path.get(path.size()-1).getWeight();
	}
	
	public double ratio() {
		return (double)card.getPoints()/(double)sumPath();
	}
	
	public ArrayList<PathEdge> getPath() {
		return path;
	}

	@Override
	public int compareTo(ContractPath path) {
		if(ratio() != path.ratio())
			return Double.compare(path.ratio(), ratio());
		return Integer.compare(path.getCard().getPoints(), card.getPoints());
	}
}
