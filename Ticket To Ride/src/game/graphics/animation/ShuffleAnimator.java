package game.graphics.animation;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class ShuffleAnimator implements Animator {
	private long duration;
	private CardAnimator[] one, two;
	
	public ShuffleAnimator(Point[] pt1, Point[] pt2, BufferedImage[] img, BufferedImage[] img2, int x, int y) {
		one = new CardAnimator[pt1.length];
		two = new CardAnimator[pt2.length];
		
		
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	@Override
	public boolean hasStarted() {
		return false;
	}

	@Override
	public boolean canEnd() {
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		
	}
}
