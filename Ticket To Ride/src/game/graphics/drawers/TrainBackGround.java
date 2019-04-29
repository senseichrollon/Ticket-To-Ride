package game.graphics.drawers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.graphics.engine.GraphicsPanel;
import game.graphics.screens.GameScreen;
import game.graphics.screens.ScreenManager;
import game.graphics.util.ImageLoader;

public class TrainBackGround {
	private static int trainX;
	private static int trainY;
	private static int dx;
	private static BufferedImage train;
	
	public static void init() {
		train = ImageLoader.resize(ImageLoader.loadImage("resources/menuscreen/chootrain.png"), 500, 200);
		trainX = GraphicsPanel.WIDTH - train.getWidth();
		trainY = 0;
		dx = 3;
	}
	public static void update() {
		if(trainX <= -1*train.getWidth())
			trainX = GraphicsPanel.WIDTH + train.getWidth();
		trainX -= dx;
	}
	public static void draw(Graphics2D g) {
		Color c2 = Color.CYAN.darker().darker();
		GradientPaint gp1 = new GradientPaint(0, 0, Color.CYAN, 0, (GraphicsPanel.HEIGHT), c2, true);
		g.setPaint(gp1);
		g.fillRect(0, 0, GraphicsPanel.WIDTH, GraphicsPanel.HEIGHT);
		g.drawImage(train, trainX, trainY, null);
		
		g.setStroke(new BasicStroke(20));
		g.setColor(Color.BLACK);
		int lineY = trainY + train.getHeight()-15;
		g.drawLine(0, lineY, GraphicsPanel.WIDTH, lineY);
	}
}
