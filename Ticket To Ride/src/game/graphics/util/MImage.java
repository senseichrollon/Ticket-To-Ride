package game.graphics.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MImage {
	
	private int x, y;
	private int width;
	private int height;
	
	private BufferedImage img;
	
	private boolean hover;
	
	
	public MImage(BufferedImage img, int x, int y,int width, int height) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	
	public void draw(Graphics2D g) {
		g.drawImage(img,x,y,width,height,null);
		
		if(hover) {
			g.setColor(Color.YELLOW);
			g.drawRect(x, y, width, height);
		}
	}
	
	
	
	
}
