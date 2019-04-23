package game.graphics.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.stream.IntStream;

import game.entity.ContractCard;
import game.entity.Deck;
import game.entity.Player;
import game.graphics.drawers.CityMapDrawer;
import game.graphics.drawers.ContractCardDrawer;
import game.graphics.drawers.HandDrawer;
import game.graphics.engine.GraphicsPanel;
import game.graphics.input.InputManager;
import game.graphics.input.MouseInput;
import game.graphics.util.ImageLoader;
import game.main.GameState;

public class GameScreen extends ScreenManager implements Runnable {

	private GameState game;
	private CityMapDrawer cMapDrawer;
	private ContractCardDrawer contractDrawer;
	private HandDrawer handDrawer;

	private Thread gameThread;
	private BufferedImage logo, govContract, trainContract;
	private LinkedHashMap<String, BufferedImage> cards;

	private InputManager input;

	public GameScreen(MouseInput in) {
		try {
			game = new GameState();
		} catch (IOException e) {
			e.printStackTrace();
		}

		input = new InputManager(in);
		cMapDrawer = new CityMapDrawer(game.getBoard());
		logo = ImageLoader.loadImage("resources/menuscreen/logo2.png");
		logo = ImageLoader.resize(logo, logo.getWidth() / 3, logo.getHeight() / 3);
		govContract = ImageLoader.loadImage("resources/contractcard/ticket_card_back.jpg");
		trainContract = ImageLoader.loadImage("resources/traincards/backtrain.png");
		contractDrawer = new ContractCardDrawer(game.getDeck().getContractCards());

		cards = new LinkedHashMap<>();

		String[] colors = { "black", "blue", "green", "orange", "purple", "red", "white", "yellow", "wild" };
		for (String color : colors) {
			if (color.equals("wild"))
				cards.put(color, ImageLoader.loadImage("resources/traincards/" + color + ".png"));
			else
				cards.put(color, ImageLoader.loadImage("resources/traincards/" + color + ".jpg"));
		}

		handDrawer = new HandDrawer(cards);
		handDrawer.setTree(game.getPlayers()[game.getCurrentPlayer()].getCards());

		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		while (!game.hasWinner()) {
			int num = input.requestTypeOfTurn(game.getPlayers()[game.getCurrentPlayer()].getName());
			input.reset();
			switch (num) {
				case 1: {
					Rectangle[] coords = new Rectangle[6];
					int y = 20;
					String[] upTrains = game.getDeck().getUpCards();
					for(int i = 0; i < 5; i++) {
						BufferedImage img = cards.get(upTrains[i]);
						coords[i] = new Rectangle(1700,y,(img.getWidth()*8)/10,(img.getHeight()*8)/10);
						y+= 130;
					}
					coords[5] = new Rectangle(1900- trainContract.getHeight()/4,760,(trainContract.getHeight())/4,(trainContract.getWidth())/4);
					while(game.getNumCardsDrawn() < 2) {
						int index = input.requestTrainCardSelection(coords,game.getNumCardsDrawn(),upTrains);
						if(index < 5)
							game.drawFaceUpCard(index);
						else
							game.drawFaceDownCard();
						input.reset();
					}

					break;
				}
				case 2: {

				}
				case 3: {
					ContractCard[] cards = game.drawContracts();
					BufferedImage[] img = new BufferedImage[cards.length];
					for(int i = 0; i < cards.length; i++) {
						img[i] = ImageLoader.getCopy(contractDrawer.getCardImages().get(cards[i]));
					}
					ArrayList<Integer> keep = input.requestGovernmentContract(cards, img);
					ArrayList<ContractCard> retCards = new ArrayList<ContractCard>();
					ArrayList<ContractCard> keepCards = new ArrayList<ContractCard>();
					for(int i = 0; i < cards.length; i++) {
						if(keep.contains(i))
							keepCards.add(cards[i]);
						else
							retCards.add(cards[i]);
					}

					game.setContracts(keepCards);
					game.returnContracts(retCards);
				}
			}
			input.reset();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			game.updatePlayer();
		}
	}

	@Override
	public void update() {
		input.update();
		if (!(contractDrawer.getParent() == GraphicsPanel.getPanel())) {
			GraphicsPanel.getPanel().add(contractDrawer);
		}
		handDrawer.setTree(game.getPlayers()[game.getCurrentPlayer()].getCards());
		contractDrawer.setPlayerContracts(game.getPlayers()[game.getCurrentPlayer()].getContracts());
	}

	public void drawPiles(Graphics2D g) {
		g.fillRect(1625, 0, 500, 1080);
		g.drawImage(govContract, 1700, 900, (govContract.getWidth() * 2) / 3, (govContract.getHeight() * 2) / 3, null);
		AffineTransform at = new AffineTransform();
		at.setToTranslation(1900, 760);
		at.rotate(Math.PI / 2);
		at.scale(0.25, 0.25);
		g.drawImage(trainContract, at, null);

		Deck deck = game.getDeck();
		int y = 20;

		for (String s : deck.getUpCards()) {
			if(s != null)
			g.drawImage(cards.get(s), 1700, y, (cards.get(s).getWidth() * 8) / 10, (cards.get(s).getHeight() * 8) / 10,
					null);
			y += 130;
		}
	}
	
	
	
	public void drawLeaderBoard(Graphics2D g) {
		Player[] players = game.getPlayers();
		ArrayList<Player> sorted = new ArrayList<>();
		IntStream.range(0, players.length).forEach(i -> sorted.add(players[i]));
		Collections.sort(sorted, (a,b) -> Integer.compare(b.getPoints(), a.getPoints()));
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 25));
		g.drawString(String.format("%-8s%-8s%-8s","Player","Points","Trains"), 20, 80);
		int y = 150;
		for(Player p : players) {
			g.setColor(Color.BLACK);
			g.drawString(String.format("%-8s%4d  %8d",p.getName(), p.getPoints(),p.getTrains()), 20, y);
			Color c = null;
			switch(p.getTrainColor()) {
				case "blue": {
					c = Color.BLUE;
					break;
				}
				case "purple": {
					c = new Color(148,0,211);
					break;
				}
				case "green": {
					c = Color.GREEN;
					break;
				}
				case "yellow": {
					c = Color.YELLOW;
					break;
				}
			}
			g.setColor(c);
			g.fill(new Ellipse2D.Double(230,y-20,30,30));
			y+= 70;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		handDrawer.draw(g);
		Color c2 = Color.RED.darker();
		GradientPaint gp1 = new GradientPaint(0, 0, Color.orange, 0, (600), c2, true);
		Paint p = g.getPaint();
		g.setPaint(gp1);
		g.fillRect(0, 0, 398, 400);
		g.setPaint(p);
		g.drawImage(logo, 10, 0, null);

		c2 = Color.YELLOW;
		gp1 = new GradientPaint(0, 0, Color.ORANGE.darker(), 0, (600), c2, true);
		g.setPaint(gp1);
		g.fillRect(0, 400, 398, 680);
	 	drawPiles(g);
		cMapDrawer.draw(g);
		input.draw(g);
		drawLeaderBoard(g);
		
	}

}
