package game.graphics.animation;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class ShuffleAnimator {
	private long duration;
	private CardAnimator[] one, two;
	
	public ShuffleAnimator(Point[] pt1, Point[] pt2, BufferedImage[] img, BufferedImage[] img2) {
		one = new CardAnimator[pt1.length];
		two = new CardAnimator[pt2.length];
		
		
	}
}
