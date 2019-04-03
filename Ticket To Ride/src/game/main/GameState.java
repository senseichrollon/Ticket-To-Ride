package game.main;

import java.io.IOException;
import java.util.ArrayList;
import game.entity.CityMap;
import game.entity.ContractCard;
import game.entity.Deck;
import game.entity.Player;

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
		if (currentPlayer == 4)
			currentPlayer = 0;
	}
	
	public String drawFaceUpCard(int num)
	{
		return deck.drawTrain(num);
	}
	
	public String drawFaceDownCard()
	{
		return deck.drawRandTrain();
	}
	
	public String getPlayerEdges(int num)
	{
		return players[num].getEdges();
	}
	
	public ContractCard[] drawContracts()
	{
		return deck.drawContracts();
	}
	
	public void returnContracts(ArrayList<ContractCard> list)
	{
		deck.replaceContract(list);
	}
}
