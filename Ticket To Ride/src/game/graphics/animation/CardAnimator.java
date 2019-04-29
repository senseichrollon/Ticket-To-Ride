package game.graphics.animation;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import game.graphics.util.ImageLoader;

public class CardAnimator implements Animator {
	private int startX;
	private int startY;
	
	private int endX;
	private int endY;
	
	private double numRotations;
	private double scaleX,scaleY;

	private long startTime;
	private long duration;
	
	private BufferedImage cardImage;
	private boolean running = false;
	
	public CardAnimator(int sx, int sy, int ex, int ey, BufferedImage img, long dur, double rot,double sX,double sY) {
		startX = sx;
		startY = sy;
		endX = ex;
		endY = ey;
		cardImage = img;
		duration = dur;
		numRotations = rot;
		scaleX = sX;
		scaleY = sY;
	}
	
	public void start() {
		running = true;
		startTime = System.nanoTime();
	}
	
	
	public void draw(Graphics2D g) {
		double ratio = (System.nanoTime()-startTime)/(double)duration;
		double x = ((endX-startX) * ratio) + startX;
		double y = ((endY - startY) * ratio) + startY;
		
		double rotRatio = ratio*numRotations;
		double angle = Math.PI * 2 * rotRatio;
		
		double sX = 1;
		double sY = 1;
		if(scaleX < 1) {
			sX = 1-((1-scaleX) * ratio);
		} else {
			sX = 1 + ((scaleX-1) * ratio);
		}
		if(scaleY < 1) {
			sY = 1-((1-scaleY) * ratio);
		} else {
			sY = 1 + ((scaleY-1) * ratio);
		}
		double newW = sX * cardImage.getWidth();
		double newH = sY * cardImage.getHeight();
		AffineTransform at = new AffineTransform();
		at.setToTranslation(x, y);
		at.scale(sX,sY);
		at.rotate(angle,(cardImage.getWidth()/2),(cardImage.getHeight())/2);
		g.drawImage(cardImage,at,null);
//		g.drawOval((int)x,(int)y,50,50);

//		BufferedImage img = rotate(cardImage,angle,newW,newH);
//		
//		g.drawImage(img,(int)x,(int)y,null);
	}
	


	public static BufferedImage rotate(BufferedImage image, double angle, double neww, double newh) {
	    int w = image.getWidth(), h = image.getHeight();
	    BufferedImage result = ImageLoader.resize(image, (int)neww, (int)newh);
	    Graphics2D g = result.createGraphics();
	    g.clearRect(0, 0, result.getWidth(), result.getHeight());
	    g.translate((neww-w)/2, (newh-h)/2);
	    g.rotate(angle, w/2, h/2);
	    g.drawRenderedImage(image, null);
	    g.dispose();
	    return result;
	}
	
	public boolean hasStarted() {
		return running;
	}
	
	public boolean canEnd() {
		return (System.nanoTime() - startTime) >= duration;
	}
	
	public void stop() {
		running = false;
	}
}
