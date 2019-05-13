package game.testing;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import game.entity.Deck;

public class DeckTest {
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		Deck deck = new Deck();
		while(true) {
			System.out.println(Arrays.toString(deck.getUpCards()));
			System.out.println(deck.getTrainDeck());
			
			System.out.println("Enter type of move:");
			switch(in.nextInt()) {
				case 1 :{
					deck.drawRandTrain(false);
					break;
				}
				case 2:{
					break;
				}
				case 3:{
					break;
				}
			}
		}
	}
}
