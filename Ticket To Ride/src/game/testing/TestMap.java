package game.testing;
import java.io.IOException;

import game.entity.CityMap;


public class TestMap 
{
	public static void main(String[] args) throws IOException 
	{
		CityMap cm = new CityMap();
		System.out.println(cm.printFullMap());
		System.out.println(cm.addTrack("Raleigh", "Atlanta" , "Yeet", "Gray"));
		System.out.println(cm.printOGMap());
	}
}
