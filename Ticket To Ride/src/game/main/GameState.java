package game.main;

import java.io.IOException;
import java.util.ArrayList;

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
	public static int NUMPLAYERS = 4;

	public GameState() throws IOException {
		players = new Player[4];
		players[0] = new Player("Jim", "blue");
		players[1] = new Player("Joe", "purple");
		players[2] = new Player("Bob", "green");
		players[3] = new Player("John", "yellow");
		currentPlayer = (int) (Math.random() * 4);
		numCardsDrawn = 0;
		board = new CityMap();
		deck = new Deck();
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
	}

	public void drawFaceUpCard(int num) {
		String card = deck.drawTrain(num);
		players[currentPlayer].addCard(card, 1);
		if(card.equals("wild")) {
			if(numCardsDrawn == 1)
				return;
			numCardsDrawn += 2;
		} else {
			numCardsDrawn++;
		}
	}

	public void drawFaceDownCard() {
		players[currentPlayer].addCard(deck.drawRandTrain(), 1);
		numCardsDrawn += 2;
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
	
	public void resetNumCardsDrawn() {
		numCardsDrawn = 0;
	}

//	public void removeCards()
//	{
//		
//	}
//	
	
	public boolean hasWinner() {
		for(Player p : players) {
			if(p.getTrains() <= 3) {
				return true;
			}
		}
		return false;
	}
	public boolean canPlaceTrack(Track track, ArrayList<String> cards) {
		return false;
	}
	
	
}
