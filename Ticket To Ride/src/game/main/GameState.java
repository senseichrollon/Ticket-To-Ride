package game.main;

import java.io.IOException;
import java.util.ArrayList;

import game.entity.CardNode;
import game.entity.CityMap;
import game.entity.ContractCard;
import game.entity.Deck;
import game.entity.Player;
import game.entity.Track;

public class GameState 
{
	private Player[] players;
	private int currentPlayer, numCardsDrawn;
	private CityMap board;
	private Deck deck;
	private boolean hasWinner;
	public static int NUMPLAYERS = 4;
	public static String[] PLAYER_COLS;
	
	public GameState() throws IOException
	{
		players = new Player[4];
		players[0] = new Player("Jim", "blue");
		players[1] = new Player("Joe", "purple");
		players[2] = new Player("Bob", "green");
		players[3] = new Player("John", "yellow");
		PLAYER_COLS = new String[4];
		PLAYER_COLS[0] = "blue";
		PLAYER_COLS[1] = "purple";
		PLAYER_COLS[2] = "green";
		PLAYER_COLS[3] = "yellow";
		currentPlayer = (int)(Math.random() * 4);
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
		resetNumCardsDrawn();
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
		System.out.println("hello");
		players[currentPlayer].addCard(deck.drawRandTrain(), 1);
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
	
	public boolean placeTrack(	String city1, String city2)
	{
		Track temp = board.getTrack(city1, city1);
		if (!temp.isFilled())
		{
			String color = temp.getTrackColor1();
			if (board.addTrack(city1, city2, players[this.currentPlayer].getTrainColor(), color))
			{
				ArrayList<CardNode> cards = removeCards(temp.getTrackColor1(), temp.getLength());
				deck.addDrawnCards(cards);
				return true;
			}
			else if (board.addTrack(city1, city2, players[this.currentPlayer].getTrainColor(), temp.getTrackColor2()))
			{
				ArrayList<CardNode> cards = removeCards(temp.getTrackColor2(), temp.getLength());
				deck.addDrawnCards(cards);
				return true;
			}
			
		}
		return false;
	}
	public void resetNumCardsDrawn() {
		numCardsDrawn = 0;
	}

	public ArrayList<CardNode> removeCards(String color, int len)
	{
		return new ArrayList<CardNode>();
	}
	
	
	public boolean hasWinner() {
		for(Player p : players) {
			if(p.getTrains() <= 3) {
				return true;
			}
		}
		return false;
	}
	
	public String[][] endgame(Player p)
	{
		String[][] points = new String[4][5];
		for (int y = 0; y < p.getContracts().size(); y++)
		{
			ArrayList<ContractCard> temp = p.getContracts();
				
					
		}
	}
}
