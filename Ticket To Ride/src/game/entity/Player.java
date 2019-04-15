package game.entity;

import java.util.ArrayList;

public class Player {
	private PlayerCardTree cards;
	private String name, trainColor;
	private int trains;
	private ArrayList<ContractCard> contracts;

	public static ContractCard card = new ContractCard("San Francisco", "Atlanta", 14);

	public Player(String n, String c) {
		name = n;
		trainColor = c;
		cards = new PlayerCardTree();
		String[] colors = { "black", "blue", "green", "orange", "purple", "red", "white", "yellow", "wild" };
		for (String s : colors) {
			cards.add(s, 0);
		}
		setTrains(45);
		contracts = new ArrayList<>();
		contracts.add(card);
	}

	public void addCard(String color, int cnt) {
		cards.add(color, cnt);
	}

	public String getName() {
		return name;
	}

	public String getTrainColor() {
		return trainColor;
	}

	public void decrementTrain(int cnt) {
		setTrains(getTrains() - cnt);
	}

	public ArrayList<CardNode> removeCards(String color, int cnt) {
		return cards.remove(color, cnt);
	}

	public PlayerCardTree getCards() {
		return cards;
	}

	public String getEdges() {
		return "";
	}

	public ArrayList<ContractCard> getContracts() {
		return contracts;
	}

	public void setContracts(ArrayList<ContractCard> cards) {
		for (int i = 0; i < cards.size(); i++) {
			contracts.add(cards.get(i));
		}
	}

	public int getTrains() {
		return trains;
	}

	public void setTrains(int trains) {
		this.trains = trains;
	}
}
