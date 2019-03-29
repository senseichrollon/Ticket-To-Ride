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
		testerTree.add("blue", 2);
		testerTree.add("wild", 1);
		testerTree.add("green", 2);
		testerTree.add("pink", 2);
		testerTree.add("orange", 2);
		testerTree.add("wild", 1);
		testerTree.add("red", 2);
		System.out.println(testerTree);
		testerTree.remove("red", 5);
		System.out.println(testerTree);
	}
}
