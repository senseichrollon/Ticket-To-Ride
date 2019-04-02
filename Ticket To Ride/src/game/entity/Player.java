package game.entity;

import java.util.ArrayList;

public class Player 
{
	private PlayerCardTree cards;
	private String name, trainColor;
	private int trains;
	private ArrayList<ContractCard> contracts;
	
	public Player(String n, String c)
	{
		name = n;
		trainColor = c;
		cards = new PlayerCardTree();
		trains = 45;
		contracts = new ArrayList<ContractCard>();
	}
	
	public void addCard(String color, int cnt)
	{
		cards.add(color, cnt);
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getTrainColor()
	{
		return trainColor;
	}
	
	public void decrementTrain(int cnt)
	{
		trains -= cnt;
	}
	
	public boolean removeCards(String color, int cnt)
	{
		return cards.remove(color, cnt);
	}
}
