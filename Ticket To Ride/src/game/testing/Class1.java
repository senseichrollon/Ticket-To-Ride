package game.testing;
import java.util.*;
import java.io.*;
public class Class1 {
	public static void main(String[] args)throws Exception {
		Scanner in = new Scanner(new File("resources/gamedata/tracks.txt"));
		int n = in.nextInt();
		in.nextLine();
		for(int i = 1; i <= n; i++) {
			String[] sp = in.nextLine().split(" ");
			String ret = "" + i;
			for(int j = 1; j < sp.length; j++) {
				ret += " " + sp[j];
			}
			System.out.println(ret);
			
		}
	}
}
