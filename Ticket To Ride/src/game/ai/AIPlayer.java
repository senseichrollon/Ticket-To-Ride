package game.ai;

import java.util.ArrayList;
import java.util.Collections;

import game.entity.ContractCard;
import game.entity.Player;
import game.main.GameState;

public class AIPlayer extends Player{
	private GameState game;
	
	public AIPlayer(String n, String c, GameState game) {
		super(n, c);
		this.game = game;
	}
	
	public void startGameMove() {

	}
	
	public void makeMove() {
		
	}
	
	private void selectContracts(int numCards, int keepCards) {
		ContractCard[] cards = game.drawContracts(numCards);
		ArrayList<ContractCard> keep = new ArrayList<>();
		ArrayList<ContractCard> discard = new ArrayList<>();
		
		ArrayList<ContractPath> list = new ArrayList<>();
		for(int i = 0; i < cards.length; i++) {
			ContractPath cPath = new ContractPath(cards[i]);
			cPath.calculateShortestPath(game.getBoard(), this);
			list.add(cPath);
		}
		
	}

}
