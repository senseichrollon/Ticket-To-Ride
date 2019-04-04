package game.graphics.animation;

import java.util.ArrayList;

public class AnimationSequence implements Runnable {
	private ArrayList<Animator> animations;
	private Thread thread;
	private boolean running;
	
	
	public void start() {
		
	}
	
	
	public AnimationSequence() {
		animations = new ArrayList<Animator>();
	}
	
	public void addAnimation(Animator am) {
		animations.add(am);
	}
	
	
	
	public boolean hasEnded() {
		return false;
	}


	@Override
	public void run() {
		
	}
}
