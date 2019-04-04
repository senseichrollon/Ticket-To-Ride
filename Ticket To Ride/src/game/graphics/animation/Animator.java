package game.graphics.animation;

public interface Animator {
	public void start();
	public void stop();
	public boolean hasStarted();
	public boolean canEnd();
}
