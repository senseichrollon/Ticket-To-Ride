package game.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import game.ai.AIPlayer;
import game.entity.CardNode;
import game.entity.CityMap;
import game.entity.ContractCard;
import game.entity.Deck;
import game.entity.Player;
import game.entity.Track;
import game.graphics.animation.AnimationManager;
import game.graphics.screens.PlayerSelectionScreen;

public class GameState {
	private Player[] players;
	private int currentPlayer, numCardsDrawn;
	private CityMap board;
	private Deck deck;
	private boolean hasWinner;
	private int lastRound;
	public static int NUMPLAYERS = 4;

	public static String[] PLAYER_COLS = new String[]{"blue", "purple", "green", "yellow"};
	public static String[] PLAYER_NAMES = new String[]{"Jim", "Joe", "Bob", "John"};;
	
	public GameState() throws IOException
	{
		players = new Player[4];
		deck = new Deck();
		for(int i = 0; i < 4; i++) {
			Player ply;
			if(PlayerSelectionScreen.PLAYER_TYPE[i]) {
				ply = new AIPlayer(PLAYER_NAMES[i], PLAYER_COLS[i],this);
			} else {
				ply = new Player(PLAYER_NAMES[i], PLAYER_COLS[i]);
			}
			for(int j = 0; j < 4; j++) {
				String card = deck.drawRandTrain(false);
				ply.addCard(card, 1);
			}
			players[i] = ply;
		}
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
			if(ply.getTrains()  <= 3 && lastRound == -1) {
				lastRound = 1;
			}
		}
		resetNumCardsDrawn();
	}
	
	public void setContractCompletion() {
		ArrayList<ContractCard> contracts = players[currentPlayer].getContracts();
		for(int i = 0; i < contracts.size(); i++) {
			//
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

	public ContractCard[] drawContracts(int n) {
		return deck.drawContracts(n);
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

	public boolean placeTrack(Track track, String color, int colorCount, int wildCount, boolean second) {
		players[currentPlayer].removeCards(color, colorCount, wildCount);
		AnimationManager.placeTrainsAnimation(color, colorCount, wildCount);
		players[currentPlayer].decrementTrain(track.getLength());
		boolean ret = board.addTrack(track, players[currentPlayer].getTrainColor(), second?track.getTrackColor2():track.getTrackColor1(), second?2:1);
		deck.addDrawnCards(color, colorCount);
		deck.addDrawnCards("wild", wildCount);
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
			setContractCompletion();
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
	
	public int[][] endGame() 
	{
		int[][] mat = new int[4][6];
		mat[0][0] = 0;
		mat[1][0] = 1;
		mat[2][0] = 2;
		mat[3][0] = 3;
		for(int i = 0; i < players.length; i++) 
		{
			mat[i][1] = players[i].getPoints();
			int cnt = 0;
			for(ContractCard card : players[i].getContracts()) 
			{
				if(card.isComplete()) 
				{
					mat[i][2] += card.getPoints();
					cnt++;
				} 
				else 
				{
					mat[i][3] += card.getPoints();
				}
			}
			players[i].setNumCompletedContracts(cnt);
		}
		ArrayList<String> longestPath = board.getPlayersLongest();
		System.out.println(longestPath);
		if (longestPath.size() > 1)
		{
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (int i = 0; i < longestPath.size(); i++)
			{
				for(int j = 0; j < PLAYER_COLS.length; j++)
				{
					if(longestPath.get(i).equals(PLAYER_COLS[j]))
						temp.add(j);
				}
			}
			int mostCompleted = temp.get(0);
			for (int x = 1; x < temp.size(); x++)
			{
				if (players[temp.get(x)].getNumCompletedContracts() > players[mostCompleted].getNumCompletedContracts())
					mostCompleted = temp.get(x);
			}
			mat[mostCompleted][4] = 10;
		}
		else
		{
			for (int i = 0; i < PLAYER_COLS.length; i++)
			{
				if (longestPath.get(0).equals(PLAYER_COLS[i]))
					mat[i][4] = 10;
			}
		}
		int max = Integer.MIN_VALUE;
		ArrayList<Integer> mostCompleted = new ArrayList<>();
		for (int i = 0; i < NUMPLAYERS; i++)
		{
			if(players[i].getNumCompletedContracts() > max) {
				mostCompleted.clear();
				max = players[i].getNumCompletedContracts();
				mostCompleted.add(i);
			} else if(players[i].getNumCompletedContracts() == max) {
				mostCompleted.add(i);
			}
				
		}
		for(int i : mostCompleted) {
			mat[i][5] = 15;
		}
		return mat;
	}
	
	public void endGameDebug()
	{
		lastRound = 5;
	}
}
