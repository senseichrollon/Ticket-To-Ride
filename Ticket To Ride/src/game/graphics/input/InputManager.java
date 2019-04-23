package game.graphics.input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import game.entity.ContractCard;
import game.entity.Track;
import game.graphics.util.MButton;
import java.awt.geom.RoundRectangle2D;


public class InputManager   {
	private ArrayList<MButton> displayButtons;
	private MButton pressedButton;
	private MouseInput input;
	
	private boolean selectType;
	private boolean placeTrack;
	private boolean getContract;
	private boolean getTrain;
	
	public InputManager(MouseInput input) {
		displayButtons = new ArrayList<MButton>();
		pressedButton = null;
		this.input = input;
	}
	
	public void update() {
		boolean clicked = input.clicked();
		boolean released = input.released();
		updateButtons(clicked,released,new Point(input.getX(),input.getY()));
	}
	
	public void updateButtons(boolean mousePressed, boolean mouseReleased, Point mouseLoc) {
		if(mousePressed)
			for(int i = 0; i < displayButtons.size(); i++) {
				 MButton b = displayButtons.get(i);
					if(b.checkContains(mouseLoc)) {
						b.setPressed(true);
					}
			}
		if(mouseReleased)
			for(int i = 0; i < displayButtons.size(); i++) {
				 MButton b = displayButtons.get(i);
					if(b.checkContains(mouseLoc)) {
					//	b.setValidRelease(true);
						pressedButton = b;
					}
			}
		
		if(!mousePressed && !mouseReleased)
			for(int i = 0; i < displayButtons.size(); i++) {
				 MButton b = displayButtons.get(i);
					if(b.checkContains(mouseLoc)) {
						b.setPressed(false);
					}
			}
	}
	

	
	
	public int requestTypeOfTurn() {
		MButton b1 = new MButton("Get Contract Card",new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 15),Color.RED, Color.ORANGE);
		b1.setCenter(new Point(80,800));
		b1.setShape(new RoundRectangle2D.Double(0,0,200,50,25,25));
		b1.setId(1);
		
		MButton b2 = new MButton("Place Trains",new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 15),Color.RED, Color.ORANGE);
		b2.setCenter(new Point(80,700));
		b2.setShape(new RoundRectangle2D.Double(0,0,200,50,25,25));
		b2.setId(2);
		
		
		MButton b3 = new MButton("Get Train Card",new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 15),Color.RED, Color.ORANGE);
		b3.setCenter(new Point(80,600));
		b3.setShape(new RoundRectangle2D.Double(0,0,200,50,25,25));
		b3.setId(3);
		
		displayButtons.add(b1);
		displayButtons.add(b2);
		displayButtons.add(b3);
		
		while(pressedButton == null) {
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
		return pressedButton.getId();
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
	
	public void reset() {
		clearButtons();
		pressedButton = null;
	}
	public void clearButtons() {
		displayButtons.clear();
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < displayButtons.size(); i++) {
			MButton b = displayButtons.get(i);
			b.draw(g);
		}
	}
	
	public void drawTrackSelectors(Graphics2D g) {
		
	}
	
	

}
