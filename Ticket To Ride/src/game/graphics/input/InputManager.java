package game.graphics.input;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.entity.ContractCard;
import game.entity.Track;
import game.graphics.util.MButton;


public class InputManager   {
	private ArrayList<MButton> displayButtons;
	private ArrayList<ClickBox> trainCardCoords;
	
	private ClickBox pressedClick;
	private MButton pressedButton;
	private MouseInput input;

	
	public InputManager(MouseInput input) {
		displayButtons = new ArrayList<MButton>();
		trainCardCoords = new ArrayList<ClickBox>();
		pressedButton = null;
		this.input = input;
	}
	
	public void update() {
		boolean clicked = input.clicked();
		boolean released = input.released();
		updateButtons(clicked,released,new Point(input.getX(),input.getY()));
		updateClickBox(clicked,released,new Point(input.getX(),input.getY()));
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
	
	public void updateClickBox(boolean mousePressed, boolean mouseReleased, Point mouseLoc) {
		if(mousePressed)
			for(int i = 0; i < trainCardCoords.size(); i++) {
				 ClickBox b = trainCardCoords.get(i);
					if(b.contains(mouseLoc)) {
						b.setPressed(true);
					} else
						b.setHover(false);
			}
		if(mouseReleased)
			for(int i = 0; i < trainCardCoords.size(); i++) {
				ClickBox b = trainCardCoords.get(i);
					if(b.contains(mouseLoc)) {
						pressedClick = b;
					} else
						b.setHover(false);
			}
		
		if(!mousePressed && !mouseReleased)
			for(int i = 0; i < trainCardCoords.size(); i++) {
				ClickBox b = trainCardCoords.get(i);
					if(b.contains(mouseLoc)) {
						b.setHover(true);
						b.setPressed(false);
					} else
						b.setHover(false);
			}
	}
	

	
	
	public int requestTypeOfTurn() {
		MButton b1 = new MButton("Get Contract Card",new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 15),Color.RED, Color.ORANGE);
		b1.setCenter(new Point(80,800));
		b1.setShape(new RoundRectangle2D.Double(0,0,200,50,25,25));
		b1.setId(3);
		
		MButton b2 = new MButton("Place Trains",new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 15),Color.RED, Color.ORANGE);
		b2.setCenter(new Point(80,700));
		b2.setShape(new RoundRectangle2D.Double(0,0,200,50,25,25));
		b2.setId(2);
		
		
		MButton b3 = new MButton("Get Train Card",new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 15),Color.RED, Color.ORANGE);
		b3.setCenter(new Point(80,600));
		b3.setShape(new RoundRectangle2D.Double(0,0,200,50,25,25));
		b3.setId(1);
		
		displayButtons.add(b1);
		displayButtons.add(b2);
		displayButtons.add(b3);
		
		while(pressedButton == null) {
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
		pressedButton.setValidRelease(false);
		return pressedButton.getId();
	}
	
	public int requestTrainCardSelection(Rectangle[] clickArea, int numCardsDrawn,String[] upTrains) {
		for(int i = 0; i < 5; i++) {
			if(numCardsDrawn != 1 || !upTrains[i].equals("wild")) {
				trainCardCoords.add(new ClickBox(clickArea[i],i));
			}
		}
		trainCardCoords.add(new ClickBox(clickArea[5],5));
		while(pressedClick == null) {
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
		return pressedClick.getId();
	}
	
	public ArrayList<ContractCard> requestGovernmentContract() {
		
		return null;
	}
	
	public Track requestTrackPlacement() {
		return null;
	}
	
	public void reset() {
		trainCardCoords.clear();
		pressedClick = null;
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
		
		for(int i = 0; i < trainCardCoords.size(); i++) {
			trainCardCoords.get(i).draw(g);
		}
	}
	
	public void drawTrackSelectors(Graphics2D g) {
		
	}
	
	class ClickBox {
		private Rectangle clickBox;
		private int id;
		private boolean hover;
		private boolean pressed;
		private Color color;
		private BufferedImage img;
		
		public ClickBox(Rectangle rect, int id) {
			this.clickBox = rect;
			this.id = id;
			hover = false;
			color = null;
			img = null;;
		}
		
		public void draw(Graphics2D g) {
			g.setColor(Color.YELLOW);
			g.setStroke(new BasicStroke(10));
			if(hover)
				g.draw(clickBox);
		}
		
		public int getId() {
			return id;
		}
		
		public boolean pressed() {
			return pressed;
		}
		
		public void setPressed(boolean pressed) {
			this.pressed = pressed;
		}
		
		public boolean isHovering() {
			return hover;
		}
		
		public void setHover(boolean hover) {
			this.hover = hover;
		}
		
		public boolean contains(Point p) {
			return clickBox.contains(p);
		}
	}

}
