package game.entity;

import java.io.*;
import java.util.*;

public class Deck {
	private Queue<String> trainDeck, drawnTrainDeck;
	private Queue<ContractCard> contractDeck;
	private String[] upTrains = new String[5];
	
	// list of all contract cards to be returned in get method
	private ArrayList<ContractCard> contractList;

	public Deck() throws IOException {
		trainDeck = new LinkedList<String>();
		drawnTrainDeck = new LinkedList<String>();
		contractDeck = new LinkedList<ContractCard>();
		for (int i = 0; i < 12; i++) {
			trainDeck.add("green");
			trainDeck.add("yellow");
			trainDeck.add("purple");
			trainDeck.add("black");
			trainDeck.add("white");
			trainDeck.add("blue");
			trainDeck.add("red");
			trainDeck.add("orange");
		}

		for (int i = 0; i < 14; i++) {
			trainDeck.add("wild");
		}
		shuffle();
		for (int i = 0; i < 5; i++) {
			upTrains[i] = trainDeck.poll();
		}
		contractList = new ArrayList<>();
		Scanner kb = new Scanner(new File("resources/gamedata/tickets.txt"));
		while (kb.hasNextLine()) {
			String s = kb.nextLine();
			String[] ss = s.split("; ");
		//	System.out.println(Arrays.toString(ss));
			Integer x = new Integer(ss[0]);
			ContractCard card = new ContractCard(ss[1], ss[2], x);
			contractDeck.add(card);
			contractList.add(card);
		}

	}

	public String drawRandTrain() {
		return trainDeck.poll();
	}

	public ContractCard[] drawContracts() {
		ContractCard[] cc = new ContractCard[3];
		for (int i = 0; i < cc.length; i++) {
			cc[i] = contractDeck.poll();
		}
		return cc;
	}

	public void shuffle() {
		ArrayList<String> list = new ArrayList<String>();
		while (!trainDeck.isEmpty()) {
			list.add(trainDeck.poll());
		}
		for (int i = 0; i < upTrains.length; i++)
		{
			list.add(upTrains[i]);
		}
		Collections.shuffle(list);
		for (int i = 0; i < list.size(); i++) {
			trainDeck.add(list.get(i));
		}
		replaceTrains();
	}
	
	public void shuffleIfDeckFinished()
	{
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < drawnTrainDeck.size(); i++)
		{
			temp.add(drawnTrainDeck.poll());
		}
		for (int i = 0; i < upTrains.length; i++)
		{
			temp.add(upTrains[i]);
		}
		Collections.shuffle(temp);
		for (int i = 0; i < temp.size(); i++) 
		{
			trainDeck.add(temp.get(i));
		}
		replaceTrains();
	}

	public void replaceContract(List<ContractCard> ss) {
		for (ContractCard c : ss) {
			contractDeck.add(c);
		}

	}

	public String drawTrain(int c) {
		String temp = upTrains[c];
		upTrains[c] = null;
		replaceTrains();
		check();
		return temp;
	}

	public void replaceTrains() {
		for (int i = 0; i < upTrains.length; i++) {
			if (upTrains[i] == null)
				upTrains[i] = trainDeck.poll();
		}
	}

	public void check() {
		int cntWild = 0;
		for (int i = 0; i < upTrains.length; i++) {
			if (upTrains[i].equals("wild"))
				cntWild++;
		}
		if (cntWild >= 3)
			shuffle();
		if (trainDeck.size() == 0)
			shuffleIfDeckFinished();
	}

	public String[] getUpCards() {
		return upTrains;
	}
	
	public ArrayList<ContractCard> getContractCards() {
		return contractList;
	}
	
	public void addDrawnCards(String color, int cnt)
	{
		for (int i = 0; i < cnt; i++)
		{
			drawnTrainDeck.add(color);
		}
	}
	
	public void addDrawnCards(ArrayList<CardNode> cards)
	{
		for (int i = 0; i < cards.size(); i++)
		{
			drawnTrainDeck.add(cards.get(i).getColor());
		}
	}
}
