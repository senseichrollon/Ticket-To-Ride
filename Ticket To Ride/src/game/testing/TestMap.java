package game.testing;
<<<<<<< HEAD
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
=======
>>>>>>> branch 'master' of https://github.com/senseichrollon/Ticket-To-Ride.git

import game.ai.ContractPath;
import game.entity.CityMap;
<<<<<<< HEAD
import game.entity.ContractCard;
import game.entity.Deck;
=======
>>>>>>> branch 'master' of https://github.com/senseichrollon/Ticket-To-Ride.git

public class TestMap
{
	public static void main(String[] args) {
		CityMap cm = new CityMap();
<<<<<<< HEAD
		Deck deck = new Deck();
=======
		cm.addTrack(cm.getTrack("Sault St. Marie", "Toronto"), "Yeet", "gray", 1);
		cm.addTrack(cm.getTrack("Montreal", "Toronto"), "Yeet", "gray", 1);
		cm.addTrack(cm.getTrack("Pittsburgh", "Toronto"), "Yeet", "gray", 1);
		
		cm.addTrack(cm.getTrack("Denver", "Helena"), "Bleet", "green", 1);
>>>>>>> branch 'master' of https://github.com/senseichrollon/Ticket-To-Ride.git
		
<<<<<<< HEAD
//		cm.addTrack(cm.getTrack("Nashville", "Raleigh"), "Yeet", null, 1);
//		cm.addTrack(cm.getTrack("Pittsburgh", "Raleigh"), "Yeet", null, 1);
//		cm.addTrack(cm.getTrack("Pittsburgh", "New York"), "Yeet", "green", 1);
//		cm.addTrack(cm.getTrack("Washington", "New York"), "Yeet", "orange", 1);
//		cm.addTrack(cm.getTrack("Boston", "New York"), "Yeet", "red", 1);
//		cm.addTrack(cm.getTrack("Salt Lake City", "Denver"), "Yeet", "red", 1);
//		cm.addTrack(cm.getTrack("Denver", "Kansas City"), "Yeet", "black", 1);
		
		/*cm.addTrack("Calgary", "Helena" , "Yeet", null);
		cm.addTrack("Helena", "Winnipeg" , "Yeet", null);
		cm.addTrack("Winnipeg", "Duluth", "Yeet", null);
		cm.addTrack("Helena", "Duluth", "Yeet", null);
		cm.addTrack("Helena", "Omaha" , "Yeet", null);
		cm.addTrack("Omaha", "Denver" , "Yeet", null);
		cm.addTrack("Helena", "Denver" , "Yeet", null);*/
		
		//cm.addTrack("Toronto", "Duluth" , "Yeet", null);
		//cm.addTrack("Las_Vegas", "Salt_Lake_City" , "Bleet", null);
		//cm.addTrack("Las_Vegas", "Los_Angeles" , "Bleet", null);
		//cm.addTrack("Winnipeg", "Sault_St._Marie" , "Bleet", null);
		//cm.addTrack("Duluth", "Chicago" , "Yeet", null);
		//cm.addTrack("Duluth", "Toronto" , "Yeet", null);
		
		/*System.out.println(cm.getPlayersLongest());
		System.out.println(cm.testDP());*/
		
//		System.out.println(cm.completedContract("Yeet", new ContractCard("Salt Lake City", "Kansas City", 100)));
//		System.out.println(cm.completedContract("Yeet", new ContractCard("Nashville", "New York", 100)));
		ArrayList<ContractPath> list = new ArrayList<>();
		for(ContractCard card : deck.getContractCards()) {
			ContractPath path = new ContractPath(card);
			path.calculateShortestPath(cm, "Yeet");
			list.add(path);
		}
		Collections.sort(list);
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).ratio());
		}
=======
		System.out.println(cm.getPlayersLongest());
>>>>>>> branch 'master' of https://github.com/senseichrollon/Ticket-To-Ride.git
	}
}