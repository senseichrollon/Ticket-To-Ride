package game.graphics.screens;

import java.awt.Graphics2D;

import game.graphics.input.MouseInput;

public abstract class ScreenManager {
	
	public static final int GAME = 0;
	public static final int MENU = 1;
	public static final int RULE = 2;
	public static final int END = 3;
	
	private static GameScreen game;
	private static MenuScreen menu;
	private static RuleScreen rule;
	private static Endgame end;
	
	private static ScreenManager currentScreen;
	
	public static void init(MouseInput input) {
		game = new GameScreen(input);
		menu = new MenuScreen(input);
		rule = new RuleScreen();
		end = new Endgame(input);
		currentScreen = menu;
	}
	
	public static GameScreen getGame() {
		return game;
	} 
	
	public static MenuScreen getMenu() {
		return menu;
	}
	
	public static Endgame getEnd(){
		return end;
	}
		
	public static ScreenManager getCurrentScreen() {
		return currentScreen;
	}
	
	public static void switchScreen(int key) {
		if(key == GAME) 
			currentScreen = game;
		else if(key == MENU) 
			currentScreen = menu;
		else if(key == END)
			currentScreen = end;
	}
	
	public static void switchEndGame(int[][] data)
	{
		end.setData(data);
		switchScreen(END);
	}
	
	
	// extension methods
	public abstract void update();
	public abstract void draw(Graphics2D g);
}

