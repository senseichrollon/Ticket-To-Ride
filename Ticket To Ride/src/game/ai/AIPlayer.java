package game.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import game.entity.ContractCard;
import game.entity.Player;
import game.entity.Track;
import game.main.GameState;

public class AIPlayer extends Player{
	private GameState game;
	
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
			ContractPath path = new ContractPath(card);
			path.calculateShortestPath(game.getBoard(), trainColor);
			contractList.add(path);
		}
		HashMap<Track, boolean[]> tracks = game.getPlacableTracks();
		
		if(contractList.isEmpty() && trains > 10 && game.getDeck().canDrawContracts()) {
			selectContracts(3,1);
		} else if(contractList.isEmpty() && trains <= 10) { 
			
		} else {
			ContractPath opt = Collections.min(contractList);
			Track track = opt.getBestTrack(this);
			if(tracks.containsKey(track)) {
				placeTrack(track, tracks.get(track));
			} else {
				drawTrainCards(track);
			}
		}
	}
	
	private void placeTrack(Track track, boolean[] b) {
		if(!track.isDoubleTrack()) {
			int cnt = Math.min(track.getLength(), cards.getCard(track.getTrackColor1()).getCount());
			int wildCnt = track.getLength()-cnt;
			game.placeTrack(track, track.getTrackColor1(), cnt, wildCnt, false);
		} else {
			int cnt = Math.min(track.getLength(), cards.getCard(track.getTrackColor1()).getCount());
			int wildCnt = track.getLength()-cnt;
			
			int cnt2 = Math.min(track.getLength(), cards.getCard(track.getTrackColor2()).getCount());
			int wildCnt2 = track.getLength() - cnt2;
			
			if(b[0] && b[1]) {
				if(wildCnt < wildCnt2) {
					game.placeTrack(track, track.getTrackColor1(), cnt, wildCnt, false);
				} else {
					game.placeTrack(track, track.getTrackColor2(), cnt, wildCnt, true);
				}
			} else if(b[0]) {
				game.placeTrack(track, track.getTrackColor1(), cnt, wildCnt, false);
			} else {
				game.placeTrack(track, track.getTrackColor2(), cnt, wildCnt, true);
			}
		}
	}
	
	private void drawTrainCards(Track track) {
		int side = 0;
		if(track.isDoubleTrack()) {
			int cnt1 = cards.getCard(track.getTrackColor1()).getCount();
			int cnt2 = cards.getCard(track.getTrackColor2()).getCount();
			
			if(cnt1 < cnt2) {
				side = 1;
			}
		}
		
		
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
			trains -= list.get(i).sumPath();
			keep.add(list.remove(0).getCard());
		}
		
		for(ContractPath path : list) {
			if(path.ratio() >= 1.5 && trainCount > 0) {
				trains -= path.sumPath();
				keep.add(path.getCard());
			} else {
				discard.add(path.getCard());
			}
		}
		game.setContracts(keep);
		game.returnContracts(discard);
	}

}
