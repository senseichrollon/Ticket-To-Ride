package game.entity;
import java.util.*;
public class Deck {
	private Queue<String> trainDeck;
	private Queue<ContractCard> contractDeck;
	private String[] upTrains;
	public Deck(Queue<String> s, Queue<ContractCard> q, String[] ss) {
		trainDeck = s;
		contractDeck = q;
		upTrains = ss;
	}
	public String drawRandTrain() {
		return trainDeck.poll();
	}
	public ContractCard[] drawContracts() {
		ContractCard[] cc = new ContractCard[5];
		for(int i=0;i<cc.length;i++) {
			cc[i] = contractDeck.poll();
		}
		return cc;
	}
	public void shuffle() {
		ArrayList<String> list = new ArrayList<String>();
		while(!trainDeck.isEmpty()) {
			list.add(trainDeck.poll());
		}
		Collections.shuffle(list);
		for(int i=0;i<list.size();i++) {
			trainDeck.add(list.get(i));
		}

	}
	
	public void replaceContract(List<ContractCard> ss) {
		for(ContractCard c:ss) {
			contractDeck.add(c);
		}
		
	}
	public void replaceTrains(String s, int x) {
		CardNode node = new CardNode(s, x);
		
	}
}
