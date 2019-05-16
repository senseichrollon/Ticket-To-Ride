package game.entity;

import java.io.*;
import java.util.*;

import game.graphics.animation.AnimationManager;

public class Deck {
	private Queue<String> trainDeck, drawnTrainDeck;
	private Queue<ContractCard> contractDeck;
	private String[] upTrains = new String[5];

	// list of all contract cards to be returned in get method
	private ArrayList<ContractCard> contractList;
	private int shuffleCount;

	public Deck() throws IOException {
		trainDeck = new LinkedList<String>();
		drawnTrainDeck = new LinkedList<String>();
		contractDeck = new LinkedList<ContractCard>();
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < 12; i++) {
			list.add("green");
			list.add("yellow");
			list.add("purple");
			list.add("black");
			list.add("white");
			list.add("blue");
			list.add("red");
			
			list.add("orange");
		}
		for (int i = 0; i < 14; i++) {
			list.add("wild");
		}
		Collections.shuffle(list);
		for (int i = 0; i < list.size(); i++) {
			trainDeck.add(list.get(i));
		}

		for (int i = 0; i < 5; i++) {
			upTrains[i] = trainDeck.poll();
		}
		contractList = new ArrayList<>();
		Scanner kb = new Scanner(new File("resources/gamedata/tickets.txt"));
		while (kb.hasNextLine()) {
			String s = kb.nextLine();
			String[] ss = s.split("; ");
			// System.out.println(Arrays.toString(ss));
			Integer x = new Integer(ss[0]);
			ContractCard card = new ContractCard(ss[1], ss[2], x);
			contractList.add(card);
		}
		
		Collections.shuffle(contractList);
		for(int i = 0; i < contractList.size(); i++) {
			contractDeck.add(contractList.get(i));
		}

	}

	public String drawRandTrain(boolean def) {
		String temp = trainDeck.poll();
		if (def)
			AnimationManager.faceDownCardAnimation(temp);
		check();
		return temp;
	}

	public ContractCard[] drawContracts(int n) {
		ContractCard[] cc = new ContractCard[n];
		for (int i = 0; i < cc.length; i++) {
			cc[i] = contractDeck.poll();
		}
		return cc;
	}

	public void shuffle(boolean in) {
		ArrayList<String> list = new ArrayList<String>();
		while (!trainDeck.isEmpty()) {
			list.add(trainDeck.poll());
		}
		String[] temp = Arrays.copyOf(upTrains, upTrains.length);
		for (int i = 0; i < upTrains.length; i++) {
			list.add(upTrains[i]);
			upTrains[i] = null;
		}
		if(in)
			AnimationManager.shuffle(temp, true);
		
		Collections.shuffle(list);
		for (int i = 0; i < list.size(); i++) {
			trainDeck.add(list.get(i));
		}
		if(shuffleCount > 500)
			return;
		replaceTrains(null, false);
	}

	public void shuffleIfDeckFinished() {
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < drawnTrainDeck.size(); i++) {
			temp.add(drawnTrainDeck.poll());
		}
		Collections.shuffle(temp);
		for (int i = 0; i < temp.size(); i++) {
			trainDeck.add(temp.get(i));
		}
		boolean hasNull = true;
		for(String s : upTrains) {
			if(s == null) {
				hasNull = true;
			}
		}
		if(hasNull)
			replaceTrains(null, true);
	}

	public void replaceContract(List<ContractCard> ss) {
		for (ContractCard c : ss) {
			contractDeck.add(c);
		}

	}

	public String drawTrain(int c) {
		String temp = upTrains[c];
		upTrains[c] = null;
		if(trainDeck.isEmpty() && !drawnTrainDeck.isEmpty()) {
			shuffleIfDeckFinished();
		}
		if(!trainDeck.isEmpty())
			replaceTrains(temp, true);
		else
			AnimationManager.addTrainCardAnimation(c, temp);					
		check();
		return temp;
	}

	public Queue getTrainDeck() {
		return trainDeck;
	}

	public void replaceTrains(String prev, boolean draw) {
		if(draw) {
			for (int i = 0; i < upTrains.length; i++) {
				if (upTrains[i] == null) {
					String s = trainDeck.poll();
					AnimationManager.replaceTrainsAnimation(i, s);
					if(prev != null)
						AnimationManager.addTrainCardAnimation(i, prev);					
					upTrains[i] = s;
				}
			}
		} else {
			String[] temp = new String[5];
			int wildCount = 0;
//			System.out.println(trainDeck);
			for(int i = 0; i < upTrains.length; i++) {
				temp[i] = trainDeck.poll();
				if(temp[i].equals("wild"))
					wildCount++;
			}
			if(wildCount< 3)
				AnimationManager.shuffle(temp, false);
			for(int i = 0; i < upTrains.length; i++) {
				upTrains[i] = temp[i];
			}
			if(wildCount >= 3) {
				shuffleCount++;
				shuffle(false);
			}
		}

	}

	public void check() {
		if (trainDeck.size() == 0 && drawnTrainDeck.size() != 0)
			shuffleIfDeckFinished();
		int cntWild = 0;
		for (int i = 0; i < upTrains.length; i++) {
			if (upTrains[i] != null && upTrains[i].equals("wild"))
				cntWild++;
		}
		if (cntWild >= 3) {
			shuffleCount = 0;
			shuffle(true);
		}
	}

	public String[] getUpCards() {
		return upTrains;
	}

	public ArrayList<ContractCard> getContractCards() {
		return contractList;
	}

	public void addDrawnCards(String color, int cnt) {
		for (int i = 0; i < cnt; i++) {
			drawnTrainDeck.add(color);
		}
		check();
	}

//	public void addDrawnCards(ArrayList<CardNode> cards) {
//		for (int i = 0; i < cards.size(); i++) {
//			drawnTrainDeck.add(cards.get(i).getColor());
//		}
//	}

	public boolean canDrawTrains() {
		boolean cards = false;
		for(String s : upTrains) {
			if(s != null)
				cards = true;
		}
		if(cards) {
			int cnt = 0;
			for(String s : upTrains) {
				if(s != null  && !s.equals("wild")) {
					cnt++;
				}
			}
			if(cnt <= 1 && trainDeck.isEmpty() && drawnTrainDeck.isEmpty()) {
				cards = false;
			}
		}
		return !(trainDeck.size() == 0 && drawnTrainDeck.size() == 0 && !cards);
	}
	
	public boolean canDrawContracts() {
		return contractDeck.size() >= 3;
	}
}
