package game.entity;

import java.util.ArrayList;

public class Player {
	private PlayerCardTree cards;
	private String name, trainColor;
	private int trains, points;
	private ArrayList<ContractCard> contracts;
	


	public Player(String n, String c) {
		name = n;
		trainColor = c;
		cards = new PlayerCardTree();
		String[] colors = { "black", "blue", "green", "orange", "purple", "red", "white", "yellow", "wild" };
		for (String s : colors) {
			cards.add(s, 0);
		}
		setPoints(0);
		setTrains(45);
		contracts = new ArrayList<>();
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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
