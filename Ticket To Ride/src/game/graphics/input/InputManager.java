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
import java.util.HashMap;
import java.util.LinkedHashMap;

import game.entity.ContractCard;
import game.entity.Track;
import game.graphics.drawers.CityMapDrawer.TrackDrawer;
import game.graphics.util.ImageLoader;
import game.graphics.util.MButton;


public class InputManager   {
	private ArrayList<MButton> displayButtons;
	private ArrayList<ClickBox> clickBoxes;
	private ArrayList<Text> textDisplay;
	
    private ClickBox pressedClick;
	private MButton pressedButton;
	private MouseInput input;

	
	public InputManager(MouseInput input) {
		displayButtons = new ArrayList<MButton>();
		clickBoxes = new ArrayList<ClickBox>();
		textDisplay = new ArrayList<Text>();
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
					if(b.isPressed() && b.checkContains(mouseLoc)) {
						b.setValidRelease(true);
						pressedButton = b;
					}
			}
		
		if(!mousePressed && !mouseReleased)
			for(int i = 0; i < displayButtons.size(); i++) {
				 MButton b = displayButtons.get(i);
					if(!b.checkContains(mouseLoc)) {
						b.setPressed(false);
					}
			}
	}
	
	public void updateClickBox(boolean mousePressed, boolean mouseReleased, Point mouseLoc) {
		if(mousePressed)
			for(int i = 0; i < clickBoxes.size(); i++) {
				 ClickBox b = clickBoxes.get(i);
					if(b.contains(mouseLoc)) {
						b.setPressed(true);
					} else
						b.setHover(false);
			}
		if(mouseReleased)
			for(int i = 0; i < clickBoxes.size(); i++) {
				ClickBox b = clickBoxes.get(i);
					if(b.pressed() && b.contains(mouseLoc)) {
						pressedClick = b;
					} else
						b.setHover(false);
			}
		
		if(!mousePressed && !mouseReleased)
			for(int i = 0; i < clickBoxes.size(); i++) {
				ClickBox b = clickBoxes.get(i);
					if(b.contains(mouseLoc)) {
						b.setHover(true);
						b.setPressed(false);
					} else
						b.setHover(false);
			}
	}
	

	
	
	public int requestTypeOfTurn(String playerName) {
		Text text = new Text(playerName + ", select an action",80,500,new Font("TimesRoman", Font.BOLD | Font.ITALIC, 25));
		
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
		
		textDisplay.add(text);
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
				clickBoxes.add(new ClickBox(clickArea[i],i));
			}
		}
		clickBoxes.add(new ClickBox(clickArea[5],5));
		while(pressedClick == null) {
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
		return pressedClick.getId();
	}
	
	public ArrayList<Integer> requestGovernmentContract(ContractCard[] cards, BufferedImage[] img) {
		ArrayList<Integer> keep = new ArrayList<Integer>();
		int y = 450;
		for(int i = 0; i < cards.length; i++) {
			keep.add(i);
			Rectangle rect = new Rectangle(10,y, img[i].getWidth()/7, img[i].getHeight()/8);
			img[i] = ImageLoader.resize(img[i],(int)rect.getWidth(), (int)rect.getHeight());
			clickBoxes.add(new ClickBox(rect,i,img[i]));
			y += 200;
		}
		MButton save = new MButton("Save selections",new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 15),Color.RED, Color.ORANGE);
		save.setCenter(new Point(90,1000));
		save.setShape(new RoundRectangle2D.Double(0,0,200,50,25,25));
		
		displayButtons.add(save);
		
		Text k = new Text("Keep",10,420,new Font("TimesRoman", Font.BOLD | Font.ITALIC, 25));
		Text d = new Text("Discard",200,420,new Font("TimesRoman", Font.BOLD | Font.ITALIC, 25));
		textDisplay.add(k);
		textDisplay.add(d);
		
		while(!save.isValidRelease()) {
			for(int i = 0; i < clickBoxes.size(); i++) {
				if(pressedClick == clickBoxes.get(i)) {
					Rectangle rect = pressedClick.getBounds();
					if(!keep.contains(i)) {
						rect.setBounds(10, (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
						keep.add(i);

					} else if(keep.size() > 1) {
						rect.setBounds(200, (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
						keep.remove(new Integer(i));
					}
					pressedClick = null;
				}
			}
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
		return keep;
	}
	
	public int requestTrack(LinkedHashMap<Track, TrackDrawer> map, HashMap<Track, boolean[]> canPlace) {
		int id = 0;
		for(Track track : map.keySet()) {
			TrackDrawer drawer = map.get(track);
			boolean[] place = canPlace.get(track);
			if(place[0]) {
				Point point = drawer.getClick(0);
				Rectangle rect = new Rectangle((int)point.getX(),(int)point.getY(),15,15);
				clickBoxes.add(new ClickBox(rect,id,Color.RED));
			}
			
			if(place[1]) {
				Point point = drawer.getClick(1);
				Rectangle rect = new Rectangle((int)point.getX(),(int)point.getY(),15,15);
				clickBoxes.add(new ClickBox(rect,id+1000,Color.RED));	
			}
			id++;
		}
		
		while(pressedClick == null) {
			try {Thread.sleep(100);} catch (InterruptedException e) {}

		}
		return pressedClick.getId();
	}
	
	public void reset() {
		textDisplay.clear();
		clickBoxes.clear();
		pressedClick = null;
		displayButtons.clear();
		pressedButton = null;
		
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < displayButtons.size(); i++) {
			MButton b = displayButtons.get(i);
			b.draw(g);
		}
		
		for(int i = 0; i < clickBoxes.size(); i++) {
			clickBoxes.get(i).draw(g);
		}
		
		for(int i = 0; i < textDisplay.size(); i++) {
			textDisplay.get(i).draw(g);
		}
	}
	

	
	class Text {
		private String text;
		private int x, y;
		private Font f;
		
		public Text(String text, int x, int y, Font f) {
			this.text = text;
			this.x = x;
			this.y = y;
			this.f = f;
		}
		
		public void draw(Graphics2D g) {
			g.setColor(Color.BLACK);
			g.setFont(f);
			g.drawString(text, x, y);
		}
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
		
		public ClickBox(Rectangle rect, int id, BufferedImage img) {
			this(rect,id);
			this.img = img;
		}
		
		public ClickBox(Rectangle rect, int id, Color c) {
			this(rect,id);
			this.color = c;
		}
		
		
		
		public void draw(Graphics2D g) {
			
			if(img != null)
				g.drawImage(img,(int)clickBox.getX(), (int)clickBox.getY(), (int)clickBox.getWidth(), (int)clickBox.getHeight(),null);

			g.setColor(Color.CYAN);
			g.setStroke(new BasicStroke(10));
			if(hover)
				g.draw(clickBox);
		}
		
		public Rectangle getBounds() {
			return clickBox;
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
