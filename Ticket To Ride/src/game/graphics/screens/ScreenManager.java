package game.graphics.screens;

import java.awt.Graphics2D;

import game.graphics.engine.GraphicsPanel;
import game.graphics.input.MouseInput;
import game.main.GameState;

public abstract class ScreenManager {
	
	public static final int GAME = 0;
	public static final int MENU = 1;
	public static final int RULE = 2;
	public static final int END = 3;
	public static final int STATIC = 4;
	public static final int PLAY = 5;
	
	private static GameScreen game;
	private static MenuScreen menu;
	private static RuleScreen rule;
	private static Endgame end;
	private static StaticScreen stat;
	private static PlayerSelectionScreen play;
	
	private static ScreenManager currentScreen;
	
	public static void init(MouseInput input) {
		game = new GameScreen(input);
		menu = new MenuScreen(input);
		rule = new RuleScreen();
		end = new Endgame(input);
		stat = new StaticScreen(input);
		play = new PlayerSelectionScreen(input);
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
	
	public static StaticScreen getStatic(){
		return stat;
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
		else if(key == STATIC)
			currentScreen = stat;
		else if(key == PLAY)
			currentScreen = play;
	}
	
	public static void switchEndGame(int[][] data, GameState gs)
	{
		end.setData(data);
		stat.setGameState(gs);
		switchScreen(END);
	}
	
	
	// extension methods
	public abstract void update();
	public abstract void draw(Graphics2D g);
}

