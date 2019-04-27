package game.testing;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;

import game.entity.Deck;
import game.entity.PlayerCardTree;

public class TestBranch 
{
	public static void main(String[] args) throws IOException
	{
		// TODO Auto-generated method stub
<<<<<<< HEAD
		System.out.println("Nikunj");
		PlayerCardTree testerTree = new PlayerCardTree();
		testerTree.add("red", 2);
		testerTree.add("white", 2);
		testerTree.add("blue", 4);
		testerTree.add("yellow", 1);
		testerTree.add("green", 2);
		testerTree.add("purple", 2);
		testerTree.add("orange", 2);
		testerTree.add("wild", 6);
		testerTree.add("black", 2);
		
		
		System.out.println(testerTree.hasEnough("blue", 10));
=======
//		System.out.println("Nikunj");
//		PlayerCardTree testerTree = new PlayerCardTree();
//		testerTree.add("red", 2);
//		testerTree.add("white", 2);
//		testerTree.add("blue", 2);
//		testerTree.add("wild", 1);
//		testerTree.add("green", 2);
//		testerTree.add("pink", 2);
//		testerTree.add("orange", 2);
//		testerTree.add("wild", 1);
//		testerTree.add("red", 2);
//		System.out.println(testerTree);
//		testerTree.remove("red", 5);
//		System.out.println(testerTree);
		
		Deck test = new Deck();
//		System.out.println(Arrays.toString(test.getUpCards()));
//		test.shuffleIfDeckFinished();
//		System.out.println(Arrays.toString(test.getUpCards()));
		
//		ArrayList<String> temp = new ArrayList<String>();
//		temp.add(1);
//		temp.add(2);
//		temp.add(3);
//		System.out.println(temp);
//		Collections.shuffle(temp);
//		System.out.println(temp);
		System.out.println(Arrays.toString(test.getUpCards()));
		String temp = test.drawTrain(1);
		System.out.println(Arrays.toString(test.getUpCards()));
		test.shuffle();
		System.out.println(Arrays.toString(test.getUpCards()));
>>>>>>> branch 'master' of https://github.com/senseichrollon/Ticket-To-Ride.git
	}
}
