package game.graphics.screens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.graphics.drawers.TrainBackGround;
import game.graphics.engine.GraphicsPanel;
import game.graphics.input.MouseInput;
import game.graphics.util.ImageLoader;
import game.graphics.util.MButton;

public class MenuScreen extends ScreenManager {

	private MouseInput input;
	private MButton playButton;
	private MButton exitButton;
	private MButton creditButton;
	private BufferedImage logo;


	private JFrame credits;
	
	public MenuScreen(MouseInput input) {
		this.input = input;
		playButton = new MButton("Play", new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 20), Color.GREEN, Color.orange);
		playButton.setCenter(new Point(600,850));
		playButton.setShape(MButton.ellipse(200,100));
		
		creditButton = new MButton("Credits", new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 20), Color.GREEN, Color.orange);
		creditButton.setCenter(new Point(1100,850));
		creditButton.setShape(MButton.ellipse(200,100));
		credits = new JFrame("Credits");
		
		exitButton = new MButton("Exit", new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 20), Color.GREEN, Color.orange);
		exitButton.setCenter(new Point(850,850));
		exitButton.setShape(MButton.ellipse(200,100));
		

		logo = ImageLoader.resize(ImageLoader.loadImage("resources/menuscreen/logo.png"), 550, 550);
		TrainBackGround.init();
	}
	
	@Override
	public void update() {
		TrainBackGround.update();
		updateButton(input.clicked(), input.released(), new Point(input.getX(), input.getY()));
		if(playButton.isValidRelease()) {
			playButton.setValidRelease(false);
			ScreenManager.switchScreen(ScreenManager.GAME);
			((GameScreen)ScreenManager.getCurrentScreen()).startGame();
		}
		
		if(exitButton.isValidRelease()) {
			System.exit(0);
		}
		
		if(creditButton.isValidRelease())
		{
			creditButton.setValidRelease(false);
			JOptionPane.showMessageDialog(credits,
					"Aurko Routh - Project Lead\nRac Mukkamala - Software Developer"
					+ "\nNikunj Zamwar - Software Developer\nMiles Tran - Software Developer",
				    "Credits",
				    JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public void updateButton(boolean mousePressed, boolean mouseReleased, Point mouseLoc) {
		MButton[] buttons = {playButton, exitButton, creditButton};
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
				if(!b.checkContains(mouseLoc))
					b.setPressed(false);
	}
	

	
	@Override
	public void draw(Graphics2D g) {	
		TrainBackGround.draw(g);
		playButton.draw(g);
		exitButton.draw(g);
		creditButton.draw(g);
		
		g.drawImage(logo, 700, 250, null);
	}

}
