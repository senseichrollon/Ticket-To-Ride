package game.testing;

import game.entity.CityMap;

public class TestMap
{
	public static void main(String[] args) {
		CityMap cm = new CityMap();
		cm.addTrack(cm.getTrack("Sault St. Marie", "Toronto"), "Yeet", "gray", 1);
		cm.addTrack(cm.getTrack("Montreal", "Toronto"), "Yeet", "gray", 1);
		cm.addTrack(cm.getTrack("Pittsburgh", "Toronto"), "Yeet", "gray", 1);
		
		cm.addTrack(cm.getTrack("Denver", "Helena"), "Bleet", "green", 1);
		
		System.out.println(cm.getPlayersLongest());
	}
}