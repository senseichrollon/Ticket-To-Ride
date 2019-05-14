package game.graphics.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import game.graphics.drawers.TrainBackGround;
import game.graphics.input.MouseInput;
import game.graphics.util.MButton;
import game.main.GameState;

public class PlayerSelectionScreen extends ScreenManager {
	
	public static boolean[] PLAYER_TYPE = new boolean[4];
	
	private MButton[] humanPlayer;
	private MButton[] aiPlayer;
	private MButton cont;
	private MouseInput in;
	
	
	public PlayerSelectionScreen(MouseInput in) {
		this.in = in;
		humanPlayer = new MButton[4];
		aiPlayer = new MButton[4];
		
		for(int i = 0; i < 4; i++) {
			humanPlayer[i] = new MButton("Human Player", new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15), Color.RED,Color.ORANGE);
			humanPlayer[i].setCenter(new Point(900,i * 100 + 400));
			humanPlayer[i].setShape(new RoundRectangle2D.Double(0, 0, 200, 50, 25, 25));
			
			aiPlayer[i] = new MButton("AI Player", new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15), Color.BLUE,Color.CYAN);
			aiPlayer[i].setCenter(new Point(900,i * 100 + 400));
			aiPlayer[i].setShape(new RoundRectangle2D.Double(0, 0, 200, 50, 25, 25));
		}
		
		cont = new MButton("Continue", new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 20), Color.GREEN, Color.orange);
		cont.setCenter(new Point(900,900));
		cont.setShape(MButton.ellipse(200,100));
	}
	
	public static void reset() {
		Arrays.fill(PLAYER_TYPE, false);
	}
	
	public void update() {
		TrainBackGround.update();
		updateButtons(in.clicked(), in.released(), new Point(in.getX(), in.getY()));
		if(cont.isValidRelease()) {
			cont.setValidRelease(false);
			ScreenManager.switchScreen(ScreenManager.GAME);	
			((GameScreen)ScreenManager.getCurrentScreen()).startGame();
		}

		for(int i = 0; i < 4; i++) {
			if(PLAYER_TYPE[i]) {
				if(aiPlayer[i].isValidRelease()) {
					aiPlayer[i].setValidRelease(false);
					PLAYER_TYPE[i] = false;
				}
			} else {
				if(humanPlayer[i].isValidRelease() ) {
					humanPlayer[i].setValidRelease(false);
					PLAYER_TYPE[i] = true;
				}
			}
		}
	}
	
	public void updateButtons(boolean mousePressed, boolean mouseReleased, Point mouseLoc) {
		ArrayList<MButton> buttons = new ArrayList<>();
		for(int i = 0; i < 4; i++) {
			if(PLAYER_TYPE[i]) {
				buttons.add(aiPlayer[i]);
			} else {
				buttons.add(humanPlayer[i]);
			}
		}
		buttons.add(cont);
		if(mousePressed)
			for(MButton b : buttons)
				if(b.checkContains(mouseLoc))
					b.setPressed(true);
		if(mouseReleased)
			for(MButton b : buttons)
				if(b.checkContains(mouseLoc) && b.isPressed())
					b.setValidRelease(true);
		if(!mousePressed && !mouseReleased)
			for(MButton b : buttons)
					b.setPressed(false);
				
	}
	
	public void draw(Graphics2D g) {
		TrainBackGround.draw(g);
		for(int i = 0; i < 4; i++) {
			g.setFont(new Font("TimesRoman", Font.BOLD, 25));
			g.drawString(GameState.PLAYER_NAMES[i], 800, i * 100 + 420);
			if(PLAYER_TYPE[i]) {
				aiPlayer[i].draw(g);
			} else {
				humanPlayer[i].draw(g);
			}
		}
		cont.draw(g);
	}
}
