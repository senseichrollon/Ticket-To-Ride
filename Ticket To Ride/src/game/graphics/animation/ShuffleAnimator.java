package game.graphics.animation;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class ShuffleAnimator implements Animator {
	private long duration;
	private CardAnimator[] anim;
	private boolean first;
	private boolean running;
	
	public ShuffleAnimator(Point[] pt1, BufferedImage[] img, int x, int y,long duration, boolean in) {
		anim = new CardAnimator[pt1.length];
		for(int i = 0; i < img.length; i++) {
			int x1 = (int)pt1[i].getX();
			int y1 = (int)pt1[i].getY();
			if(in)
				anim[i] = new CardAnimator(x1,y1,x,y,img[i],duration,0,1,1);
			else
				anim[i] = new CardAnimator(x,y,x1,y1,img[i],duration,0,1,1);
		}
		
	}

	@Override
	public void start() {
		for(CardAnimator an : anim) {
			an.start();
		}
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public boolean hasStarted() {
		return running;
	}

	@Override
	public boolean canEnd() {
		boolean end = true;
		for(CardAnimator an : anim) {
			if(an.canEnd()) {
				an.stop();
			} else {
				end = false;
			}
		}
		return end;
	}

	@Override
	public void draw(Graphics2D g) {
		for(CardAnimator an : anim) {
			if(an.hasStarted())
				an.draw(g);
		}
	}
}
