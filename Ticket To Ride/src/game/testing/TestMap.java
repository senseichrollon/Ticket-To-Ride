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
		
		//cm.addTrack("Seattle", "Helena" , "Yeet", null);
		
		cm.addTrack("Calgary", "Winnipeg" , "Yeet", null);
		cm.addTrack("Calgary", "Helena" , "Yeet", null);
		cm.addTrack("Helena", "Winnipeg" , "Yeet", null);
		cm.addTrack("Winnipeg", "Duluth", "Yeet", null);
		cm.addTrack("Helena", "Duluth", "Yeet", null);
		cm.addTrack("Helena", "Omaha" , "Yeet", null);
		cm.addTrack("Omaha", "Denver" , "Yeet", null);
		cm.addTrack("Helena", "Denver" , "Yeet", null);
		
		//cm.addTrack("Toronto", "Duluth" , "Yeet", null);
		//cm.addTrack("Las_Vegas", "Salt_Lake_City" , "Bleet", null);
		//cm.addTrack("Las_Vegas", "Los_Angeles" , "Bleet", null);
		//cm.addTrack("Winnipeg", "Sault_St._Marie" , "Bleet", null);
		//cm.addTrack("Duluth", "Chicago" , "Yeet", null);
		//cm.addTrack("Duluth", "Toronto" , "Yeet", null);
		
		System.out.println(cm.getPlayersLongest());
		System.out.println(cm.testDP());
		
		
	}
}
