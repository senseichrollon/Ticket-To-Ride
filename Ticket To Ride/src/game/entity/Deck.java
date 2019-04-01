package game.entity;
import java.io.*;
import java.util.*;
public class Deck {
	private Queue<String> trainDeck;
	private Queue<ContractCard> contractDeck;
	private String[] upTrains = new String[5];
	public Deck()throws IOException {
		for(int i=0;i<12;i++) {
			trainDeck.add("green");
		}
		for(int i=0;i<12;i++) {
			trainDeck.add("yellow");
		}
		for(int i=0;i<12;i++) {
			trainDeck.add("purple");
		}
		for(int i=0;i<12;i++) {
			trainDeck.add("black");
		}
		for(int i=0;i<12;i++) {
			trainDeck.add("white");
		}
		for(int i=0;i<12;i++) {
			trainDeck.add("blue");
		}
		for(int i=0;i<12;i++) {
			trainDeck.add("red");
		}
		for(int i=0;i<12;i++) {
			trainDeck.add("orange");
		}
		for(int i=0;i<14;i++) {
			trainDeck.add("wild");
		}
		shuffle();
		for(int i=0;i<5;i++) {
			upTrains[i] = trainDeck.poll();
		}
		Scanner kb = new Scanner(new File("tickets.txt"));
		while(kb.hasNextLine()) {
			String s = kb.nextLine();
			String[]ss = s.split(" ");
			Integer x = new Integer(ss[0]);
			
			contractDeck.add(new ContractCard(ss[1], ss[2], x));
		}
		
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
		if(upTrains[0]==null) {
			upTrains[0] = trainDeck.poll();
		}
		if(upTrains[1]==null) {
			upTrains[1] = trainDeck.poll();
		}
		if(upTrains[2]==null) {
			upTrains[2] = trainDeck.poll();
		}
		if(upTrains[3]==null) {
			upTrains[3] = trainDeck.poll();
		}
		if(upTrains[4]==null) {
			upTrains[4] = trainDeck.poll();
		}
		if(upTrains[5]==null) {
			upTrains[5] = trainDeck.poll();
		}
	}
}
