package game.graphics.screens;

import java.awt.Graphics2D;

import game.graphics.input.MouseInput;

public abstract class ScreenManager {
	
	public static final int GAME = 0;
	public static final int MENU = 1;
	public static final int RULE = 2;
	
	private static GameScreen game;
	private static MenuScreen menu;
	private static RuleScreen rule;
	
	private static ScreenManager currentScreen;
	
	public static void init(MouseInput input) {
		game = new GameScreen();
		menu = new MenuScreen(input);
		rule = new RuleScreen();
		currentScreen = menu;
	}
	
	public static GameScreen getGame() {
		return game;
	} 
	
	public static MenuScreen getMenu() {
		return menu;
	}
		
	public static ScreenManager getCurrentScreen() {
		return currentScreen;
	}
	
	public static void switchScreen(int key) {
		if(key == GAME) 
			currentScreen = game;
		else if(key == MENU) 
			currentScreen = menu;		
	}
	
	
	// extension methods
	public abstract void update();
	public abstract void draw(Graphics2D g);
}

