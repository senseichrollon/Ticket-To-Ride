package game.testing;

import game.entity.PlayerCardTree;

public class TestBranch 
{
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
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
	}
}
