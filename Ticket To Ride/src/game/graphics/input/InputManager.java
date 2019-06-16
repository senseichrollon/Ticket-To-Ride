package game.graphics.input;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import game.entity.CityMap;
import game.entity.ContractCard;
import game.entity.Player;
import game.entity.Track;
import game.graphics.animation.AnimationManager;
import game.graphics.drawers.CityMapDrawer.TrackDrawer;
import game.graphics.util.ImageLoader;
import game.graphics.util.MButton;
import game.main.GameState;

public class InputManager {
	
	private ArrayList<MButton> displayButtons;
	private ArrayList<ClickBox> clickBoxes;
	private ArrayList<Text> textDisplay;

	private ClickBox pressedClick;
	private MButton pressedButton;
	private MouseInput input;
	
	public InputManager(MouseInput input) {
		displayButtons = new ArrayList<>();
		clickBoxes = new ArrayList<ClickBox>();
		textDisplay = new ArrayList<Text>();
		pressedButton = null;
		this.input = input;
	}

	public void update(boolean clicked, boolean released) {
		if(AnimationManager.animating())
			return;			
		updateButtons(clicked, released, new Point(input.getX(), input.getY()));
		updateClickBox(clicked, released, new Point(input.getX(), input.getY()));
	}

	public void updateButtons(boolean mousePressed, boolean mouseReleased, Point mouseLoc) {
		if (mousePressed)
			for (int i = 0; i < displayButtons.size(); i++) {
				MButton b = displayButtons.get(i);
				if (b != null && b.checkContains(mouseLoc)) {
					b.setPressed(true);
				}
			}
		if (mouseReleased)
			for (int i = 0; i < displayButtons.size(); i++) {
				MButton b = displayButtons.get(i);
				if (b != null && b.isPressed() && b.checkContains(mouseLoc)) {
					b.setValidRelease(true);
					pressedButton = b;
				}
			}

		if (!mousePressed && !mouseReleased)
			for (int i = 0; i < displayButtons.size(); i++) {
				MButton b = displayButtons.get(i);
				if (b != null && !b.checkContains(mouseLoc)) {
					b.setPressed(false);
				}
			}
	}

	public void updateClickBox(boolean mousePressed, boolean mouseReleased, Point mouseLoc) {
		if (mousePressed)
			for (int i = 0; i < clickBoxes.size(); i++) {
				ClickBox b = clickBoxes.get(i);
				if (b != null&& b.contains(mouseLoc)) {
					b.setPressed(true);
				} else
					b.setHover(false);
			}
		if (mouseReleased)
			for (int i = 0; i < clickBoxes.size(); i++) {
				ClickBox b = clickBoxes.get(i);
				if (b != null && b.pressed() && b.contains(mouseLoc)) {
					pressedClick = b;
				} else
					b.setHover(false);
			}

		if (!mousePressed && !mouseReleased)
			for (int i = 0; i < clickBoxes.size(); i++) {
				ClickBox b = clickBoxes.get(i);
				if (b != null && b.contains(mouseLoc)) {
					b.setHover(true);
					b.setPressed(false);
				} else
					b.setHover(false);
			}
	}

	public int requestTypeOfTurn(GameState game) {
		String playerName = game.getPlayers()[game.getCurrentPlayer()].getName();
		Text text = new Text(playerName + ", select an action", 80, 500,
				new Font("TimesRoman", Font.BOLD | Font.ITALIC, 25),Color.BLACK);
		
		MButton b1 = new MButton("Get Contract Card", new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15), Color.RED,
				Color.ORANGE);
		b1.setCenter(new Point(80, 800));
		b1.setShape(new RoundRectangle2D.Double(0, 0, 200, 50, 25, 25));
		b1.setId(3);

