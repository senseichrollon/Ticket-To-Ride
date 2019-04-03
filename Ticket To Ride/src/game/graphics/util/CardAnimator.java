package game.graphics.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CardAnimator {
	private int startX;
	private int startY;
	
	private int endX;
	private int endY;

	private long startTime;
	private long duration;
	
	private BufferedImage cardImage;
	private boolean running = false;
	
	public CardAnimator(int sx, int sy, int ex, int ey, BufferedImage img, long dur) {
		startX = sx;
		startY = sy;
		endX = ex;
		endY = ey;
		cardImage = img;
		duration = dur;
	}
	
	public void start() {
		running = true;
		startTime = System.nanoTime();
	}
	
	
	public void draw(Graphics2D g) {
		
	}
	
	public boolean hasStarted() {
		return running;
	}
	
	public boolean canEnd() {
		return System.nanoTime() - startTime >= duration;
	}
	
	public void stop() {
		running = false;
	}
}
