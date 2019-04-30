package game.testing;
import java.util.*;

import game.graphics.screens.ScreenManager;

import java.io.*;
public class Class1 {
	public static void main(String[] args)throws Exception {
		Scanner in = new Scanner(new File("resources/gamedata/cities.txt"));
		int n = in.nextInt();
		in.nextLine();
		HashMap<Integer,String> map = new HashMap<>();
		for(int i = 1; i <= n; i++) {
			map.put(in.nextInt(), in.next().replace('_', ' '));
		}
		
		in = new Scanner(new File("resources/gamedata/tracks.txt"));
		
		n = in.nextInt();
		in.nextLine();
		for(int i = 1; i <= n; i++) {
			String[] sp = in.nextLine().split(" ");
			System.out.printf("%s %s %s %s %s\n",sp[0],map.get(Integer.parseInt(sp[1])),map.get(Integer.parseInt(sp[2])),sp[5],sp[6]);
		}
	}
}
