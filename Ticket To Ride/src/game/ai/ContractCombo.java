package game.ai;

import java.util.ArrayList;
import java.util.HashSet;

import game.entity.ContractCard;
import game.entity.Track;

public class ContractCombo implements Comparable<ContractCombo> {
	public static int TRAIN_LIM = 45;
	public static String PLAYER_COL = "";
	
	private ArrayList<ContractPath> combo;
	
	public ContractCombo() {
		combo = new ArrayList<ContractPath>();
	}
	
	public void addContractCard(ContractPath path) {
		combo.add(path);
	}
	
	private double ratio() {
		return (double)(pointTotal())/(pathTotal());
	}
	
	public int pathTotal() {
		int total = 0;
		HashSet<Track> track = new HashSet<>();
		for(ContractPath path : combo) {
			for(PathEdge e : path.getPath()) {
				if(!track.contains(e.getTrack()) && !e.getTrack().containsPlayerCol(PLAYER_COL)) {
					total += e.getTrack().getLength();
					track.add(e.getTrack());
				}
			}
		}
		return total;
	}
	
	public int pointTotal() {
		int total = 0;
		HashSet<Track> track = new HashSet<>();
		for(ContractPath path : combo) {
			for(PathEdge e : path.getPath()) {
				if(!track.contains(e.getTrack()))
					total += e.getTrackPoints();
			}
			total += path.getCard().getPoints();
		}
		return total;
	}
	
	public ArrayList<ContractCard> cardList() {
		ArrayList<ContractCard> list = new ArrayList<>();
		for(ContractPath path : combo) {
			list.add(path.getCard());
		}
		return list;
	}

	@Override
	public int compareTo(ContractCombo o) {
		int total = 0;
		for(ContractPath path : combo) {
			total += path.sumPath();
		}
		int shortEnough = Boolean.compare(pathTotal() <= TRAIN_LIM, o.pathTotal() <= TRAIN_LIM);
		if(shortEnough != 0) {
			return shortEnough;
		}
		int ratio = Double.compare(ratio(), o.ratio());
		if(ratio != 0) {
			return ratio;
		}
		return Integer.compare(pointTotal(), o.pointTotal());
	}
}
