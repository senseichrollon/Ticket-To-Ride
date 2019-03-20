package game.testing;
import java.util.ArrayList;
import java.util.Collections;

public class LongestPath 
{
	static int[] dp;
	static int[][] graph;
	public static void main(String[] args)
	{
		
	}
	
	public static int longestPath(ArrayList<String> visited, int node, int edgeLength)
	{
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		for(int i = 0; i < graph[node].length; i++)
		{
			for(String j: visited)
			{
				int one = j.charAt(0)-'0';
				int two = j.charAt(2)- '0';
				if((node == one && i == two) || (node == two && i == one))
				{
					ArrayList<String> pass = new ArrayList<String>(visited);
					pass.add(node + "-" + i);
					lengths.add(longestPath(pass, i, graph[node][i]));
				}
			}
		}
		Collections.sort(lengths);
		return Integer.MAX_VALUE;
	}
	
}
