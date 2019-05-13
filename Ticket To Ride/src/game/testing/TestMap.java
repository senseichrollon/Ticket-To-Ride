package game.testing;
import java.io.IOException;
import java.util.ArrayList;

import game.entity.CityMap;
import game.entity.ContractCard;
import game.entity.Track;


public class TestMap 
{
	public static void main(String[] args) throws IOException 
	{
		CityMap cm = new CityMap();
		cm.addTrack(cm.getTrack("Calgary", "Helena"), "Bob", "gray", 1);
		cm.addTrack(cm.getTrack("Denver", "Helena"), "Bob", "green", 1);
		cm.addTrack(cm.getTrack("Denver", "Salt Lake City"), "Bob", "yellow", 1);
		cm.addTrack(cm.getTrack("Denver", "Kansas City"), "Bob", "black", 1);
		cm.addTrack(cm.getTrack("Kansas City", "Saint Louis"), "Bob", "purple", 1);
		cm.addTrack(cm.getTrack("Saint Louis", "Nashville"), "Bob", "gray", 1);
		
		cm.addTrack(cm.getTrack("Duluth", "Omaha"), "Gary", "gray", 1);
		cm.addTrack(cm.getTrack("Kansas City", "Omaha"), "Gary", "gray", 1);
		cm.addTrack(cm.getTrack("Kansas City", "Oklahoma City"), "Gary", "gray", 1);
		
		cm.addTrack(cm.getTrack("Duluth", "Omaha"), "Ron", "gray", 2);
		cm.addTrack(cm.getTrack("Denver", "Omaha"), "Ron", "purple", 1);
		cm.addTrack(cm.getTrack("Denver", "Santa Fe"), "Ron", "gray", 1);
		cm.addTrack(cm.getTrack("Santa Fe", "El Paso"), "Ron", "gray", 1);
		
		cm.addTrack(cm.getTrack("Seattle", "Portland"), "Yeet", "gray", 2);
		cm.addTrack(cm.getTrack("San Francisco", "Portland"), "Yeet", "purple", 2);
		cm.addTrack(cm.getTrack("San Francisco", "Los Angeles"), "Yeet", "yellow", 1);
		
		System.out.println("\nblue: "  + cm.completedContract("Bob", new ContractCard("Calgary", "Nashville", 10)));
		System.out.println("blue: "  + cm.completedContract("Bob", new ContractCard("Salt Lake City", "Kansas City", 10)));
		System.out.println("red: "  + cm.completedContract("Ron", new ContractCard("Duluth", "El Paso", 10)));
		System.out.println("green: "  + cm.completedContract("Gary", new ContractCard("Duluth", "El Paso", 10)));
		System.out.println("yellow: "  + cm.completedContract("Yeet", new ContractCard("Seattle", "Los Angeles", 10)));
	}
}
