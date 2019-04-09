package game.entity;

import java.io.*;
import java.util.*;

public class Deck {
	private Queue<String> trainDeck, drawTrainDeck;
	private Queue<ContractCard> contractDeck;
	private String[] upTrains = new String[5];

	public Deck() throws IOException {
		trainDeck = new LinkedList<String>();
		drawTrainDeck = new LinkedList<String>();
		contractDeck = new LinkedList<ContractCard>();
		for (int i = 0; i < 12; i++) {
			trainDeck.add("green");
		}
		for (int i = 0; i < 12; i++) {
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
		Scanner kb = new Scanner(new File("resources/gamedata/tickets.txt"));
		while (kb.hasNextLine()) {
			String s = kb.nextLine();
			String[] ss = s.split(" ");
			System.out.println(Arrays.toString(ss));
			Integer x = new Integer(ss[0]);

			contractDeck.add(new ContractCard(ss[1], ss[2], x));
		}

	}

	public String drawRandTrain() {
		return trainDeck.poll();
	}

	public ContractCard[] drawContracts() {
		ContractCard[] cc = new ContractCard[5];
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
		Collections.shuffle(list);
		for (int i = 0; i < list.size(); i++) {
			trainDeck.add(list.get(i));
		}
	}

	public void replaceContract(List<ContractCard> ss) {
		for (ContractCard c : ss) {
			contractDeck.add(c);
		}

	}

	public String drawTrain(int c) {
		String temp = upTrains[c];
		upTrains[c] = drawRandTrain();
		check();
		return temp;
	}

	public void replaceTrains(String s, int x) {
//		CardNode node = new CardNode(s, x);
//		if(upTrains[0]==null) {
//			upTrains[0] = trainDeck.poll();
//		}
//		if(upTrains[1]==null) {
//			upTrains[1] = trainDeck.poll();
//		}
//		if(upTrains[2]==null) {
//			upTrains[2] = trainDeck.poll();
//		}
//		if(upTrains[3]==null) {
//			upTrains[3] = trainDeck.poll();
//		}
//		if(upTrains[4]==null) {
//			upTrains[4] = trainDeck.poll();
//		}
//		if(upTrains[5]==null) {
//			upTrains[5] = trainDeck.poll();
//		}
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
		if (trainDeck.size() <= 5)
			shuffle();
	}

	public String[] getUpCards() {
		return upTrains;
	}
}
