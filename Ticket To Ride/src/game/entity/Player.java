package game.entity;

import java.util.ArrayList;

public class Player {
	protected PlayerCardTree cards;
	protected String name, trainColor;
	protected int trains, points, numCompletedContracts;
	protected ArrayList<ContractCard> contracts;

	public Player(String n, String c) {
		name = n;
		trainColor = c;
		numCompletedContracts = 0;
		cards = new PlayerCardTree();
		String[] colors = { "black", "blue", "green", "orange", "purple", "red", "white", "yellow", "wild" };
		for (String s : colors) {
			cards.add(s, 0);
		}

		setTrains(5);
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

	public void removeCards(String color, int cnt, int wildCnt) {
		cards.remove(color, cnt, wildCnt);
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

	public void addPoints(int points) {
		this.points += points;
	}

	public int getNumCompletedContracts() {
		return this.numCompletedContracts;
	}

	public void setNumCompletedContracts(int num) {
		this.numCompletedContracts = num;
	}
}
