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
	public static int NUMPLAYERS = 4;
	
	public GameState() throws IOException
	{
		players = new Player[4];
		players[0] = new Player("Jim", "blue");
		players[1] = new Player("Joe", "purple");
		players[2] = new Player("Bob", "green");
		players[3] = new Player("John", "yellow");
		currentPlayer = (int)(Math.random() * 5);
		numCardsDrawn = 0;
		board = new CityMap();
		deck = new Deck();
	}

	public Player[] getPlayers() 
	{
		return players;
	}

	public int getCurrentPlayer()
	{
		return currentPlayer;
	}

	public CityMap getBoard() 
	{
		return board;
	}

	public Deck getDeck() 
	{
		return deck;
	}
	public void updatePlayer()
	{
		currentPlayer++;
		if (currentPlayer == NUMPLAYERS)
			currentPlayer = 0;
	}
	
	public void drawFaceUpCard(int num)
	{
		this.numCardsDrawn++;
		players[currentPlayer].addCard(deck.drawTrain(num), 1);
	}
	
	public void drawFaceDownCard()
	{
		this.numCardsDrawn++;
		players[currentPlayer].addCard(deck.drawRandTrain(), 1);
	}
	
	public String getPlayerEdges(int num)
	{
		return players[num].getEdges();
	}
	
	public void drawContracts()
	{
		players[currentPlayer].setContracts(deck.drawContracts());
	}
	
	public void returnContracts(ArrayList<ContractCard> list)
	{
		deck.replaceContract(list);
	}
	
	public ArrayList<CardNode> removeCards(String color, int cnt)
	{
		return players[currentPlayer].removeCards(color, cnt);
	}
	
	public boolean placeTrack(String city1, String city2)
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
}