		MButton b2 = new MButton("Place Trains", new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15), Color.RED,
				Color.ORANGE);
		b2.setCenter(new Point(80, 700));
		b2.setShape(new RoundRectangle2D.Double(0, 0, 200, 50, 25, 25));
		b2.setId(2);

		MButton b3 = new MButton("Get Train Card", new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15), Color.RED,
				Color.ORANGE);
		b3.setCenter(new Point(80, 600));
		b3.setShape(new RoundRectangle2D.Double(0, 0, 200, 50, 25, 25));
		b3.setId(1);

		textDisplay.add(text);
		if(game.getDeck().canDrawContracts())
			displayButtons.add(b1);
		if(game.getPlacableTracks().size() > 0)
			displayButtons.add(b2);
		if(game.getDeck().canDrawTrains())
			displayButtons.add(b3);

		while (pressedButton == null) {
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
		pressedButton.setValidRelease(false);
		return pressedButton.getId();
	}

	public int requestTrainCardSelection(int numCardsDrawn,GameState game, LinkedHashMap<String, BufferedImage> cards,BufferedImage trainContract) {
		String[] upTrains = game.getDeck().getUpCards();
		Rectangle2D.Double[] coords = new Rectangle2D.Double[6];
		for (int i = 0; i < 5; i++) {
			BufferedImage img = cards.get(upTrains[i]);
			if (img != null)
				coords[i] = new Rectangle2D.Double(1700, i * 130 + 20, (img.getWidth() * 8) / 10,
						(img.getHeight() * 8) / 10);
		}
		coords[5] = new Rectangle2D.Double(1900 - trainContract.getHeight() / 4, 760,
				(trainContract.getHeight()) / 4, (trainContract.getWidth()) / 4);
		
		
		for (int i = 0; i < 5; i++) {
			if (upTrains[i] != null && (numCardsDrawn != 1 || !upTrains[i].equals("wild"))) {
				clickBoxes.add(new ClickBox(coords[i], i));
			}
		}
		if(!game.getDeck().getTrainDeck().isEmpty())
			clickBoxes.add(new ClickBox(coords[5], 5));
		while (pressedClick == null) {
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
		return pressedClick.getId();
	}

	public ArrayList<Integer> requestGovernmentContract(ContractCard[] cards, BufferedImage[] img) {
		if(input.duluthHack())
		{
			input.resetDuluth();
			return null;
		}
		ArrayList<Integer> keep = new ArrayList<Integer>();
		int numKeep = (cards.length == 5)?3:1;
		int y = 420;
		int xScale = ((cards.length == 5)?8:7);
		int yScale = ((cards.length == 5)?9:8);

		for (int i = 0; i < cards.length; i++) {
			keep.add(i);
			Rectangle2D.Double rect = new Rectangle2D.Double(10, y, img[i].getWidth() / xScale, img[i].getHeight() / yScale);
			img[i] = ImageLoader.resize(img[i], (int) rect.getWidth(), (int) rect.getHeight());
			clickBoxes.add(new ClickBox(rect, i, img[i]));
			y += (cards.length == 5)?115:200;
		}
		MButton save = new MButton("Save selections", new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15), Color.RED,
				Color.ORANGE);
		save.setCenter(new Point(90, 1000));
		save.setShape(new RoundRectangle2D.Double(0, 0, 200, 50, 25, 25));

		displayButtons.add(save);

		Text k = new Text("Keep", 10, 420, new Font("TimesRoman", Font.BOLD | Font.ITALIC, 25),Color.BLACK);
		Text d = new Text("Discard", 200, 420, new Font("TimesRoman", Font.BOLD | Font.ITALIC, 25),Color.BLACK);
		textDisplay.add(k);
		textDisplay.add(d);
		
		while (!save.isValidRelease()) {
			for (int i = 0; i < clickBoxes.size(); i++) {
				if (pressedClick == clickBoxes.get(i)) {
					Rectangle2D.Double rect = pressedClick.getBounds();
					if (!keep.contains(i)) {
						rect.setRect(10, rect.getY(), rect.getWidth(), rect.getHeight());
						keep.add(i);

					} else if (keep.size() > numKeep) {
						rect.setRect(200, rect.getY(), rect.getWidth(), rect.getHeight());
						keep.remove(new Integer(i));
					}
					pressedClick = null;
				}
			}
			try {Thread.sleep(100);	} catch (InterruptedException e) {}
		}
		return keep;
	}

	public int requestTrack(LinkedHashMap<Track, TrackDrawer> map, HashMap<Track, boolean[]> canPlace) {
		for (Track track : canPlace.keySet()) {
			TrackDrawer drawer = map.get(track);
			boolean[] place = canPlace.get(track);
			if (place[0]) {
				Point2D.Double point = drawer.getClick(0);
				Rectangle2D.Double rect = new Rectangle2D.Double( point.getX(), point.getY(), 10, 10);
				clickBoxes.add(new ClickBox(rect, track.getID(), Color.RED));
			}
			
			if (place[1]) {
				Point2D.Double point = drawer.getClick(1);
				
				Rectangle2D.Double rect = new Rectangle2D.Double( point.getX(), point.getY(), 10, 10);
				clickBoxes.add(new ClickBox(rect, track.getID() + 1000, Color.RED));		
			}
		}
		while (pressedClick == null) {
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
		return pressedClick.getId();
	}

	public HashMap<String,Integer> requestCards(Track track, boolean second, LinkedHashMap<String, BufferedImage> cardImage,Player player) {
		
		HashMap<String, Integer> map = new HashMap<>();
		map.put("wild", 0);

		HashMap<String, MButton> addMap = new HashMap<>();
		HashMap<String, MButton> subtractMap = new HashMap<>();
		HashMap<String, Text> textMap = new HashMap<>();

		textDisplay.add(new Text("Place trains for:",170,650,new Font("TimesRoman", Font.BOLD, 28),Color.BLACK));
		Text city1 = new Text(CityMap.INDEX_TO_CITY.get(track.getCityOne()),200,700,new Font("TimesRoman", Font.BOLD, 25),Color.BLACK);
		Text city2 = new Text(CityMap.INDEX_TO_CITY.get(track.getCityTwo()),200,800,new Font("TimesRoman", Font.BOLD, 25),Color.BLACK);
		textDisplay.add(city1);
		textDisplay.add(new Text("to",200,750,new Font("TimesRoman", Font.BOLD, 25),Color.BLACK));
		textDisplay.add(city2);
		String search = "";
		if (second) {
			search = track.getTrackColor2();
		} else {
			search = track.getTrackColor1();
		}
			
		int x = 10;
		int y = 420;
		for (String s : cardImage.keySet()) {
			ClickBox box = new ClickBox(
					new Rectangle2D.Double(x, y, (int) cardImage.get(s).getWidth()/2, (int) cardImage.get(s).getHeight()/2),
					s.hashCode(), cardImage.get(s));
			box.setCanHover(false);

			MButton add = new MButton("+", new Font("TimesRoman", Font.BOLD | Font.ITALIC, 14), new Color(100,149,237), Color.CYAN);
			add.setCenter(new Point((int) (x + 5 + cardImage.get(s).getWidth()/2), y));
			add.setShape(new RoundRectangle2D.Double(0, 0, 30, 20,10,10));

			MButton subtract = new MButton("-", new Font("TimesRoman", Font.BOLD | Font.ITALIC, 14), new Color(100,149,237),
					Color.CYAN);
			subtract.setCenter(new Point((int) (x +5 +  cardImage.get(s).getWidth()/2), y+40));
			subtract.setShape(new RoundRectangle2D.Double(0, 0, 30, 20,10,10));

			Text text = new Text("0", cardImage.get(s).getWidth()/4, y + 50, new Font("TimesRoman", Font.BOLD | Font.ITALIC, 40),Color.YELLOW);
			textMap.put(s, text);
			if (s.equals(search) || search.equals("gray") || s.equals("wild")) {
				displayButtons.add(add);
				map.put(s, 0);
				addMap.put(s, add);
				subtractMap.put(s, subtract);
				clickBoxes.add(box);
				textDisplay.add(text);
				y += 75;

			}

		}

		MButton placeCards = new MButton("Place trains", new Font("TimesRoman", Font.BOLD | Font.ITALIC, 30), Color.BLUE,Color.CYAN);
		placeCards.setCenter(new Point(140,1020));
		int total = 0;
		placeCards.setShape(new RoundRectangle2D.Double(0, 0, 200, 50, 25, 25));
		HashSet<String> set = new HashSet<>();
		displayButtons.add(placeCards);
		while (pressedButton != placeCards || !(total == track.getLength() && !((set.size() >= 2 && !set.contains("wild")) || (set.size() >= 3 && set.contains("wild"))))) {
			if(pressedButton == placeCards) {
				placeCards.setValidRelease(false);
			}
			
			total = 0;
			for (String s : addMap.keySet()) {
				MButton button = addMap.get(s);
				int val = map.get(s);
				if (button.isValidRelease()) {
					button.setValidRelease(false);
					button.setPressed(false);
					map.put(s, ++val);
					Text text = textMap.get(s);
					text.setText(Integer.toString(map.get(s)));

					displayButtons.add(subtractMap.get(s));
				}
				if (val == player.getCards().getCard(s).getCount()) {
					displayButtons.remove(button);
				}
			}
			for (String s : subtractMap.keySet()) {
				MButton button = subtractMap.get(s);
				int val = map.get(s);
				if (button.isValidRelease()) {
					button.setValidRelease(false);
					button.setPressed(false);
					map.put(s, --val);
					Text text = textMap.get(s);
					text.setText(Integer.toString(map.get(s)));

					displayButtons.add(addMap.get(s));
				}
				if (val == 0) {
					displayButtons.remove(button);
				}
				total += val;
			}
			for(String s : map.keySet()) {
				if(map.get(s) != 0)
					set.add(s);
				else
					set.remove(s);
			}
			try {Thread.sleep(10);} catch (InterruptedException e) {}
		}
		reset();
		return map;
	}

	public void reset() {
		textDisplay.clear();
		clickBoxes.clear();
		pressedClick = null;
		displayButtons.clear();
		pressedButton = null;

	}

	public void draw(Graphics2D g) {
		if(AnimationManager.animating())
			return;
		for (int i = 0; i < displayButtons.size(); i++) {
			MButton b = displayButtons.get(i);
			if(b == null)
				continue;
			b.draw(g);
		}
		for (int i = 0; i < clickBoxes.size(); i++) {
			if(clickBoxes.get(i) == null)
				continue;
			clickBoxes.get(i).draw(g);
		}

		for (int i = 0; i < textDisplay.size(); i++) {
			if(textDisplay.get(i) == null)
				continue;
			textDisplay.get(i).draw(g);
		}
	}

	class Text {
		private String text;
		private int x, y;
		private Font f;
		private Color color;

		public Text(String text, int x, int y, Font f, Color c) {
			this.text = text;
			this.x = x;
			this.y = y;
			this.f = f;
			this.color =c;
		}

		public void draw(Graphics2D g) {
			g.setColor(color);
			g.setFont(f);
			g.drawString(text, x, y);
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	class ClickBox {
		private Rectangle2D.Double clickBox;
		private int id;
		private boolean hover;
		private boolean canHover;
		private boolean pressed;
		private Color color;
		private BufferedImage img;
		
		private Color hoverColor;

		public ClickBox(Rectangle2D.Double rect, int id) {
			this.clickBox = rect;
			this.id = id;
			hover = false;
			canHover = true;
			color = null;
			img = null;
			hoverColor = Color.PINK;
		}

		public ClickBox(Rectangle2D.Double rect, int id, BufferedImage img) {
			this(rect, id);
			this.img = img;
			hoverColor = Color.RED;
		}

		public ClickBox(Rectangle2D.Double rect, int id, Color c) {
			this(rect, id);
			this.color = c;
		}

		public void draw(Graphics2D g) {
			if (color != null) {
				g.setColor(color);
				g.fill(clickBox);
			}
			if (img != null)
				g.drawImage(img, (int) clickBox.getX(), (int) clickBox.getY(), (int) clickBox.getWidth(),
						(int) clickBox.getHeight(), null);

			g.setColor(hoverColor);
			g.setStroke(new BasicStroke(6));
			if (canHover && hover)
				g.draw(clickBox);
		}

		public Rectangle2D.Double getBounds() {
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

		public void setCanHover(boolean b) {
			canHover = b;
		}

		public boolean contains(Point p) {
			return clickBox.contains(p);
		}
	}

}

