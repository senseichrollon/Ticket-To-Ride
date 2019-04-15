package game.graphics.input;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import game.entity.ContractCard;
import game.entity.Track;
import game.graphics.util.MButton;

public class InputManager   {
	private ArrayList<MButton> displayButtons;
	private MButton pressedButton;
	
	private ArrayList<Rectangle> imageCheck;
	private Rectangle pressImage;
	private MouseInput input;
	
	public InputManager(MouseInput input) {
		displayButtons = new ArrayList<MButton>();
		pressedButton = null;
		imageCheck = new ArrayList<Rectangle>();
		this.input = input;
	}
	
	public void update() {
		
	}
	
	public void updateButtons(boolean mousePressed, boolean mouseReleased, Point mouseLoc) {
		
	}
	
	public void updateRects() {
		
	}
	
	
	public int requestTypeOfTurn() {
		MButton b1 = new MButton()
		return 0;
	}
	
	public int requestTrainCardSelection() {
		
		return 0;
	}
	
	public ArrayList<ContractCard> requestGovernmentContract() {
		
		return null;
	}
	
	public Track requestTrackPlacement() {
		return null;
	}
	
	
	public void clearButtons() {
		displayButtons.clear();
	}
	
	public void draw(Graphics2D g) {
		for(MButton b : displayButtons) {
			b.draw(g);
		}
	}
	
	public void drawTrackSelectors(Graphics2D g) {
		
	}
	
	

}
