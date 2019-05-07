package game.ai;

import game.main.GameState;

public class State {
	private GameState board;
	private int visitCount;
	private double winScore;
		
	public double UCT(double parent) {
		return winScore/visitCount + (Math.sqrt(Math.log(parent)/winScore));
	}
}
