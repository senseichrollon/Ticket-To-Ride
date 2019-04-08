package game.graphics.animation;

import java.util.ArrayList;

public class AnimationSequence implements Runnable {
	private ArrayList<Animator> animations;

	

	
	
	public AnimationSequence() {
		animations = new ArrayList<Animator>();
	}
	
	public void addAnimation(Animator am) {
		animations.add(am);
	}
	
	
	
	public boolean hasEnded() {
		return false;
	}



}
