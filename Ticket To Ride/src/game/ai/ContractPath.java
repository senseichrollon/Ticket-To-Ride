package game.ai;

import java.util.ArrayList;

import game.entity.CityMap;
import game.entity.ContractCard;
import game.entity.Player;

public class ContractPath implements Comparable<ContractPath> {
	private ContractCard card;
	private ArrayList<PathEdge> path;	
	public ContractPath(ContractCard card) {
		this.card = card;
	}
	
	public void calculateShortestPath(CityMap map, Player player) {
		this.path =  map.shortestPath(card.getCityOne(), card.getCityTwo(), player.getTrainColor());
	}
	
	public int sumPath() {
		if(path.isEmpty()) return Integer.MAX_VALUE;
		return path.get(path.size()-1).getWeight();
	}
	
	public double ratio() {
		return (double)card.getPoints()/sumPath();
	}

	@Override
	public int compareTo(ContractPath path) {
		return Double.compare(path.ratio(), ratio());
	}
}
