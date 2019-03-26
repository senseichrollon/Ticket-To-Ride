package game.graphics.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import game.graphics.engine.GraphicsPanel;
import game.graphics.input.MouseInput;
import game.graphics.util.MButton;

public class MenuScreen extends ScreenManager {

	private MouseInput input;
	private MButton playButton;
	private Rectangle rect;
	private int dx, dy;
	
	public MenuScreen(MouseInput input) {
		this.input = input;
		playButton = new MButton("Play", new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 20), Color.GREEN, Color.orange);
		playButton.setCenter(new Point(300,300));
		playButton.setShape(MButton.ellipse(200,100));
		dx = 5;
		rect = new Rectangle(0,780,100,100);
	}
	
	@Override
	public void update() {
		if(rect.getX() + rect.getWidth() + dx >= GraphicsPanel.WIDTH || rect.getX() < 0) {
			dx *=-1;
		}

		rect.setBounds((int)rect.getX() + (int)dx, (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
		updateButton(input.clicked(), input.released(), new Point(input.getX(), input.getY()));
		System.out.println(input.clicked() + " " + input.released());
		if(playButton.isValidRelease()) {
			playButton.setValidRelease(false);
			ScreenManager.switchScreen(ScreenManager.GAME);
		}
	}
	
	public void updateButton(boolean mousePressed, boolean mouseReleased, Point mouseLoc) {
		MButton[] buttons = {playButton};
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
		Color c2 = Color.CYAN.darker().darker();
		GradientPaint gp1 = new GradientPaint(0, 0, Color.CYAN, 0, (GraphicsPanel.HEIGHT), c2, true);
		g.setPaint(gp1);
		g.fillRect(0, 0, GraphicsPanel.WIDTH, GraphicsPanel.HEIGHT);
		playButton.draw(g);
		g.setColor(Color.GREEN);
		g.fill(rect);
	}

}
