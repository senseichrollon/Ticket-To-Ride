package game.graphics.animation;

import java.awt.Graphics2D;

public interface Animator {
	public void start();
	public void stop();
	public boolean hasStarted();
	public boolean canEnd();
	
	public void draw(Graphics2D g);
}
