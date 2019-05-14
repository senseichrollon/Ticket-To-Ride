package game.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import game.entity.CardNode;
import game.entity.ContractCard;
import game.entity.Player;
import game.entity.Track;
import game.main.GameState;

public class AIPlayer extends Player{
	private GameState game;
	public static boolean print = false;
	
	public AIPlayer(String n, String c, GameState game) {
		super(n, c);
		this.game = game;
	}
	
	public void startGameMove() {
		selectContracts(5,3);
	}
	
	public void makeMove() {
		ArrayList<ContractPath> contractList = new ArrayList<>();
		for(ContractCard card : contracts) {
			if(card.isComplete())
				continue;
			ContractPath path = new ContractPath(card);
			path.calculateShortestPath(game.getBoard(), trainColor);
			if(!(path.sumPath() == Integer.MAX_VALUE))
				contractList.add(path);
		}
		HashMap<Track, boolean[]> tracks = game.getPlacableTracks();
		
		if(contractList.isEmpty() && trains > 10 && game.getDeck().canDrawContracts()) {
			selectContracts(3,1);
		} else if(contractList.isEmpty() && trains <= 10) { 
			Track best = null;
			int len = Integer.MIN_VALUE;
			int close = Integer.MAX_VALUE;
			List<ArrayList<Track>> map = game.getBoard().getFullMap();
			for(ArrayList<Track> t : map) {
				for(Track track : t) {
					if(track.getLength() > len) {
						len = track.getLength();
						best = track;
						close = getCardDistance(track);
					} else if(track.getLength() == len) {
						if(getCardDistance(track) < close) {
							len = track.getLength();
							best = track;
							close = getCardDistance(track);
						}
					}
				}
			}

			
			if(tracks.containsKey(best)) {
				placeTrack(best, tracks.get(best));
			} else {
				drawTrainCards(best);
			}
		} else {
			ContractPath opt = Collections.min(contractList);
			Track track = null;
			int close = Integer.MAX_VALUE;
			for(PathEdge e : opt.getPath()) {
				int cl = getCardDistance(e.getTrack());
				if(cl < close && (track == null || e.getTrack().getLength() > track.getLength())) {
					track = e.getTrack();
					close = cl;
				}
			}
			if(track == null) {
				print = true;
				opt.calculateShortestPath(game.getBoard(), trainColor);
				track = opt.getPath().get(0).getTrack();
			}
			if(tracks.containsKey(track)) {
				placeTrack(track, tracks.get(track));
			} else {
				drawTrainCards(track);
			}
		}
	}
	
	private void placeTrack(Track track, boolean[] b) {
		String color1 = track.getTrackColor1();
		String color2 = track.getTrackColor2();
		if(color1.equals("gray")) {
			color1 = cards.getMax().getColor();
		}
		
		if(track.isDoubleTrack() && color2.equals("gray")) {
			color2 = cards.getMax().getColor();
		}
		
		System.out.println(color1 + " " + color2);
		
		if(!track.isDoubleTrack()) {
			int cnt = Math.min(track.getLength(), cards.getCard(color1).getCount());
			int wildCnt = track.getLength()-cnt;
			game.placeTrack(track, color1, cnt, wildCnt, false);
		} else {
			int cnt = Math.min(track.getLength(), cards.getCard(color1).getCount());
			int wildCnt = track.getLength()-cnt;
			
			int cnt2 = Math.min(track.getLength(), cards.getCard(color2).getCount());
			int wildCnt2 = track.getLength() - cnt2;
			
			
			if(b[0] && b[1]) {
				if(wildCnt < wildCnt2) {
					game.placeTrack(track, color1, cnt, wildCnt, false);
					
				} else {
					game.placeTrack(track, color2, cnt2, wildCnt2, true);
				}
			} else if(b[0]) {
				game.placeTrack(track, color1, cnt, wildCnt, false);
			} else {
				game.placeTrack(track, color2, cnt2, wildCnt2, true);
			}
		}
	}
	
	private void drawTrainCards(Track track) {
		int side = 0;
		int cnt[] = new int[2];
		
		String[] colors = new String[2];
		
		colors[0] = track.getTrackColor1();
		if(track.isDoubleTrack())
			colors[1] = track.getTrackColor2();
		
		if(colors[0].equals("gray")) {
			colors[0] = cards.getMax().getColor();
			if(track.isDoubleTrack()) {
				colors[1] = colors[0];
			}
		}
		
		cnt[0] = cards.getCard(colors[0]).getCount();
		if(track.isDoubleTrack())
			cnt[1] = cards.getCard(colors[1]).getCount();
		
		if(track.isDoubleTrack() && cnt[0] < cnt[1]) {
			side = 1;			
		}
		cnt[side] += cards.getCard("wild").getCount();	
		
		String[] upTrains = game.getDeck().getUpCards();
		
		while(game.getNumCardsDrawn() < 2) {
			int wildPos = -1;
			int colPos = -1;
			
			for(int i = 0; i < upTrains.length; i++) {
				if(upTrains[i].equals(colors[side])) {
					colPos = i;
				} else if(upTrains[i].equals("wild")) {
					wildPos = i;
				}
			}
			if(colPos != -1) {
				game.drawFaceUpCard(colPos);
			} else if(wildPos != -1 && game.getNumCardsDrawn() == 0) {
				game.drawFaceUpCard(wildPos);
			} else {
				game.drawFaceDownCard();
			}
		}
	}
	
	private int getCardDistance(Track track) {
		CardNode node;
		if(track.getTrackColor1().equals("gray")) {
			node = cards.getMax();
		} else if(track.isDoubleTrack()) {
			CardNode one = cards.getCard(track.getTrackColor1());
			CardNode two = cards.getCard(track.getTrackColor2());
			if(one.getCount() > two.getCount()) {
				node = one;
			} else {
				node = two;
			}
		} else {
			node = cards.getCard(track.getTrackColor1());
		}
		return track.getLength() - node.getCount();
	}
	
	private void selectContracts(int numCards, int keepCards) {
		ContractCard[] cards = game.drawContracts(numCards);
		ArrayList<ContractCard> keep = new ArrayList<>();
		ArrayList<ContractCard> discard = new ArrayList<>();
		
		ArrayList<ContractPath> list = new ArrayList<>();
		for(int i = 0; i < cards.length; i++) {
			ContractPath cPath = new ContractPath(cards[i]);
			cPath.calculateShortestPath(game.getBoard(), trainColor);
			list.add(cPath);
		}
		
		Collections.sort(list);
		int trainCount = trains;
		for(int i = 0; i < keepCards; i++) {
			trainCount -= list.get(i).sumPath();
			keep.add(list.remove(0).getCard());
		}
		
		for(ContractPath path : list) {
			if(path.ratio() >= 1.5 && trainCount > 0) {
				trainCount -= path.sumPath();
				keep.add(path.getCard());
			} else {
				discard.add(path.getCard());
			}
		}
		game.setContracts(keep);
		game.returnContracts(discard);
	}

}
