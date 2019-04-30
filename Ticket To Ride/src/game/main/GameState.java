package game.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import game.entity.CardNode;
import game.entity.CityMap;
import game.entity.ContractCard;
import game.entity.Deck;
import game.entity.Player;
import game.entity.Track;

public class GameState {
	private Player[] players;
	private int currentPlayer, numCardsDrawn;
	private CityMap board;
	private Deck deck;
	private boolean hasWinner;
	private int lastRound;
	public static int NUMPLAYERS = 4;

	public static String[] PLAYER_COLS;
	
	public GameState() throws IOException
	{
		players = new Player[4];
		players[0] = new Player("Jim", "blue");
		players[1] = new Player("Joe", "purple");
		players[2] = new Player("Bob", "green");
		players[3] = new Player("John", "yellow");
		deck = new Deck();

		for(Player ply : players) {
			for(int i = 0; i < 3; i++) {
				String card = deck.drawRandTrain(false);
				ply.addCard(card, 1);
			}
		}
		
		currentPlayer = (int) (Math.random() * 4);
		PLAYER_COLS = new String[4];
		PLAYER_COLS[0] = "blue";
		PLAYER_COLS[1] = "purple";
		PLAYER_COLS[2] = "green";
		PLAYER_COLS[3] = "yellow";
		currentPlayer = (int)(Math.random() * 4);
		numCardsDrawn = 0;
		lastRound = -1;
		board = new CityMap();
	}

	public Player[] getPlayers() {
		return players;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public CityMap getBoard() {
		return board;
	}

	public Deck getDeck() {
		return deck;
	}

	public void updatePlayer() {
		currentPlayer++;
		if (currentPlayer == NUMPLAYERS)
			currentPlayer = 0;
		if(lastRound != -1)
			lastRound++;
		for(Player ply : players) {
			if(ply.getTrains()  <= 3) {
				lastRound = 1;
			}
		}
		
		resetNumCardsDrawn();
		setContractCompletion();
	}
	
	public void setContractCompletion() {
		ArrayList<ContractCard> contracts = players[currentPlayer].getContracts();
		for(int i = 0; i < contracts.size(); i++) {
			if(!contracts.get(i).isComplete()) {
				contracts.get(i).setComplete(board.completedContract(players[currentPlayer].getTrainColor(), contracts.get(i)));
			}
		}
	}

	public void drawFaceUpCard(int num) {
		String card = deck.drawTrain(num);
		players[currentPlayer].addCard(card, 1);
		if (card.equals("wild")) {
			if (numCardsDrawn == 1)
				return;
			numCardsDrawn += 2;
		} else {
			numCardsDrawn++;
		}
	}

	public void drawFaceDownCard() {
		players[currentPlayer].addCard(deck.drawRandTrain(true), 1);
		numCardsDrawn += 1;
	}

	public String getPlayerEdges(int num) {
		return players[num].getEdges();
	}

	public ContractCard[] drawContracts() {
		return deck.drawContracts();
	}

	public void setContracts(ArrayList<ContractCard> list) {
		players[currentPlayer].setContracts(list);
	}

	public void returnContracts(ArrayList<ContractCard> list) {
		deck.replaceContract(list);
	}

	public int getNumCardsDrawn() {
		return numCardsDrawn;
	}

	public boolean placeTrack(Track track, String color, int colorCount, int wildCount,boolean second) {
		players[currentPlayer].removeCards(color, colorCount, wildCount);
		players[currentPlayer].decrementTrain(track.getLength());
		boolean ret = board.addTrack(track, players[currentPlayer].getTrainColor(), second?track.getTrackColor2():track.getTrackColor1());
		if(ret) {
			int points = 0;
			switch(track.getLength()) {
				case 1 : {
					points = 1;
					break;
				}
				case 2: {
					points = 2;
					break;
				}
				case 3: {
					points = 4;
					break;
				}
				case 4: {
					points = 7;
					break;
				}
				case 5: {
					points = 10;
					break;
				}
				case 6: {
					points = 15;
					break;
				}
			}
			players[currentPlayer].addPoints(points);
		}
		return ret;
	}

	public void resetNumCardsDrawn() {
		numCardsDrawn = 0;
	}

	public ArrayList<CardNode> removeCards(String color, int len) {
		return new ArrayList<CardNode>();
	}

	public HashMap<Track, boolean[]> getPlacableTracks() {
		return board.getPlaceableTracks(players[currentPlayer]);
	}

	public int hasWinner() {
		return lastRound;
	}
	
	public int[][] endGame() {
		int[][] mat = new int[3][3];
		for(int i = 0; i < players.length; i++) {
			mat[0][i] = players[i].getPoints();
			for(ContractCard card : players[i].getContracts()) {
				if(card.isComplete()) {
					mat[1][i] += card.getPoints();
				} else {
					mat[2][i] += card.getPoints();
				}
			}
		}
		return mat;
	}
}
