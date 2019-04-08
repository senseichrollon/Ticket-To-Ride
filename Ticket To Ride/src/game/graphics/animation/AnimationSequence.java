package game.graphics.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class AnimationSequence {
	private ArrayList<Animator> animations;
	
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g) {
		
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


}
