package game.graphics.drawers;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

import game.entity.CardNode;
import game.entity.PlayerCardTree;
import game.graphics.util.ImageLoader;

public class HandDrawer {
	
	private LinkedHashMap<String, BufferedImage> cards;
	private LinkedHashMap<String, Point> cardCoords;
	private PlayerCardTree tree;
	
	public HandDrawer() {
		cards = new LinkedHashMap<>();
		cardCoords = new LinkedHashMap<>();
		int x = 420;
		int y = 770;
		String[] colors = {"black","blue", "green", "orange", "purple", "red", "white", "yellow","wild"};
		for(String color : colors) {
			if(color.equals("wild"))
				cards.put(color, ImageLoader.loadImage("resources/traincards/" + color + ".png"));
			else
				cards.put(color, ImageLoader.loadImage("resources/traincards/" + color +  ".jpg"));
			cardCoords.put(color, new Point(x,y));
			x += 136;
		}
		
	}
	
	public void update() {
		
	}
	
	
	public void draw(Graphics2D g) {
		Color c2 = Color.RED.darker();
		GradientPaint gp1 = new GradientPaint(298, 720, Color.ORANGE, 1524, (920), c2, false);
		g.setPaint(gp1);
		g.fillRect(298, 720, 1226, 200);
		
		for(String s : cards.keySet()) {
			AffineTransform at = new AffineTransform();
			at.setToTranslation(cardCoords.get(s).getX(), cardCoords.get(s).getY());
			at.rotate(Math.PI/2);
			at.scale(0.65, 0.65);
			g.drawImage(cards.get(s), at, null);
			
			CardNode node = tree.getCard(s);
			
			g.setColor(Color.YELLOW);
			g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,40));
			if(node != null)
				g.drawString(Integer.toString(node.getCount()), (int)cardCoords.get(s).getX()-70, (int)cardCoords.get(s).getY()+80);
		}
		
		
		
	}
	
	public void setTree(PlayerCardTree tree) {
		this.tree = tree;
	}
	
	
	
	
}
